package com.app.minishop.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.model.Product
import com.app.minishop.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _productsState = MutableStateFlow<ApiResult<List<Product>>>(ApiResult.Loading)
    val productsState: StateFlow<ApiResult<List<Product>>> = _productsState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { result ->
                _productsState.value = result
            }
        }
    }
}