package com.limjihoon.subwaytoilet.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.gson.Gson

import com.limjihoon.subwaytoilet.data.Body
import com.limjihoon.subwaytoilet.data.StationDataSearch
import com.limjihoon.subwaytoilet.databinding.RecyclerViewLastBinding

class AdapterLast(var context: Context, var body:List<Body>) : Adapter<AdapterLast.VH>() {

    inner class VH(var binding:RecyclerViewLastBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater =LayoutInflater.from(context)
        val binding =RecyclerViewLastBinding.inflate(layoutInflater,parent,false)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return body.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val last = body[position]
        holder.binding.tvInout.text = last.gateInotDvNm
        holder.binding.tvMsg.text = last.dtlLoc
        holder.binding.tvName.text = last.mlFmlDvNm
        holder.binding.tvNumber.text = last.exitNo
        Log.d("aqqq",last.exitNo+"asd")


    }
}