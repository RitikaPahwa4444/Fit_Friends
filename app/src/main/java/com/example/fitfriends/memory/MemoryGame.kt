package com.example.fitfriends.memory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.example.fitfriends.R.drawable.*
import com.example.fitfriends.databinding.ActivityMemoryGameBinding

class MemoryGame : AppCompatActivity() {
    private var binding: ActivityMemoryGameBinding?=null
    private lateinit var buttons:List<ImageButton?>
    private var singleSelectedCard: Int? = null
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
        cards = buttons.indices.map { index->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button?.setOnClickListener {
                // Update models of the Memory Card
                updateModelsOfMemoryCard(index)
                // Update the UI for the game screen
                updateViews()
            }
        }
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            // If the cards are matched, fade the views
            if (card.isMatched) {
                button?.alpha = 0.01f
            }
            button?.setImageResource(if (card.isRevealed) card.id else unknown_image_code)
        }
        var count = 0
        for (card in cards){
            if (card.isMatched){
                count++
            }
        }
        if (count == cards.size){
            Toast.makeText(this@MemoryGame, "Congratulations! You won 😎", Toast.LENGTH_LONG ).show()
            val intent = Intent(this, EndActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateModelsOfMemoryCard(position: Int) {
        val card = cards[position]
        // Error checking: if user clicks on the card again after it is revealed
        if (card.isRevealed) {
            Toast.makeText(this, "Invalid move!! 🙄", Toast.LENGTH_SHORT).show()
            return
        }
        /* Three cases in totality
         0 cards previously flipped over ==> restore cards + flip over the selected card
         1 card was previously flipped over ==> flip over the selected card + check if the images match
         2 cards previously flipped over ==> restore cards + flip over the selected card */

        if (singleSelectedCard == null) {
            // 0 or 2 selected cards previously
            restoreCardsBack()
           singleSelectedCard = position
        } else {
            // Exactly 1 card was selected previously
            checkIfMatched(singleSelectedCard!!, position)
            singleSelectedCard = null
        }
        //We have to flip the selected card in every case
        card.isRevealed = !card.isRevealed
    }
    private fun restoreCardsBack() {
        for (card in cards) {
            // If card is not matched, we make all the cards hidden
            if (!card.isMatched) {
                card.isRevealed = false
            }
        }
    }

    private fun checkIfMatched(pos1: Int, pos2: Int) {
        //If the ids match, we change the isMatched attribute to true
        if (cards[pos1].id == cards[pos2].id) {
            cards[pos1].isMatched = true
            cards[pos2].isMatched = true
        }
    }
}