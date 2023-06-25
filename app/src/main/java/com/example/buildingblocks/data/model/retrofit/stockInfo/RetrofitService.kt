package com.example.buildingblocks.data.model.retrofit.stockInfo
import com.example.buildingblocks.data.model.retrofit.stockInfo.model.StockUrlData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("all?filter_entities=true&language=en&api_token=DqyTrOBwOSA6HQYJNsFOaZXQVyxggv2Cavd5IVOX")
    suspend fun getUrlForSpecifiedStock(@Query("symbols")name:String): Response<StockUrlData>
}