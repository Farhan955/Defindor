package com.techoship.defindor.Activities.Auth

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.idealogics.eatlaa.Utils.SharedPref
import com.techoship.defindor.R
import com.techoship.defindor.Utils.Functions
import com.techoship.defindor.databinding.ActivityEmailBinding


class EmailActivity : AppCompatActivity() {
    val context = this
    private val sp = SharedPref(context)

    lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val slide = Slide()
        slide.setSlideEdge(Gravity.RIGHT)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun btnNext(view: View) {
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()

        if (!Functions.isValidEmailAddress(email)) {
            Toast.makeText(this, "invalid email address", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Functions.isValidPhone(phone)) {
            Toast.makeText(this, "invalid phone number", Toast.LENGTH_SHORT).show()
            return
        }


        sp.save("email", email)
        sp.save("phone", phone)

        startActivity(Intent(context, OtpActivity::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }


    override fun onBackPressed() {
        super.onBackPressed()
//        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        Animatoo.animateZoom(context)

    }

    fun ivBack(view: View) {
        onBackPressed()
    }
}