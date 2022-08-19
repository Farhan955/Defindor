package com.techoship.defindor.Activities.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.idealogics.eatlaa.Utils.SharedPref
import com.techoship.defindor.Activities.Auth.FaceID.FaceIdActivity
import com.techoship.defindor.Models.Profile
import com.techoship.defindor.Models.SignupResponse
import com.techoship.defindor.R
import com.techoship.defindor.Utils.Const.TAG
import com.techoship.defindor.Utils.Functions
import com.techoship.defindor.databinding.ActivityOtpBinding
import com.techoship.testretrofit2.Api.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpActivity : AppCompatActivity() {

    val context = this
    val sp = SharedPref(context)
    lateinit var binding: ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    fun btnNext(view: View) {
//        startActivity(Intent(context, FaceIdActivity::class.java))
//        overridePendingTransition(R.anim.right_in, R.anim.left_out)

        val emailOtp = binding.pinEmail.text.toString()
        val phoneOtp = binding.pinPhone.text.toString()

        Log.d(TAG, "btnNext: $emailOtp")
        Log.d(TAG, "btnNext: $phoneOtp")
        if (!emailOtp?.equals("000000")!!) {
            Toast.makeText(this, "invalid email OTP", Toast.LENGTH_SHORT).show()
            return
        }

        if (!phoneOtp?.equals("0000")!!) {
            Toast.makeText(this, "invalid phone OTP", Toast.LENGTH_SHORT).show()
            return
        }

        registerUser()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    fun ivBack(view: View) {
        onBackPressed()
    }


    private fun registerUser() {
        Functions.loadingDialog(context,true)
        val profile = Profile(sp["fName"], sp["mName"], sp["lName"], sp["email"], sp["phone"])
        val apiCall = ApiUtils.getApiSerevice(context).registerProfile(profile)
        apiCall.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                Functions.loadingDialog(context,false)

                if (response.body() != null && response.isSuccessful) {

                    if (response.body()!!.success == true) {
                        sp.saveSignUpResponse(response.body()!!)
                        sp.saveProfile(profile)
                        sp.save("status", "verified")
                        var intent = Intent(context, FaceIdActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            context,
                            "Error: " + response.body().toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }


                } else {

                    Toast.makeText(context, "Error: " + response.message(), Toast.LENGTH_SHORT)
                        .show()
                    Functions.loadingDialog(context,false)

                }

            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(context, "Failed:  ${t.message}", Toast.LENGTH_LONG).show()
                Functions.loadingDialog(context,false)
            }
        })
    }

}