package com.app.minishop.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.minishop.core.network.ApiResult
import com.app.minishop.data.datasource.local.CartLocalDataSource
import com.app.minishop.domain.model.CartItem
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
    private val repository: ProductRepository,
    private val cartRepository: CartLocalDataSource
) : ViewModel() {

    private val _productState = MutableStateFlow<ApiResult<Product>>(ApiResult.Loading())
    val productState: StateFlow<ApiResult<Product>> = _productState.asStateFlow()

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            repository.getProductById(productId).collect { _productState.value = it }
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        cartRepository.addItem(
            CartItem(
                id = product.id,
                productId = product.id,
                productName = product.name,
                price = product.price,
                quantity = quantity,
                imageUrl = product.imageUrl
            )
        )
    }
}
