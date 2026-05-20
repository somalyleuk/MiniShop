package com.app.minishop.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
    val gridSpacing: Dp = 16.dp,
    val screenPadding: Dp = 16.dp,
    val buttonHeight: Dp = 56.dp,
    val cardElevation: Dp = 2.dp
)

val SmallDimens = Dimens(
    small = 4.dp,
    medium = 8.dp,
    large = 16.dp,
    screenPadding = 12.dp
)

val DefaultDimens = Dimens()

val LocalAppDimens = staticCompositionLocalOf { DefaultDimens }

@Composable
fun ProvideDimens(
    dimens: Dimens,
    content: @Composable () -> Unit
) {
    val appDimens = remember { dimens }
    CompositionLocalProvider(LocalAppDimens provides appDimens) {
        content()
    }
}

val MaterialTheme.dimens: Dimens
    @Composable
    get() = LocalAppDimens.current

@Composable
fun rememberWindowSizeDimens(): Dimens {
    val configuration = LocalConfiguration.current
    return if (configuration.screenWidthDp < 360) {
        SmallDimens
    } else {
        DefaultDimens
    }
}
