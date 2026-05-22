package com.app.minishop.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.minishop.navigation.NavGraph
import com.app.minishop.navigation.Screen
import com.app.minishop.ui.theme.AppIcons
import com.app.minishop.ui.theme.GrayDark
import com.app.minishop.ui.theme.PrimaryGreen
import com.app.minishop.ui.theme.Transparent
import com.app.minishop.ui.theme.White

@Composable
fun MiniShopApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        NavigationItem("Home", Screen.Home, AppIcons.Home),
        NavigationItem("Products", Screen.ProductList(), AppIcons.ShoppingBag),
        NavigationItem("Cart", Screen.Cart, AppIcons.Cart),
        NavigationItem("Chat", Screen.AIChat, AppIcons.Chat),
        NavigationItem("Profile", Screen.Profile, AppIcons.Profile)
    )

    val showBottomBar = items.any { item ->
        currentDestination?.hierarchy?.any { it.hasRoute(item.screen::class) } == true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    tonalElevation = 0.dp,
                    containerColor = White,
                    contentColor = GrayDark,
                    modifier = Modifier.drawBehind {
                        drawLine(
                            color = Color(0xFFE2E2E2),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                ) {
                    items.forEach { item ->
                        val interactionSource = remember { MutableInteractionSource() }
                        val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.screen::class) } == true
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryGreen,
                                unselectedIconColor = GrayDark,
                                selectedTextColor = PrimaryGreen,
                                unselectedTextColor = GrayDark,
                                indicatorColor = Transparent
                            ),
                            interactionSource = interactionSource,
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
