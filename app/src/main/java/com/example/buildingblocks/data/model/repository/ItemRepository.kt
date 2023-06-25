package com.example.buildingblocks.data.model.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.MutableLiveData
import com.example.buildingblocks.Item
import com.example.buildingblocks.data.model.local_db.ItemDao
import com.example.buildingblocks.data.model.models.Stock
import com.example.buildingblocks.data.model.retrofit.StockRemoteDataSource
import com.example.buildingblocks.data.model.retrofit.stockInfo.StockInfo
import com.example.buildingblocks.data.model.retrofit.stockInfo.model.StockUrlData
import il.co.syntax.finalkotlinproject.utils.performFetchingAndSaving
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val localDataSource: ItemDao,
    private val context: Context,
    private val remoteDataSource: StockRemoteDataSource
) {
    private val stockInfo = StockInfo.retrofitService()
    val mutableLiveData = MutableLiveData<Boolean>()
    private val ioScope = CoroutineScope(Dispatchers.IO)


    fun getItems(): MutableList<Item>? {
        return localDataSource.getItems()
    }

    fun addItem(item: Item) {
        localDataSource.addItem(item)
    }

    fun deleteItem(item: Item) {
        localDataSource.deleteItem(item)
    }


    fun getStock(name: String, image: String) = performFetchingAndSaving(
        { localDataSource.getItem(name) },
        { remoteDataSource.getStock(name) },
        { data ->
            if(data.responseTimeSeries == null) {
                mutableLiveData.postValue(false)
            }
            else {
                data.responseTimeSeries?.let {
                    var key = ""
                    for (x in it.keys) {
                        key = x
                        break
                    }
                    it[key]?.let { item ->
                        val newItem = Item(
                            open = item.open,
                            high = item.high,
                            close = item.close,
                            low = item.low,
                            volume = item.volume,
                            symbol = data.responseMetaData.symbol,
                            image = image
                        )
                        localDataSource.addItem(item = newItem)
                    }
                }
            }
        }
    )

    suspend fun getUrlForSpecifiedStock(stockSymbol: String): Flow<StockUrlData> {
        return flow {
            val response = stockInfo.getUrlForSpecifiedStock(stockSymbol)
            if(response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            }
        }
    }
}