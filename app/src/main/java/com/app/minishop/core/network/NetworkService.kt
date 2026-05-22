package com.app.minishop.core.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface NetworkService {

    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap params: Map<String, @JvmSuppressWildcards String>
    ): Response<ResponseBody>

    @POST
    suspend fun post(
        @Url url: String,
        @Body body: RequestBody
    ): Response<ResponseBody>

    @PUT
    suspend fun put(
        @Url url: String,
        @Body body: RequestBody
    ): Response<ResponseBody>

    @PATCH
    suspend fun patch(
        @Url url: String,
        @Body body: RequestBody
    ): Response<ResponseBody>

    @DELETE
    suspend fun delete(@Url url: String): Response<ResponseBody>
}
