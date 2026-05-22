package com.app.minishop.domain.usecase

import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.model.Product
import com.app.minishop.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(category: String? = null): Flow<ApiResult<List<Product>>> =
        repository.getProducts(category)
}

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(id: Int): Flow<ApiResult<Product>> =
        repository.getProductById(id)
}

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(query: String): Flow<ApiResult<List<Product>>> =
        repository.searchProducts(query)
}
