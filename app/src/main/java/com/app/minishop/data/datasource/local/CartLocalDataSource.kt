package com.app.minishop.data.datasource.local

import com.app.minishop.domain.model.Cart
import com.app.minishop.domain.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartLocalDataSource @Inject constructor() {

    private val _cartState = MutableStateFlow(Cart())
    val cartState: StateFlow<Cart> = _cartState.asStateFlow()

    fun addItem(item: CartItem) {
        _cartState.value = _cartState.value.addItem(item)
    }

    fun removeItem(productId: Int) {
        _cartState.value = _cartState.value.removeItem(productId)
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) removeItem(productId)
        else _cartState.value = _cartState.value.updateQuantity(productId, quantity)
    }

    fun clearCart() {
        _cartState.value = Cart()
    }

    val itemCount: Int get() = _cartState.value.items.sumOf { it.quantity }
}