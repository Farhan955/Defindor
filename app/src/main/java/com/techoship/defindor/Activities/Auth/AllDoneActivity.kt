package com.techoship.defindor.Activities.Auth

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.techoship.defindor.Activities.CreateVaultActivity
import com.techoship.defindor.R

class AllDoneActivity : AppCompatActivity() {
    val context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_done)
    }

    fun btnFreeTrial(view: View) {

        showDialog()

    }

    private fun showDialog() {


        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_free_service)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        dialog.findViewById<TextView>(R.id.tv_done).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun btnNext(view: View) {

        startActivity(Intent(context, CreateVaultActivity::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    fun ivBack(view: View) {onBackPressed()}

}