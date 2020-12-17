package com.example.cardswipedemo.screens.cardScreen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardswipedemo.R
import com.example.cardswipedemo.databinding.ItemSpotBinding
import com.example.cardswipedemo.models.Card

class CardStackAdapter(
    private val context: Context,
    private var cards: ArrayList<Card>
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        val user = card.results[0].user
        holder.binding.textTopValue.text =
            "${user.location.street}, ${user.location.city}, ${user.location.state}"
        Glide.with(holder.binding.imageCircular)
            .load(card.results[0].user.picture)
            .into(holder.binding.imageCircular)

        holder.binding.imageViewProfile.setOnClickListener {
            holder.binding.imageViewProfile.setImageDrawable(context.getDrawable(R.drawable.ic_profile_green))
            holder.binding.imageViewPhone.setImageDrawable(context.getDrawable(R.drawable.ic_call))
            holder.binding.imageViewLocation.setImageDrawable(context.getDrawable(R.drawable.ic_location))

            holder.binding.textViewTopLabel.text = "My Name is"
            holder.binding.textTopValue.text = "${user.name.first} ${user.name.last}"
        }

        holder.binding.imageViewLocation.setOnClickListener {
            holder.binding.imageViewProfile.setImageDrawable(context.getDrawable(R.drawable.ic_profile))
            holder.binding.imageViewPhone.setImageDrawable(context.getDrawable(R.drawable.ic_call))
            holder.binding.imageViewLocation.setImageDrawable(context.getDrawable(R.drawable.ic_location_green))

            holder.binding.textViewTopLabel.text = "My Location is"
            holder.binding.textTopValue.text =
                "${user.location.street}, ${user.location.city}, ${user.location.state}"
        }

        holder.binding.imageViewPhone.setOnClickListener {
            holder.binding.imageViewProfile.setImageDrawable(context.getDrawable(R.drawable.ic_profile))
            holder.binding.imageViewPhone.setImageDrawable(context.getDrawable(R.drawable.ic_call_green))
            holder.binding.imageViewLocation.setImageDrawable(context.getDrawable(R.drawable.ic_location))

            holder.binding.textViewTopLabel.text = "My Phone Number is"
            holder.binding.textTopValue.text = "${user.phone}"
        }

    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun getCards(): ArrayList<Card> {
        return cards
    }

    inner class ViewHolder(val binding: ItemSpotBinding) : RecyclerView.ViewHolder(binding.root)

}
