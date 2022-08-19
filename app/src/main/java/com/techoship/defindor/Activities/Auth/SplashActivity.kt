package com.techoship.defindor.Activities.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.bumptech.glide.Glide
import com.idealogics.eatlaa.Utils.SharedPref
import com.techoship.defindor.Activities.Auth.FaceID.FaceIdActivity
import com.techoship.defindor.R
import com.techoship.defindor.Utils.AppSharedPrefEncrypted
import com.techoship.defindor.Utils.Const
import com.techoship.defindor.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    val context = this
    val sp = SharedPref(context)
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
            val status = sp["status"]
            if (status?.isEmpty()!!) {
                startActivity(Intent(context, StartActivity::class.java))
            } else if (status.equals("verified")) {
                startActivity(Intent(context, FaceIdActivity::class.java))
            }
            finish()
        }, 1500)

    }

}