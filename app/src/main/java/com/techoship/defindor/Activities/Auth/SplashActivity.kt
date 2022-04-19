package com.techoship.defindor.Activities.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _init_()

    }

    private fun _init_() {
        Glide.with(context)
            .load(R.drawable.splash_gif)
            .into(binding.imageView)


        Handler().postDelayed({
            startActivity(Intent(context,StartActivity::class.java))
            finish()
                              },1500)
    }

}