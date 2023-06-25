package com.example.buildingblocks.data.model.local_db
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import com.example.buildingblocks.Item


@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: Item)

    @Delete()
    fun deleteItem(items: Item)

    @Query("DELETE FROM stocks_new")
    fun deleteAllItems()

    @Update()
    fun updateItem(item:Item)

    @Query("SELECT * FROM stocks_new")
    fun getItems(): MutableList<Item>?

    @Query("SELECT * FROM stocks_new where symbol LIKE :symbol")
    fun getItem(symbol:String):LiveData<Item>
}