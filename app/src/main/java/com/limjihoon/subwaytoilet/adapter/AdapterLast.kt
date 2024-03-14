package com.limjihoon.subwaytoilet.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.limjihoon.subwaytoilet.data.AdapterData
import com.limjihoon.subwaytoilet.data.StationDataSearch
import com.limjihoon.subwaytoilet.databinding.RecyclerViewLastBinding

class AdapterLast(var context: Context, var adapterData:List<AdapterData>) : Adapter<AdapterLast.VH>() {

    inner class VH(var binding:RecyclerViewLastBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val laoutInflater =LayoutInflater.from(context)
        val binding =RecyclerViewLastBinding.inflate(laoutInflater,parent,false)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return adapterData.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val last : AdapterData = adapterData[position]
        holder.binding.tvInout.text = last.diapExchNum
        holder.binding.tvMsg.text = last.dtlLoc
        holder.binding.tvName.text = last.lnCd
        holder.binding.tvNumber.text = last.gateInotDvNm
        Log.d("aqqq",last.diapExchNum+"asd")


    }
}