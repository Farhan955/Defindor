package com.techoship.defindor.Activities.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    val context = this
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun btnNext(view: View) {
        startActivity(Intent(context, EmailActivity::class.java))
//        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        Animatoo.animateShrink(context)
    }

    fun ivBack(view: View) {
        onBackPressed()
    }
}