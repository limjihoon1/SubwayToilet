package com.limjihoon.subwaytoilet.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitHelper {

    companion object{
        fun getRetrofitAll(baseYrl:String):Retrofit{
            val retrofit =Retrofit.Builder()
                .baseUrl(baseYrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit

        }
    }
}