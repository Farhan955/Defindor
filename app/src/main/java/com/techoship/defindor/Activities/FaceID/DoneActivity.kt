package com.techoship.defindor.Activities.Auth.FaceID

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.techoship.defindor.Activities.Auth.AlmostDoneActivity
import com.techoship.defindor.R

class DoneActivity : AppCompatActivity() {

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done)
    }


    fun btnNext(view: View) {
        startActivity(Intent(context, AlmostDoneActivity::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


}