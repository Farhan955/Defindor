package com.techoship.defindor.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.techoship.defindor.Adapters.ExchangesListAdapter
import com.techoship.defindor.Interfaces.SelectExchange
import com.techoship.defindor.Models.Exchange
import com.techoship.defindor.Models.ExchangeResponse
import com.techoship.defindor.R
import com.techoship.defindor.Utils.Const.TAG
import com.techoship.defindor.databinding.ActivityCreateVaultBinding
import com.techoship.testretrofit2.Api.ApiUtils
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateVaultActivity : AppCompatActivity() {
    val context = this
    lateinit var binding: ActivityCreateVaultBinding
    var filteredList = ArrayList<Exchange>()
    var exchangesList = ArrayList<Exchange>()

    lateinit var adapter: ExchangesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateVaultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _init_()
        retrieveExchanges()
        searchListener()
    }

    private fun _init_() {

        adapter = ExchangesListAdapter(filteredList, context, object : SelectExchange {
            override fun itemClick(exchange: Exchange) {
                createVault()
            }
        })
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
            binding.tvSelectWallet.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_baseline_keyboard_arrow_down_24,
                0
            )
        } else {
            binding.layoutSearch.visibility = View.VISIBLE
            binding.tvSelectWallet.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_arrow_up,
                0
            )
        }
    }

    fun ivBack(view: View) {}


    private fun retrieveExchanges() {
        val apiCall = ApiUtils.getApiSerevice(context).getExchanges()
        apiCall.enqueue(object : Callback<ExchangeResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ExchangeResponse>,
                response: Response<ExchangeResponse>
            ) {

                if (response.body() != null && response.isSuccessful) {

                    filteredList.addAll(response.body()!!.data as ArrayList<Exchange>)
                    exchangesList.addAll(filteredList)
                    binding.rvExchanges.adapter = adapter
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        context, "Error: " + response.body().toString(), Toast.LENGTH_LONG
                    ).show()

                }

            }

            override fun onFailure(call: Call<ExchangeResponse>, t: Throwable) {
                Toast.makeText(context, "Failed:  ${t.message}", Toast.LENGTH_LONG).show()

            }

        })

    }


    private fun searchListener() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterData()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterData() {
        val txt = binding.etSearch.text.toString().trim().lowercase()
        filteredList.clear()
        if (txt.isEmpty()) {
            filteredList.addAll(exchangesList)
        } else {

            for (obj in exchangesList) {
                val s = obj.name.lowercase()
                Log.d(TAG, "filterData: $s " + obj.id)
                if (s.contains(txt)) {
                    filteredList.add(obj)
                }
            }
        }

        adapter.notifyDataSetChanged()


    }


    private fun createVault() {
        val apiCall = ApiUtils.getApiSerevice(context).fetchPosts(token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NCwiZW1haWwiOiJqYW5lMUBlbWFpbC5jb20iLCJmaXJzdE5hbWUiOiJJJ20iLCJsYXN0TmFtZSI6ImRvZSIsInBob25lIjoiMTIzNDU2Nzg2MTIzIiwiaWF0IjoxNjU0OTU0NTYwLCJleHAiOjE2NTUwNDA5NjB9.HShnrX0wGFYu3vT2Osmp0lx4rPjjvKnU3w2sFszO7Pw")

        apiCall.enqueue(object : Callback<Response<Any>> {
            override fun onResponse(call: Call<Response<Any>>, response: Response<Response<Any>>) {
                if (response.body() != null && response.isSuccessful) {
                    Toast.makeText(context, "Done: " + response.message(), Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(context, "Error: " + response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Response<Any>>, t: Throwable) {
                Toast.makeText(context, "Failed:  ${t.message}", Toast.LENGTH_LONG).show()

            }
        })

    }
}

