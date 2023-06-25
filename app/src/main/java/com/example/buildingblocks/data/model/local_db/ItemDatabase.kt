package com.example.buildingblocks.data.model.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.buildingblocks.Item

@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)


abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemsDao() : ItemDao

    companion object {
        @Volatile
        private var instance:ItemDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(this){
            Room.databaseBuilder(context.applicationContext,ItemDatabase::class.java, "stocks_new_v4")
                .allowMainThreadQueries()
                .build()
        }
    }
}