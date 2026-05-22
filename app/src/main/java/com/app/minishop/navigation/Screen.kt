package com.app.minishop.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Splash : Screen

    @Serializable
    data object Onboarding : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object Auth : Screen

    @Serializable
    data object Register : Screen

    @Serializable
    data object Categories : Screen

    @Serializable
    data class ProductList(val category: String? = null) : Screen

    @Serializable
    data class ProductDetail(val productId: Int) : Screen

    @Serializable
    data object Cart : Screen

    @Serializable
    data object Checkout : Screen

    @Serializable
    data object OrderSuccess : Screen

    @Serializable
    data object AIChat : Screen

    @Serializable
    data object Tracking : Screen

    @Serializable
    data object Profile : Screen
}