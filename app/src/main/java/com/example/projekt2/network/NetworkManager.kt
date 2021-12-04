package com.example.projekt2.network

import com.example.projekt2.data.currencyData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val currencyApi: CurrencyApi

    private const val SERVICE_URL = "http://api.exchangeratesapi.io"
    private const val APP_ID = "35901288dbfd728469b19baaaeda6848"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        currencyApi = retrofit.create(CurrencyApi::class.java)
    }

    fun getCurrency(): Call<currencyData?>? {
        return currencyApi.getCurrency(APP_ID)
    }




}