package com.techoship.defindor.Activities.Auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivityStartBinding


class StartActivity : AppCompatActivity() {
    val context = this
    private lateinit var binding: ActivityStartBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _init_()
    }

    private fun _init_() {


//        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_up)
//        binding.imageView.startAnimation(slideAnimation)


        Handler().postDelayed({

            binding.ivWallet.visibility = View.VISIBLE

            YoYo.with(Techniques.ZoomInUp)
                .duration(1000)
                .repeat(0)
                .playOn(binding.ivWallet)

            showLogo()

        }, 200)


    }

    private fun showLogo() {

        Handler().postDelayed({

            binding.imageView.visibility = View.VISIBLE

            YoYo.with(Techniques.SlideInUp)
                .duration(1500)
                .repeat(0)
                .playOn(binding.imageView)

            showButtons()

        }, 1000)


    }

    private fun showButtons() {

        Handler().postDelayed({

            binding.btnExisting.visibility = View.VISIBLE
            binding.btnNew.visibility = View.VISIBLE

            YoYo.with(Techniques.SlideInUp)
                .duration(1200)
                .repeat(0)
                .playOn(binding.btnExisting)

            YoYo.with(Techniques.SlideInUp)
                .duration(1500)
                .repeat(0)
                .playOn(binding.btnNew)


            hideWallet()


        }, 1300)


    }

    private fun hideWallet() {

        Handler().postDelayed({
            YoYo.with(Techniques.FadeOut)
                .duration(1500)
                .repeat(0)
                .playOn(binding.ivWallet)

        }, 1000)

    }

    fun btnNewAccount(view: View) {
        startActivity(Intent(context, CreateAccountActivity::class.java))
    }

    fun btnExisting(view: View) {
        startActivity(Intent(context, SignInActivity::class.java))

    }


}