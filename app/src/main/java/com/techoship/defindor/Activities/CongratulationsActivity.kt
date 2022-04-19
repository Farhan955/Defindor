package com.techoship.defindor.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.techoship.defindor.Activities.Auth.AllDoneActivity
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivityCongratulationsBinding

class CongratulationsActivity : AppCompatActivity() {
    val context = this
    lateinit var binding: ActivityCongratulationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongratulationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun btnYes(view: View) {
        startActivity(Intent(context, HomeActivity::class.java))
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