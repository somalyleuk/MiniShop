package com.app.minishop.presentation.product

import com.app.minishop.domain.model.Product

sealed interface ProductUiState {
    object Loading : ProductUiState
    data class Success(val list: List<Product>) : ProductUiState
    data class Error(val message: String) : ProductUiState
}