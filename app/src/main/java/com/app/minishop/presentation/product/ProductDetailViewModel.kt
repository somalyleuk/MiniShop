package com.app.minishop.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.model.Product
import com.app.minishop.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _productState = MutableStateFlow<ApiResult<Product>>(ApiResult.Loading)
    val productState: StateFlow<ApiResult<Product>> = _productState.asStateFlow()

    fun getProductDetail(productId: Long) {
        viewModelScope.launch {
            repository.getProductById(productId).collect { result ->
                _productState.value = result
            }
        }
    }
}
