package com.example.buildingblocks.data.model

import com.example.buildingblocks.Item
import com.google.gson.annotations.SerializedName

data class AlphaResponse(
    @SerializedName("Meta Data")
    val responseMetaData:ResponseMetaData,
    @SerializedName("Time Series (Daily)")
    val responseTimeSeries:Map<String, Item>
    )


data class ResponseTimeSeries (
    @SerializedName("1. open")
    val open:String,
    @SerializedName("2. high")
    val high:String,
    @SerializedName("3. low")
    val low:String,
    @SerializedName("4. close")
    val close:String,
    @SerializedName("5. adjusted close")
    val adjustedClose:String,
    @SerializedName("6. volume")
    val volume:String,
    @SerializedName("7. dividend amount")
    val dividendAmount:String,
    @SerializedName("8. split coefficient")
    val splitCoefficient:String,
    )

data class ResponseMetaData(
    @SerializedName("1. Information")
    val information:String,
    @SerializedName("2. Symbol")
    val symbol:String,
    @SerializedName("3. Last Refreshed")
    val lastRefreshed:String,
    @SerializedName("4. Output Size")
    val outputSize:String,
    @SerializedName("5. Time Zone")
    val timeZone:String
    )
