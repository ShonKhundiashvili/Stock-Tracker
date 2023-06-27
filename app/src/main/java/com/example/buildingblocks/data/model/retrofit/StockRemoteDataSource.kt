package com.example.buildingblocks.data.model.retrofit
import android.content.Context
import javax.inject.Inject

class StockRemoteDataSource @Inject constructor(
    private val itemServire: ItemService
):BaseDataSource(){
    suspend fun getStock(name: String, context: Context) = getResult {
        val temp = itemServire.getStockByName(name)
        val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("data_key", temp.body()?.responseTimeSeries == null).apply()
        temp
    }
}