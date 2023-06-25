package com.example.buildingblocks.data.model.retrofit
import javax.inject.Inject

class StockRemoteDataSource @Inject constructor(
    private val itemServire: ItemService
):BaseDataSource(){
    suspend fun getStock(name:String) = getResult {
        itemServire.getStockByName(name)
    }
}