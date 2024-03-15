package com.limjihoon.subwaytoilet.network

import com.limjihoon.subwaytoilet.data.Accc

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/openapi/convenientInfo/stationToilet")
    fun getLastApi(
        @Query("serviceKey") serviceKey:String,
        @Query("format") format:String,
        @Query("railOprIsttCd") railOprIsttCd:String,
        @Query("lnCd") lnCd:String,
        @Query("stinCd") stinCd:String
    ):Call<Accc>


}