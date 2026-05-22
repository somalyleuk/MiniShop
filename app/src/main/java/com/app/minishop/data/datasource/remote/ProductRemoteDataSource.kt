package com.app.minishop.data.datasource.remote

import com.app.minishop.core.network.ApiResult
import com.app.minishop.data.entity.remote.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRemoteDataSource {
    fun getProducts(category: String? = null): Flow<ApiResult<List<ProductEntity>>>
    fun getProductById(id: Int): Flow<ApiResult<ProductEntity>>
    fun getCategories(): Flow<ApiResult<List<String>>>
}
