package com.techoship.defindor.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.techoship.defindor.databinding.ActivityCoinDetailsBinding

class CoinDetailsActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityCoinDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun ivBack(view: View) {
        onBackPressed()
    }
}