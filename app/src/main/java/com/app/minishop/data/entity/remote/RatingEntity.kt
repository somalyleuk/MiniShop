package com.app.minishop.data.entity.remote

import com.google.gson.annotations.SerializedName

data class RatingEntity(
    @SerializedName("rate") val rate: Double,
    @SerializedName("count") val count: Int
)