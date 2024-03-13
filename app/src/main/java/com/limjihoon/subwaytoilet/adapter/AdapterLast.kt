package com.limjihoon.subwaytoilet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.limjihoon.subwaytoilet.data.AdapterData
import com.limjihoon.subwaytoilet.databinding.RecyclerViewLastBinding

class AdapterLast(var context: Context, var adapterData:List<AdapterData>) : Adapter<AdapterLast.VH>() {

    inner class VH(var binding:RecyclerViewLastBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val laoutInflater =LayoutInflater.from(context)
        var binding =RecyclerViewLastBinding.inflate(laoutInflater)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return adapterData.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

    }
}