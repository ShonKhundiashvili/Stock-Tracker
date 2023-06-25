package com.example.buildingblocks.data.model.retrofit.stockInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StockInfo {
    const val BASE_URL = "https://api.marketaux.com/v1/news/"
    fun retrofitService() :RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}


