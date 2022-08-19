package com.techoship.defindor.Activities.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.idealogics.eatlaa.Utils.SharedPref
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    val context = this
    private val sp = SharedPref(context)
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun btnNext(view: View) {

        val fName = binding.etFName.text.toString().trim()
        val mName = binding.etMName.text.toString().trim()
        val lName = binding.etLName.text.toString().trim()

        if (fName.isEmpty()) {
            Toast.makeText(this, "please give first name", Toast.LENGTH_SHORT).show()
            return
        }
        if (fName.length < 3) {
            Toast.makeText(this, "first name too short", Toast.LENGTH_SHORT).show()
            return
        }
        if (lName.isEmpty()) {
            Toast.makeText(this, "please give last name", Toast.LENGTH_SHORT).show()
            return
        }

        sp.save("fName",fName)
        sp.save("mName",mName)
        sp.save("lName",lName)

        startActivity(Intent(context, EmailActivity::class.java))
//        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        Animatoo.animateShrink(context)
    }

    fun ivBack(view: View) {
        onBackPressed()
    }
}