package com.app.minishop.data.entity.remote

import com.app.minishop.domain.model.Product
import com.google.gson.annotations.SerializedName

data class ProductEntity(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("price") val price: Double = 0.0,
    @SerializedName("category") val category: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("rating") val rating: RatingEntity? = null
)

fun ProductEntity.toDomain() = Product(
    id = id,
    name = title,
    description = description,
    price = price,
    category = category,
    imageUrl = image,
    rating = rating?.rate?.toFloat() ?: 0f,
    reviewCount = rating?.count ?: 0
)
