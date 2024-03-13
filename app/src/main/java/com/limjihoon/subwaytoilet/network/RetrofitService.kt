package com.limjihoon.subwaytoilet.network


import com.limjihoon.subwaytoilet.data.Alldatapl

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {
    var sival: String
        get() = "DajG.Y9Y3HD8diIuof5uUuDnkZLrm7zRE/U4jq/xlPX9d9yCi8D8O"
        set(value) = TODO()

    @GET("DajG.Y9Y3HD8diIuof5uUuDnkZLrm7zRE/U4jq/xlPX9d9yCi8D8O")
    fun dataget(@Query("format") format:String, @Query("railOprIsttCd")railOprIsttCd:String, @Query("lnCd") lnCd:String,@Query("stinCd")stinCd:String) :Call<Alldatapl>

    @GET("/openapi/convenientInfo/stationToilet?")

    fun datagetToString(@Query("serviceKey") serviceKey:String,@Query("format") format:String, @Query("railOprIsttCd")railOprIsttCd:String, @Query("lnCd") lnCd:String,@Query("stinCd")stinCd:String) :Call<String>




}