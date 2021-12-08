package com.example.fitfriends.memory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.example.fitfriends.R
import com.example.fitfriends.R.drawable.*
import com.example.fitfriends.databinding.ActivityMemoryGameBinding

class MemoryGame : AppCompatActivity() {
    private var binding: ActivityMemoryGameBinding?=null
    private lateinit var buttons:List<ImageButton?>
    private var indexOfSingleSelectedCard: Int? = null
    private lateinit var cards:List<MemoryCard>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryGameBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        buttons = listOf(binding?.imageButton1, binding?.imageButton2,
        binding?.imageButton3, binding?.imageButton4, binding?.imageButton5,
        binding?.imageButton6, binding?.imageButton7, binding?.imageButton8)

        val images = mutableListOf(ic_favorite , ic_flower, ic_giftcard,
        ic_plane)
        //Adding all the images twice to make a pair
        images.addAll(images)
        //Randomise the order in the list
        images.shuffle()

        //Making memory card model for each index of buttons
        cards = buttons?.indices.map { index->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button?.setOnClickListener {

                // Update models
                updateModels(index)
                // Update the UI for the game
                updateViews()
            }
        }
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button?.alpha = 0.1f
            }
            button?.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.ic_code)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        // Error checking: if user clicks on the card again after it is revealed
        if (card.isFaceUp) {
            Toast.makeText(this, "Invalid move!! 🙄", Toast.LENGTH_SHORT).show()
            return
        }
        /* Three cases
         0 cards previously flipped over => restore cards + flip over the selected card
         1 card was previously flipped over => flip over the selected card + check if the images match
         2 cards previously flipped over => restore cards + flip over the selected card*/
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 selected cards previously
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            // Exactly 1 card was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }
    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(pos1: Int, pos2: Int) {
        if (cards[pos1].identifier == cards[pos2].identifier) {
            Toast.makeText(this, "Match found!! 😎", Toast.LENGTH_SHORT).show()
            cards[pos1].isMatched = true
            cards[pos2].isMatched = true
        }
    }
}