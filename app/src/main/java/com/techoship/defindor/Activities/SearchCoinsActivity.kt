package com.techoship.defindor.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.techoship.defindor.Activities.Auth.AllDoneActivity
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivitySearchCoinsBinding

class SearchCoinsActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivitySearchCoinsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCoinsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun layoutItem(view: View) {
        startActivity(Intent(context, CoinDetailsActivity::class.java))
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