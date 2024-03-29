package com.techoship.defindor.Activities.Auth.FaceID

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techoship.defindor.Models.Exchange
import com.techoship.defindor.Models.ExchangeResponse
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivityFaceIdBinding
import com.techoship.testretrofit2.Api.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FaceIdActivity : AppCompatActivity() {
    val context = this
    lateinit var binding: ActivityFaceIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceIdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickListeners()
    }

    private fun clickListeners() {


    }


    fun ivFaceId(view: View) {


        startActivity(Intent(context, DoneActivity::class.java))
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