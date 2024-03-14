package com.limjihoon.subwaytoilet.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RtrofitHelper {
    companion object{
        fun getRetrofitInstance(baseUrl:String): Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl("$baseUrl")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

    }
}