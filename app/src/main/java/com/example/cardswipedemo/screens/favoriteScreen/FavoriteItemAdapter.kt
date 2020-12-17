package com.example.cardswipedemo.screens.favoriteScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardswipedemo.databinding.RecyclerItemFavoriteBinding
import com.example.cardswipedemo.models.User

class FavoriteItemAdapter(private val favoriteList: ArrayList<User>): RecyclerView.Adapter<FavoriteItemAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = RecyclerItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = favoriteList[position]
        holder.binding.textViewName.text = "${user.name.first} ${user.name.last}"
        holder.binding.textViewAddress.text = "${user.location.street}, ${user.location.city}, ${user.location.state}"
        holder.binding.textViewPhone.text = "${user.phone}"

        Glide.with(holder.binding.imageView).load(user.picture).into(holder.binding.imageView)
    }

    inner class UserViewHolder(val binding: RecyclerItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)
}