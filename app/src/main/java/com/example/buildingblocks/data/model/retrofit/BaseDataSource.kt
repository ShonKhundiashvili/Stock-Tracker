package com.example.buildingblocks.data.model.retrofit
import il.co.syntax.finalkotlinproject.utils.Resource
import retrofit2.Response


abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val result = call()
            if (result.isSuccessful) {
                val body = result.body()
                if (body != null) {
                    return Resource.success(body)
                } else {javaClass
                    return Resource.error("Data is null")
                }
            } else if (result.code() == 404) {
                return Resource.error("Requested resource not found")
            } else {
                return Resource.error(
                    "Network call has failed for the following reason: " +
                            "${result.message()} ${result.code()}"
                )
            }
        } catch (e: Exception) {
            return Resource.error(
                "Network call has failed for the following reason: " +
                        (e.localizedMessage ?: e.toString())
            )
        }
    }

}