package com.app.minishop.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    fun loadProducts(category: String? = null) {
        viewModelScope.launch {
            getProductsUseCase(category).collect { result ->
                _uiState.value = when (result) {
                    is ApiResult.Loading -> ProductUiState.Loading
                    is ApiResult.Success -> ProductUiState.Success(result.data)
                    is ApiResult.Error -> ProductUiState.Error(
                        result.message ?: "Something went wrong"
                    )
                }
            }
        }
    }
}
