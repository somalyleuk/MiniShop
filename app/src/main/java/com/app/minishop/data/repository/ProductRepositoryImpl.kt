package com.app.minishop.data.repository

import com.app.minishop.core.network.ApiResult
import com.app.minishop.core.network.ApiService
import com.app.minishop.data.entity.ProductEntity
import com.app.minishop.data.entity.toDomainModel
import com.app.minishop.domain.model.Product
import com.app.minishop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : ProductRepository {

    override fun getProducts(): Flow<ApiResult<List<Product>>> = flow {
        emit(ApiResult.Loading)
        val response = apiService.get<List<ProductEntity>>("products")
        val processedResponse = when (response) {
            is ApiResult.Success -> {
                val domainModels = response.data.map { it.toDomainModel() }
                ApiResult.Success(domainModels)
            }
            is ApiResult.Error -> ApiResult.Error(response.exception)
            is ApiResult.Loading -> ApiResult.Loading
        }
        emit(processedResponse)
    }

    override fun getProductById(id: Long): Flow<ApiResult<Product>> = flow {
        emit(ApiResult.Loading)
        val response = apiService.get<ProductEntity>("products/$id")
        val processedResponse = when (response) {
            is ApiResult.Success -> ApiResult.Success(response.data.toDomainModel())
            is ApiResult.Error -> ApiResult.Error(response.exception)
            is ApiResult.Loading -> ApiResult.Loading
        }
        emit(processedResponse)
    }
}
