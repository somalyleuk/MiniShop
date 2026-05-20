package com.app.minishop.domain.repository

import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<ApiResult<List<Product>>>
    fun getProductById(id: Long): Flow<ApiResult<Product>>
}
