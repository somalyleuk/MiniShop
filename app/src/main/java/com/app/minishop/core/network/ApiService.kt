package com.app.minishop.core.network


import com.app.minishop.core.exception.ApiException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor(
    @PublishedApi internal val engine: GenericApiEngine,
    @PublishedApi internal val gson: Gson
) {
    suspend inline fun <reified T> get(
        endpoint: String,
        queryParams: Map<String, Any>? = null,
        headers: Map<String, String>? = null
    ): ApiResult<T> = safeRequest { engine.get(endpoint, queryParams ?: emptyMap(), headers ?: emptyMap()) }

    suspend inline fun <reified T> post(
        endpoint: String,
        body: Any? = null,
        queryParams: Map<String, Any>? = null,
        headers: Map<String, String>? = null
    ): ApiResult<T> = safeRequest { engine.post(endpoint, body, queryParams ?: emptyMap(), headers ?: emptyMap()) }

    suspend inline fun <reified T> put(
        endpoint: String,
        body: Any? = null,
        queryParams: Map<String, Any>? = null,
        headers: Map<String, String>? = null
    ): ApiResult<T> = safeRequest { engine.put(endpoint, body, queryParams ?: emptyMap(), headers ?: emptyMap()) }

    suspend inline fun <reified T> patch(
        endpoint: String,
        body: Any? = null,
        queryParams: Map<String, Any>? = null,
        headers: Map<String, String>? = null
    ): ApiResult<T> = safeRequest { engine.patch(endpoint, body, queryParams ?: emptyMap(), headers ?: emptyMap()) }

    suspend inline fun <reified T> delete(
        endpoint: String,
        queryParams: Map<String, Any>? = null,
        headers: Map<String, String>? = null
    ): ApiResult<T> = safeRequest { engine.delete(endpoint, queryParams ?: emptyMap(), headers ?: emptyMap()) }

    @PublishedApi
    internal suspend inline fun <reified T> safeRequest(
        crossinline apiCall: suspend () -> Response<ResponseBody>
    ): ApiResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                val rawString = response.body()?.string()

                if (response.isSuccessful && rawString != null) {
                    if (T::class == String::class) {
                        ApiResult.Success(rawString as T)
                    } else {
                        val type = object : TypeToken<T>() {}.type
                        val parsedData: T = gson.fromJson(rawString, type)
                        ApiResult.Success(parsedData)
                    }
                } else {
                    val errorContent = response.errorBody()?.string()
                    val exception = when (response.code()) {
                        400 -> ApiException.BadRequestException(errorContent)
                        401 -> ApiException.UnauthorizedException(errorContent)
                        403 -> ApiException.ForbiddenException(errorContent)
                        404 -> ApiException.NotFoundException(errorContent)
                        500 -> ApiException.ServerErrorException(errorContent)
                        else -> ApiException.UnknownNetworkException(response.code(), errorContent)
                    }
                    ApiResult.Error(exception)
                }
            } catch (e: IOException) {
                ApiResult.Error(e)
            } catch (e: Exception) {
                ApiResult.Error(e)
            }
        }
    }
}