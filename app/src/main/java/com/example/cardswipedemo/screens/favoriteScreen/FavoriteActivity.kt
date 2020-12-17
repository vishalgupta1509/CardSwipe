package com.example.cardswipedemo.screens.favoriteScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.cardswipedemo.CommonViewModel
import com.example.cardswipedemo.R
import com.example.cardswipedemo.databinding.ActivityFavoriteBinding
import com.example.cardswipedemo.models.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: CommonViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite)

        getFavoriteList()
    }

    private fun getFavoriteList() {
        viewModel.getFavoriteList().observe(this, Observer {
            if(it.isNotEmpty()) {
                binding.textNoFav.visibility = GONE
                for(element in it) {
                    Log.e(FavoriteActivity::class.simpleName, "Email ${element.email}")
                    val adapter = FavoriteItemAdapter(it as ArrayList<User>)
                    binding.recyclerViewFavorite.adapter = adapter
                }
            } else {
                binding.textNoFav.visibility = VISIBLE
            }
        })
    }

}