package com.example.fitfriends

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfriends.ExerciseModel
import com.example.fitfriends.R
import com.example.fitfriends.databinding.ItemExerciseStatusBinding


class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    class ViewHolder(binding:ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root){
        val tv=binding.tvItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {return ViewHolder(
        ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val model:ExerciseModel=items[position]
        holder.tv.text=model.getId().toString()
        when{
            model.getIsCompleted()->{
                holder.tv.background=ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_thin_color_accent_border)
                holder.tv.setTextColor(Color.parseColor("#212121"))
            }
            model.getIsSelected()->{
                holder.tv.background=ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_backgrd)
                holder.tv.setTextColor(Color.parseColor("#212121"))
            }
            else->{
                holder.tv.background=ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_border)
                holder.tv.setTextColor(Color.parseColor("#212121"))

            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}