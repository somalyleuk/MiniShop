package com.app.minishop.domain.model

data class CartItem(
    val id: Int,
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String = "",
    val totalPrice: Double = price * quantity
)

data class Cart(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val shipping: Double = 0.0,
    val total: Double = 0.0
) {
    fun addItem(item: CartItem): Cart {
        val updatedItems = items.toMutableList()
        val existingIndex = items.indexOfFirst { it.productId == item.productId }

        if (existingIndex >= 0) {
            val existing = items[existingIndex]
            updatedItems[existingIndex] = existing.copy(quantity = existing.quantity + item.quantity)
        } else {
            updatedItems.add(item)
        }

        return calculateTotals(updatedItems)
    }

    fun removeItem(productId: Int): Cart {
        val updatedItems = items.filter { it.productId != productId }
        return calculateTotals(updatedItems)
    }

    fun updateQuantity(productId: Int, quantity: Int): Cart {
        val updatedItems = items.map { item ->
            if (item.productId == productId) item.copy(quantity = quantity) else item
        }
        return calculateTotals(updatedItems)
    }

    private fun calculateTotals(items: List<CartItem>): Cart {
        val subtotal = items.sumOf { it.price * it.quantity }
        val tax = subtotal * 0.1
        val shipping = if (subtotal > 100) 0.0 else 10.0
        val total = subtotal + tax + shipping

        return copy(
            items = items,
            subtotal = subtotal,
            tax = tax,
            shipping = shipping,
            total = total
        )
    }
}