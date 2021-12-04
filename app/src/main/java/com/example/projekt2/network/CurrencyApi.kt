package com.example.projekt2.network

import com.example.projekt2.data.currencyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
        @GET("/v1/latest")
        fun getCurrency(
            @Query("access_key") access_key: String?,
        ): Call<currencyData?>?
    }