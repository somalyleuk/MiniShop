package com.app.minishop.data.datasource

import com.app.minishop.data.entity.ProductEntity
import retrofit2.http.GET

interface ProductDataSource {
    @GET("products")
    suspend fun getProducts(): List<ProductEntity>
}