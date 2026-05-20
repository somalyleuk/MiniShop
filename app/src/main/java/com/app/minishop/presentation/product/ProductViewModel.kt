package com.app.minishop.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.minishop.core.exception.ApiException
import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { result ->
                _uiState.value = when (result) {
                    is ApiResult.Loading -> ProductUiState.Loading
                    is ApiResult.Success -> ProductUiState.Success(result.data)
                    is ApiResult.Error -> {
                        val message = when (val ex = result.exception) {
                            is ApiException.BadRequestException -> "Invalid item request details."
                            is ApiException.UnauthorizedException -> "Your session has expired. Please log in again."
                            is ApiException.NotFoundException -> "The store database could not locate this item list."
                            is ApiException.ServerErrorException -> "The FakeStore API backend is experiencing difficulties."
                            is IOException -> "No data connection available. Please verify your Wi-Fi or Cellular networks."
                            else -> "An error occurred: ${ex.localizedMessage ?: "Unknown error"}"
                        }
                        ProductUiState.Error(message)
                    }
                }
            }
        }
    }
}