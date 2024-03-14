package com.limjihoon.subwaytoilet.network

import com.limjihoon.subwaytoilet.data.AdapterData
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET()
    fun getLastApi():Call<AdapterData>
}