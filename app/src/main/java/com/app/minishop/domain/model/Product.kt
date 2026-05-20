package com.app.minishop.domain.model

import com.app.minishop.data.entity.ProductEntity

data class Product(
    val id: Long,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val displayImage: String,
    val ratingScore: Double,
    val totalReviews: Int
)