package com.app.minishop.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.app.minishop.presentation.auth.LoginScreen
import com.app.minishop.presentation.auth.RegisterScreen
import com.app.minishop.presentation.cart.CartScreen
import com.app.minishop.presentation.chat.AIChatScreen
import com.app.minishop.presentation.home.HomeScreen
import com.app.minishop.presentation.product.ProductDetailScreen
import com.app.minishop.presentation.product.ProductListScreen
import com.app.minishop.presentation.splash.SplashScreen
import com.app.minishop.presentation.tracking.TrackingScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.Home> {
            HomeScreen(
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail(productId))
                },
                viewModel = hiltViewModel()
            )
        }

        composable<Screen.Auth> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Auth) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Register) }
            )
        }

        composable<Screen.Register> {
            RegisterScreen(
                onRegisterSuccess = { navController.popBackStack() },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

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
                onAddToCart = { 
                    navController.navigate(Screen.Cart)
                }
            )
        }

        composable<Screen.Cart> {
            CartScreen(onBackClicked = { navController.popBackStack() })
        }

        composable<Screen.AIChat> {
            AIChatScreen(onBackClicked = { navController.popBackStack() })
        }

        composable<Screen.Tracking> {
            TrackingScreen(onBackClicked = { navController.popBackStack() })
        }
    }
}
