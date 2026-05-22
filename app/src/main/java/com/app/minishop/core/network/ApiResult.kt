package com.app.minishop.core.network

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val code: Int? = null,
        val message: String? = null,
        val exception: Throwable? = null
    ) : ApiResult<Nothing>()
    class Loading<T> : ApiResult<T>()
}
