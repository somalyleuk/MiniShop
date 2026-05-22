package com.app.minishop.presentation.cart

import androidx.lifecycle.ViewModel
import com.app.minishop.data.datasource.local.CartLocalDataSource
import com.app.minishop.domain.model.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartLocalDataSource
) : ViewModel() {

    val cartState: StateFlow<Cart> = cartRepository.cartState

    fun removeItem(productId: Int) = cartRepository.removeItem(productId)

    fun updateQuantity(productId: Int, quantity: Int) =
        cartRepository.updateQuantity(productId, quantity)

    fun clearCart() = cartRepository.clearCart()
}
