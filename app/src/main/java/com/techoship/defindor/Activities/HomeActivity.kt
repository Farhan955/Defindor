package com.techoship.defindor.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _init_()
    }

    private fun _init_() {

    }

    fun btnAdd(view: View) {
        startActivity(Intent(context, SearchCoinsActivity::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    fun ivSettings(view: View) {
        startActivity(Intent(context, SettingsActivity::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }
}