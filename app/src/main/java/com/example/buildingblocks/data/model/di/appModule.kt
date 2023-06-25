package com.example.buildingblocks.data.model.di
import android.content.Context
import com.example.buildingblocks.data.model.local_db.ItemDatabase
import com.example.buildingblocks.data.model.retrofit.ItemService
import com.example.buildingblocks.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class appModule {
    @Provides
    @Singleton
    fun provideItemLocalDatabase(@ApplicationContext appContext:Context):ItemDatabase =
        ItemDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideItemDao(database:ItemDatabase) = database.itemsDao()

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext:Context):Context = appContext


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()


    @Provides
    fun provideItemService(retrofit: Retrofit): ItemService = retrofit.create(ItemService::class.java)
}