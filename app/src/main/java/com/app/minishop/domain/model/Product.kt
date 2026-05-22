package com.app.minishop.domain.model

data class Product(
    val id: Int,
    val name: String,
    val description: String = "",
    val price: Double,
    val originalPrice: Double? = null,
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val discount: Int? = null,
    val category: String = "",
    val imageUrl: String = "",
    val images: List<String> = emptyList(),
    val inStock: Boolean = true,
    val quantity: Int = 1,
    val isFavorite: Boolean = false,
    val createdAt: String = "",
    val updatedAt: String = ""
)