package com.app.minishop.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.minishop.navigation.NavGraph
import com.app.minishop.navigation.Screen
import com.app.minishop.ui.theme.AppIcons

@Composable
fun MiniShopApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        NavigationItem("Home", Screen.Home, AppIcons.Home),
        NavigationItem("Products", Screen.ProductList, AppIcons.ShoppingBag),
        NavigationItem("Cart", Screen.Cart, AppIcons.Cart),
        NavigationItem("Chat", Screen.AIChat, AppIcons.Chat),
        NavigationItem("Profile", Screen.Auth, AppIcons.Profile)
    )

    // Only show bottom bar on main screens
    val showBottomBar = items.any { item ->
        currentDestination?.hierarchy?.any { it.hasRoute(item.screen::class) } == true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    items.forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.screen::class) } == true
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.screen) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // The innerPadding ensures screens don't get cut off by phone cutouts or bars
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

private data class NavigationItem(
    val label: String,
    val screen: Screen,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
