package com.app.minishop.core.network

import retrofit2.Response
import retrofit2.http.*
import okhttp3.ResponseBody

interface GenericApiEngine {
    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap queries: Map<String, @JvmSuppressWildcards Any>,
        @HeaderMap headers: Map<String, String>
    ): Response<ResponseBody>

    @POST
    suspend fun post(
        @Url url: String,
        @Body body: Any? = null,
        @QueryMap queries: Map<String, @JvmSuppressWildcards Any>,
        @HeaderMap headers: Map<String, String>
    ): Response<ResponseBody>

    @PUT
    suspend fun put(
        @Url url: String,
        @Body body: Any? = null,
        @QueryMap queries: Map<String, @JvmSuppressWildcards Any>,
        @HeaderMap headers: Map<String, String>
    ): Response<ResponseBody>

    @PATCH
    suspend fun patch(
        @Url url: String,
        @Body body: Any? = null,
        @QueryMap queries: Map<String, @JvmSuppressWildcards Any>,
        @HeaderMap headers: Map<String, String>
    ): Response<ResponseBody>

    @DELETE
    suspend fun delete(
        @Url url: String,
        @QueryMap queries: Map<String, @JvmSuppressWildcards Any>,
        @HeaderMap headers: Map<String, String>
    ): Response<ResponseBody>
}