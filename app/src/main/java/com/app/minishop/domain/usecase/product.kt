package com.app.minishop.domain.usecase

import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.model.Product
import com.app.minishop.domain.repository.ProductRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<ApiResult<List<Product>>> {
        return repository.getProducts()
    }
}