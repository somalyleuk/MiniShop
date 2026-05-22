package com.app.minishop.data.datasource.remote

import com.app.minishop.core.network.ApiClient
import com.app.minishop.core.network.ApiResult
import com.app.minishop.data.entity.remote.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val apiClient: ApiClient
) : ProductRemoteDataSource {

    override fun getProducts(category: String?): Flow<ApiResult<List<ProductEntity>>> =
        if (category != null)
            apiClient.get("products/category/$category")
        else
            apiClient.get("products")

    override fun getProductById(id: Int): Flow<ApiResult<ProductEntity>> =
        apiClient.get("products/$id")

    override fun getCategories(): Flow<ApiResult<List<String>>> =
        apiClient.get("products/categories")
}
