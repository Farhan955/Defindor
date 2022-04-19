package com.techoship.defindor.Activities.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.techoship.defindor.Activities.SearchCoinsActivity
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    val context = this
    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun ivFaceId(view: View) {
        startActivity(Intent(context, SignInActivity2::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    fun ivBack(view: View) {
        onBackPressed()
    }
}