package com.techoship.defindor.Adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.techoship.defindor.Interfaces.SelectExchange
import com.techoship.defindor.Models.Exchange
import com.techoship.defindor.R
import com.techoship.defindor.databinding.ItemCoinSearchBinding
import com.techoship.defindor.databinding.ItemExchangeBinding

/**
 * Created by FA on 1/11/2022.
 */


class ExchangesListAdapter(
    var arrayList: List<Exchange>,
    var context: Context,
    var selectExchange: SelectExchange
) :


    RecyclerView.Adapter<ExchangesListAdapter.VH?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_exchange, parent, false)
        return VH(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.bind(arrayList[position])
        holder.itemView.setOnClickListener {

            selectExchange.itemClick(arrayList[position])

        }

    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val binding = ItemExchangeBinding.bind(itemView)

        fun bind(exchange: Exchange) {

            binding.tvTitle.text = exchange.name
        }

    }


}
