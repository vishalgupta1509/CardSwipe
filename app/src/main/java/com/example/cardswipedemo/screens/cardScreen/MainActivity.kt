package com.example.cardswipedemo.screens.cardScreen

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.cardswipedemo.CommonViewModel
import com.example.cardswipedemo.R
import com.example.cardswipedemo.databinding.ActivityMainBinding
import com.example.cardswipedemo.models.Card
import com.example.cardswipedemo.network.Status
import com.example.cardswipedemo.screens.favoriteScreen.FavoriteActivity
import com.yuyakaido.android.cardstackview.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity(), CardStackListener {
    private lateinit var mBinding: ActivityMainBinding
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private var adapter: CardStackAdapter? = null
    private val viewModel: CommonViewModel by viewModel()
    private var cardList: ArrayList<Card> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        callGetCardDetailAPI()
    }

    private fun initialize() {
        if (cardList.isNotEmpty()) {
            adapter = CardStackAdapter(this, cardList)

            manager.setStackFrom(StackFrom.None)
            manager.setVisibleCount(3)
            manager.setTranslationInterval(8.0f)
            manager.setScaleInterval(0.95f)
            manager.setSwipeThreshold(0.3f)
            manager.setMaxDegree(20.0f)
            manager.setDirections(Direction.HORIZONTAL)
            manager.setCanScrollHorizontal(true)
            manager.setCanScrollVertical(true)
            manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            manager.setOverlayInterpolator(LinearInterpolator())
            mBinding.cardStackView.layoutManager = manager
            mBinding.cardStackView.adapter = adapter
            mBinding.cardStackView.itemAnimator.apply {
                if (this is DefaultItemAnimator) {
                    supportsChangeAnimations = false
                }
            }
        }
    }

    private fun addToFavorites(cards: ArrayList<Card>) {
        if (cards.isNotEmpty()) {

            val user = cards[cards.size - 1].results[0].user

            for (i in 0 until cards.size) {
                Log.e(MainActivity::class.java.simpleName, "CARD:: ${user.name.first}")
            }

            Glide.with(this)
                .asBitmap()
                .load(user.picture)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val fileName = "${user.name.first}_${user.gender}.png"
                        val imageFile =
                            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)
                        try {
                            val fileOutputStream =
                                FileOutputStream(imageFile)
                            resource.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                            fileOutputStream.flush()
                            fileOutputStream.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        if (imageFile.exists()) {
                            user.picture = imageFile.absolutePath

                            Log.e(MainActivity::class.java.simpleName, "Path: ${user.picture}")

                            viewModel.addToFavorites(user).observe(this@MainActivity, Observer {
                                if (it > -1) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Added to Favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    callGetCardDetailAPI()
                                }
                            })
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    private fun callGetCardDetailAPI() {
        viewModel.getCardData().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    mBinding.progressBar.visibility = GONE
                    cardList.add(it.data!!)
                    initialize()
                }
                Status.ERROR -> {
                    mBinding.progressBar.visibility = GONE
                    Log.e(MainActivity::class.simpleName, "${it.message}")
                }
                else -> {
                    mBinding.progressBar.visibility = VISIBLE
                    Log.e(MainActivity::class.simpleName, "LOADING")
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Log.d("CardStackView", "onCardDisappeared: ($position)")
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction!!.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction?) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (direction!! == Direction.Left) {
            callGetCardDetailAPI()
        } else {
            addToFavorites(adapter!!.getCards())
        }
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Log.d("CardStackView", "onCardAppeared: ($position)")
    }

    override fun onCardRewound() {
    }
}