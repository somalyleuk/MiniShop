package com.app.minishop.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.app.minishop.presentation.auth.LoginScreen
import com.app.minishop.presentation.auth.ProfileScreen
import com.app.minishop.presentation.auth.RegisterScreen
import com.app.minishop.presentation.cart.CartScreen
import com.app.minishop.presentation.chat.AIChatScreen
import com.app.minishop.presentation.checkout.CheckoutScreen
import com.app.minishop.presentation.home.HomeScreen
import com.app.minishop.presentation.on_boarding.OnboardingScreen
import com.app.minishop.presentation.order.OrderSuccessScreen
import com.app.minishop.presentation.product.ProductDetailScreen
import com.app.minishop.presentation.product.ProductListScreen
import com.app.minishop.presentation.product.ProductViewModel
import com.app.minishop.presentation.splash.SplashScreen
import com.app.minishop.presentation.tracking.TrackingScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash,
        modifier = modifier
    ) {
        splashGraph(navController)
        onboardingGraph(navController)
        homeGraph(navController)
        authGraph(navController)
        productGraph(navController)
        cartGraph(navController)
        checkoutGraph(navController)
        extraGraph(navController)
    }
}

fun NavGraphBuilder.splashGraph(navController: NavController) {
    composable<Screen.Splash> {
        SplashScreen(
            onNavigateToOnboarding = {
                navController.navigate(Screen.Onboarding) {
                    popUpTo(Screen.Splash) { inclusive = true }
                }
            }
        )
    }
}

fun NavGraphBuilder.onboardingGraph(navController: NavController) {
    composable<Screen.Onboarding> {
        OnboardingScreen(
            onOnboardingComplete = {
                navController.navigate(Screen.Home) {
                    popUpTo(Screen.Onboarding) { inclusive = true }
                }
            }
        )
    }
}

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable<Screen.Home> {
        HomeScreen(
            onProductClick = { productId ->
                navController.navigate(Screen.ProductDetail(productId))
            },
            onCategoryClick = { category ->
                navController.navigate(Screen.ProductList(category.ifEmpty { null }))
            },
            onSeeAllClick = {
                navController.navigate(Screen.ProductList())
            },
            viewModel = hiltViewModel()
        )
    }
}

fun NavGraphBuilder.authGraph(navController: NavController) {
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
            onRegisterSuccess = {
                navController.navigate(Screen.Auth) {
                    popUpTo(Screen.Register) { inclusive = true }
                }
            },
            onNavigateToLogin = { navController.popBackStack() }
        )
    }

    composable<Screen.Profile> {
        ProfileScreen(
            onLogout = {
                navController.navigate(Screen.Auth) {
                    popUpTo(0) { inclusive = true }
                }
            },
            onNavigateToOrders = {},
            onBackClicked = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.productGraph(navController: NavController) {
    composable<Screen.ProductList> { backStackEntry ->
        val route = backStackEntry.toRoute<Screen.ProductList>()
        val viewModel: ProductViewModel = hiltViewModel()
        LaunchedEffect(route.category) {
            viewModel.loadProducts(route.category)
        }
        ProductListScreen(
            onProductClick = { productId ->
                navController.navigate(Screen.ProductDetail(productId))
            },
            onBackClicked = { navController.popBackStack() },
            title = route.category
                ?.replaceFirstChar { it.uppercase() }
                ?: "All Products",
            viewModel = viewModel
        )
    }

    composable<Screen.ProductDetail> { backStackEntry ->
        val route = backStackEntry.toRoute<Screen.ProductDetail>()
        ProductDetailScreen(
            productId = route.productId,
            onBackClicked = { navController.popBackStack() },
            onAddToCart = { navController.navigate(Screen.Cart) }
        )
    }
}

fun NavGraphBuilder.cartGraph(navController: NavController) {
    composable<Screen.Cart> {
        CartScreen(
            onProceedToCheckout = { navController.navigate(Screen.Checkout) }
        )
    }
}

fun NavGraphBuilder.checkoutGraph(navController: NavController) {
    composable<Screen.Checkout> {
        CheckoutScreen(
            onBackClicked = { navController.popBackStack() },
            onPlaceOrder = {
                navController.navigate(Screen.OrderSuccess) {
                    popUpTo(Screen.Cart) { inclusive = true }
                }
            }
        )
    }

    composable<Screen.OrderSuccess> {
        OrderSuccessScreen(
            onContinueShopping = {
                navController.navigate(Screen.Home) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
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
