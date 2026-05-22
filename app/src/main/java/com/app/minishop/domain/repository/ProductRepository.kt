package com.app.minishop.domain.repository

import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(category: String? = null): Flow<ApiResult<List<Product>>>
    fun getProductById(id: Int): Flow<ApiResult<Product>>
    fun searchProducts(query: String): Flow<ApiResult<List<Product>>>
    fun getFavorites(): Flow<ApiResult<List<Product>>>
    fun addToFavorites(productId: Int): Flow<ApiResult<Unit>>
    fun removeFromFavorites(productId: Int): Flow<ApiResult<Unit>>
}