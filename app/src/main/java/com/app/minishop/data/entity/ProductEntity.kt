package com.app.minishop.data.entity

import com.app.minishop.domain.model.Product
import com.google.gson.annotations.SerializedName

data class ProductEntity(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("rating") val rating: RatingEntity
)

fun ProductEntity.toDomainModel(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        displayImage = this.imageUrl,
        ratingScore = this.rating.rate,
        totalReviews = this.rating.count
    )
}