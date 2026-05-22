package com.app.minishop.data.repository

import com.app.minishop.core.network.ApiResult
import com.app.minishop.core.network.mapResult
import com.app.minishop.data.datasource.remote.ProductRemoteDataSource
import com.app.minishop.data.entity.remote.toDomain
import com.app.minishop.domain.model.Product
import com.app.minishop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override fun getProducts(category: String?): Flow<ApiResult<List<Product>>> =
        remoteDataSource.getProducts(category).mapResult { entities ->
            entities.map { it.toDomain() }
        }

    override fun getProductById(id: Int): Flow<ApiResult<Product>> =
        remoteDataSource.getProductById(id).mapResult { it.toDomain() }

    override fun searchProducts(query: String): Flow<ApiResult<List<Product>>> =
        remoteDataSource.getProducts().mapResult { entities ->
            entities.filter { it.title.contains(query, ignoreCase = true) }
                .map { it.toDomain() }
        }

    override fun getFavorites(): Flow<ApiResult<List<Product>>> =
        flowOf(ApiResult.Success(emptyList()))

    override fun addToFavorites(productId: Int): Flow<ApiResult<Unit>> =
        flowOf(ApiResult.Success(Unit))

    override fun removeFromFavorites(productId: Int): Flow<ApiResult<Unit>> =
        flowOf(ApiResult.Success(Unit))
}
