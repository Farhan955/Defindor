package com.techoship.defindor.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.techoship.defindor.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    val context = this
    lateinit var binding: ActivitySettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun ivBack(view: View) {
        onBackPressed()
    }

    fun tvMyAccount(view: View) {}
}