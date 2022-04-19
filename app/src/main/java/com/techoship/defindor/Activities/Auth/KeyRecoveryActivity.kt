package com.techoship.defindor.Activities.Auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivityKeyRecoveryBinding

class KeyRecoveryActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityKeyRecoveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeyRecoveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    fun ivBack(view: View) {
        onBackPressed()
    }

}