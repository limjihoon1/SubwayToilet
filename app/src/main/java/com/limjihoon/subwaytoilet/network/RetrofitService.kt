package com.limjihoon.subwaytoilet.network

import com.limjihoon.subwaytoilet.data.KakaoSearchPlace
import com.limjihoon.subwaytoilet.data.alldatapl
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    @Headers("Authorization: KakaoAK 3f16c86dce6a4075f70b6034a4edcd01")
    @GET("/v2/local/search/keyword.json?sort=distance")
    fun kakaoSearchPlaceToString3(@Query("query") query:String, @Query("x") longitude:String, @Query("y") latitude:String ) : Call<KakaoSearchPlace>
    @GET("$2a$10"+"$"+"DajG.Y9Y3HD8diIuof5uUuDnkZLrm7zRE/U4jq/xlPX9d9yCi8D8O&format=json")
    fun dataget(@Query("lnCd") lnCd:String, @Query("railOprIsttCd")railOprIsttCd:String,@Query("stinCd")stinCd:String) :Call<alldatapl>

}