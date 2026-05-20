package com.app.minishop.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.app.minishop.presentation.auth.LoginScreen
import com.app.minishop.presentation.auth.RegisterScreen
import com.app.minishop.presentation.cart.CartScreen
import com.app.minishop.presentation.chat.AIChatScreen
import com.app.minishop.presentation.home.HomeScreen
import com.app.minishop.presentation.product.ProductDetailScreen
import com.app.minishop.presentation.product.ProductListScreen
import com.app.minishop.presentation.tracking.TrackingScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home, // Type-safe landing screen
        modifier = modifier
    ) {
        homeGraph(navController)
        authGraph(navController)
        productGraph(navController)
        cartGraph(navController)
        extraGraph(navController)
    }
}

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable<Screen.Home> {
        HomeScreen(
            onProductClick = { productId ->
                navController.navigate(Screen.ProductDetail(productId))
            },
            viewModel = hiltViewModel()
        )
    }
}

fun NavGraphBuilder.authGraph(navController: NavController) {
    composable<Screen.Auth> {
        LoginScreen(
            onLoginSuccess = { navController.navigate(Screen.Home) },
            onNavigateToRegister = { navController.navigate(Screen.Register) }
        )
    }

    composable<Screen.Register> {
        RegisterScreen(
            onRegisterSuccess = { navController.navigate(Screen.Auth) },
            onNavigateToLogin = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.productGraph(navController: NavController) {
    composable<Screen.ProductList> {
        ProductListScreen(
            onProductClick = { productId ->
                navController.navigate(Screen.ProductDetail(productId))
            },
            viewModel = hiltViewModel()
        )
    }

    composable<Screen.ProductDetail> { backStackEntry ->
        val route = backStackEntry.toRoute<Screen.ProductDetail>()
        ProductDetailScreen(
            productId = route.productId,
            onBackClicked = { navController.popBackStack() },
            onAddToCart = { /* TODO: Navigate to Cart after adding */
                navController.navigate(Screen.Cart)
            }
        )
    }
}

fun NavGraphBuilder.cartGraph(navController: NavController) {
    composable<Screen.Cart> {
        CartScreen(onBackClicked = { navController.popBackStack() })
    }
}

fun NavGraphBuilder.extraGraph(navController: NavController) {
    composable<Screen.AIChat> {
        AIChatScreen(onBackClicked = { navController.popBackStack() })
    }
    
    composable<Screen.Tracking> {
        TrackingScreen(onBackClicked = { navController.popBackStack() })
    }
}
