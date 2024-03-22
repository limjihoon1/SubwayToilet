package com.limjihoon.subwaytoilet.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.gson.Gson
import com.limjihoon.subwaytoilet.activites.DitailActivity
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

        holder.binding.tvInout.text=last.dtlLoc
        holder.binding.tvMsg.text="${last.mlFmlDvNm} 화장실"
        holder.binding.tvNumber.text="${last.lnCd} 호선"
        holder.binding.root.setOnClickListener {
            val intent = Intent(context,DitailActivity::class.java)

            //장소 정보에 대한 데이터를 추가로보냄 [ 객체는 전송 불가 -> json 문자열로 변환 하여 전송 ]
            val gson = Gson()
            val jsonString:String = gson.toJson(last) // 객체 --> json String
            intent.putExtra("place",jsonString)
            context.startActivity(intent)
        }



    }

}