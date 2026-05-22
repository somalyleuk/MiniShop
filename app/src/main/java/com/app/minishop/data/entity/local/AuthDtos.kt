package com.app.minishop.data.entity.local

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class RegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: NameRequest
)

data class NameRequest(
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String
)

data class LoginResponse(
    @SerializedName("token") val token: String
)
