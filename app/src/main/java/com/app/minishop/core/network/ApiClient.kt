package com.app.minishop.core.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
    val networkService: NetworkService,
    val gson: Gson
) {
    companion object {
        val JSON = "application/json; charset=utf-8".toMediaType()
        private const val TAG = "ApiClient"
    }

    inline fun <reified T> get(
        endpoint: String,
        params: Map<String, String> = emptyMap()
    ): Flow<ApiResult<T>> {
        val type: Type = object : TypeToken<T>() {}.type
        return flow {
            emit(ApiResult.Loading())
            try {
                val response = networkService.get(endpoint, params)
                if (!response.isSuccessful) throw HttpException(response)
                val body = response.body()?.string() ?: throw IOException("Empty response")
                emit(ApiResult.Success(gson.fromJson(body, type)))
            } catch (e: Exception) {
                emit(mapException(e))
            }
        }
    }

    inline fun <reified T> post(
        endpoint: String,
        body: Any? = null
    ): Flow<ApiResult<T>> {
        val type: Type = object : TypeToken<T>() {}.type
        val requestBody = gson.toJson(body ?: emptyMap<String, Any>()).toRequestBody(JSON)
        return flow {
            emit(ApiResult.Loading())
            try {
                val response = networkService.post(endpoint, requestBody)
                if (!response.isSuccessful) throw HttpException(response)
                val responseBody = response.body()?.string() ?: throw IOException("Empty response")
                emit(ApiResult.Success(gson.fromJson(responseBody, type)))
            } catch (e: Exception) {
                emit(mapException(e))
            }
        }
    }

    inline fun <reified T> put(
        endpoint: String,
        body: Any? = null
    ): Flow<ApiResult<T>> {
        val type: Type = object : TypeToken<T>() {}.type
        val requestBody = gson.toJson(body ?: emptyMap<String, Any>()).toRequestBody(JSON)
        return flow {
            emit(ApiResult.Loading())
            try {
                val response = networkService.put(endpoint, requestBody)
                if (!response.isSuccessful) throw HttpException(response)
                val responseBody = response.body()?.string() ?: throw IOException("Empty response")
                emit(ApiResult.Success(gson.fromJson(responseBody, type)))
            } catch (e: Exception) {
                emit(mapException(e))
            }
        }
    }

    inline fun <reified T> patch(
        endpoint: String,
        body: Any? = null
    ): Flow<ApiResult<T>> {
        val type: Type = object : TypeToken<T>() {}.type
        val requestBody = gson.toJson(body ?: emptyMap<String, Any>()).toRequestBody(JSON)
        return flow {
            emit(ApiResult.Loading())
            try {
                val response = networkService.patch(endpoint, requestBody)
                if (!response.isSuccessful) throw HttpException(response)
                val responseBody = response.body()?.string() ?: throw IOException("Empty response")
                emit(ApiResult.Success(gson.fromJson(responseBody, type)))
            } catch (e: Exception) {
                emit(mapException(e))
            }
        }
    }

    fun delete(endpoint: String): Flow<ApiResult<Unit>> = flow {
        emit(ApiResult.Loading())
        try {
            val response = networkService.delete(endpoint)
            if (!response.isSuccessful) throw HttpException(response)
            emit(ApiResult.Success(Unit))
        } catch (e: Exception) {
            emit(mapException(e))
        }
    }

    fun mapException(e: Exception): ApiResult.Error = when (e) {
        is HttpException -> ApiResult.Error(
            code = e.code(),
            message = when (e.code()) {
                400 -> "Bad request"
                401 -> "Unauthorized. Please login again."
                403 -> "Access forbidden."
                404 -> "Resource not found."
                409 -> "Conflict."
                422 -> "Unprocessable entity."
                500 -> "Server error. Please try again later."
                503 -> "Service unavailable."
                else -> "HTTP ${e.code()}"
            },
            exception = e
        )
        is SocketTimeoutException -> ApiResult.Error(message = "Request timeout. Please try again.")
        is UnknownHostException, is SocketException ->
            ApiResult.Error(message = "Network error. Please check your connection.")
        is IOException -> ApiResult.Error(message = "Network error. Please check your connection.")
        else -> {
            Log.e(TAG, "Unexpected error", e)
            ApiResult.Error(message = e.message ?: "An unknown error occurred.", exception = e)
        }
    }
}

fun <T, R> Flow<ApiResult<T>>.mapResult(transform: (T) -> R): Flow<ApiResult<R>> =
    map { result ->
        when (result) {
            is ApiResult.Success -> ApiResult.Success(transform(result.data))
            is ApiResult.Error -> result
            is ApiResult.Loading -> ApiResult.Loading()
        }
    }
