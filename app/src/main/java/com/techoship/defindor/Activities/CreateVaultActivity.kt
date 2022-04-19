package com.techoship.defindor.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.techoship.defindor.Activities.Auth.AllDoneActivity
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ActivityCreateAccountBinding
import com.techoship.defindor.databinding.ActivityCreateVaultBinding

class CreateVaultActivity : AppCompatActivity() {
    val context = this
    lateinit var binding: ActivityCreateVaultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateVaultBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun btnNext(view: View) {

        startActivity(Intent(context, CongratulationsActivity::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    fun tvSelect(view: View) {

        if (binding.layoutSearch.isVisible) {
            binding.layoutSearch.visibility = View.GONE
            binding.tvSelectWallet.setCompoundDrawablesWithIntrinsicBounds(0,
                0,
                R.drawable.ic_baseline_keyboard_arrow_down_24,
                0)
        } else {
            binding.layoutSearch.visibility = View.VISIBLE
            binding.tvSelectWallet.setCompoundDrawablesWithIntrinsicBounds(0,
                0,
                R.drawable.ic_arrow_up,
                0)
        }
    }

    fun ivBack(view: View) {}


}