package com.example.buildingblocks
import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@TypeConverters(Converters::class)
@Entity(tableName = "stocks_new")
data class Item (
    @SerializedName("1. open")
    val open:String,
    @SerializedName("2. high")
    val high:String,
    @SerializedName("symbol")
    val symbol:String,
    @SerializedName("3. low")
    val low:String,
    @SerializedName("4. close")
    val close:String,
    @SerializedName("6. volume")
    val volume:String,
    val image: String? = "",
): Parcelable {
        @PrimaryKey(autoGenerate = true)
        var id : Int = 0
    }


