package com.techoship.defindor.Activities.Auth

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivitySignIn2Binding

class SignInActivity2 : AppCompatActivity() {
    val context = this

    lateinit var binding: ActivitySignIn2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignIn2Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun btnNext(view: View) {
        startActivity(Intent(context, KeyRecoveryActivity::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.left_out);


    }

    fun tvForgot(view: View) {

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_security)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.findViewById<TextView>(R.id.tv_submit).setOnClickListener {

            startActivity(Intent(context, KeyRecoveryActivity::class.java))

            dialog.dismiss()
        }
        dialog.show()

    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    fun ivBack(view: View) {
        onBackPressed()
    }

}