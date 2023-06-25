package com.example.buildingblocks.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildingblocks.Item
import com.example.buildingblocks.data.model.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor (private val repository: ItemRepository) : ViewModel() {
    private val _symbol = MutableLiveData<String>()
    private val image = MutableStateFlow("")
    val specificStockUrl = MutableLiveData<String>()

    val items : MutableLiveData<MutableList<Item>>? by lazy {
        MutableLiveData<MutableList<Item>>()
    }

    val symbol = Transformations.switchMap(_symbol) {
        repository.getStock(it, image.value)
    }

    init {
        getItems()
    }

    fun addItem(item: Item) {
        repository.addItem(item)
        repository.getItems()?.let { itemsList ->
            items?.postValue(itemsList)
        }
    }

    fun printData() {
        println(repository.getItems());
    }

    fun getUrlForSpecifiedStock(stockSymbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUrlForSpecifiedStock(stockSymbol).collect { result ->
                val dataList = result.data
                if (dataList.isNotEmpty()) {
                    specificStockUrl.postValue(dataList[0].url ?: "")
                } else {
                    specificStockUrl.postValue("")
                }
            }
        }
    }


    fun deleteItemFromRecycler(position: Int) {
        val itemToDelete = items?.value?.get(position)
        itemToDelete?.let { repository.deleteItem(it) }
        repository.getItems()?.let { itemsList ->
            items?.postValue(itemsList)
        }
        items?.value?.removeAt(position)
    }

    fun setSymbol(name:String) {
        _symbol.value = name
    }

    fun setImage(imageUrl: String) {
        image.value = imageUrl
    }

    fun getItems() {
        items?.postValue(repository.getItems())
    }

    fun getItemAtPosition(position: Int): Item? {
        val itemList = items?.value
        return if (position in 0 until (itemList?.size ?: 0)) {
            itemList?.get(position)
        } else {
            null
        }
    }
}