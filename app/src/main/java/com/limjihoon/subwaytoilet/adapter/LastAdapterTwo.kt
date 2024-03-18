package com.limjihoon.subwaytoilet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.limjihoon.subwaytoilet.data.Accc
import com.limjihoon.subwaytoilet.data.Body
import com.limjihoon.subwaytoilet.databinding.RecyclerViewLastBinding

class LastAdapterTwo(var context: Context, var bodyList: List<Body>) : Adapter<LastAdapterTwo.VH>() {

    inner class VH(val binding: RecyclerViewLastBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(context)
        var binding = RecyclerViewLastBinding.inflate(layoutInflater,parent,false)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return bodyList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val last = bodyList[position]
        holder.binding.tvName.text=last.exitNo
        holder.binding.tvInout.text=last.dtlLoc
        holder.binding.tvMsg.text=last.mlFmlDvNm
        holder.binding.tvNumber.text=last.lnCd


    }

}