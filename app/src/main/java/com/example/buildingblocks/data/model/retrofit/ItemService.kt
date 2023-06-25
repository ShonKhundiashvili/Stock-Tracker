package com.example.buildingblocks.data.model.retrofit
import com.example.buildingblocks.data.model.AlphaResponse
import com.example.buildingblocks.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemService {
    @GET("query?function=TIME_SERIES_DAILY_ADJUSTED&apikey=${Constants.API_KEY}")
    suspend fun getStockByName(@Query("symbol")name:String): Response<AlphaResponse>
}