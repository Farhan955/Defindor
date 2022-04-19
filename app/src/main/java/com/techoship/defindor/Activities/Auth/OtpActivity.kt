package com.techoship.defindor.Activities.Auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.techoship.defindor.Activities.Auth.FaceID.FaceIdActivity
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivityOtpBinding

class OtpActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    fun btnNext(view: View) {
        startActivity(Intent(context, FaceIdActivity::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    fun ivBack(view: View) {
        onBackPressed()
    }
}