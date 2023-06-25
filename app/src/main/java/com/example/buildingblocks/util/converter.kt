package com.example.buildingblocks

import android.net.Uri
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters(UriTypeConverter::class)
class Converters {
    // Create an instance of UriTypeConverter
    private val uriTypeConverter = UriTypeConverter()

    // Define the converter methods using UriTypeConverter
    @TypeConverter
    fun fromUri(uri: Uri?): String? = uriTypeConverter.fromUri(uri)

    @TypeConverter
    fun toUri(string: String?): Uri? = uriTypeConverter.toUri(string)
}

class UriTypeConverter {
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(string: String?): Uri? {
        return if (string == null) null else Uri.parse(string)
    }
}
