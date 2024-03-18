package com.limjihoon.subwaytoilet.network

import com.limjihoon.subwaytoilet.data.Accc
import com.limjihoon.subwaytoilet.data.KakaoData

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
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
    @Headers("Authorization: KakaoAK 3f16c86dce6a4075f70b6034a4edcd01")
    @GET("/v2/local/search/keyword.json?sort=distance")
    fun kakoDataSearch(@Query("query") query:String,@Query("x") longitude:String,@Query("y") latitude:String ) : Call<KakaoData>

}