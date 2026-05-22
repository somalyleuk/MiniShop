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
    // Spacing
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val smallMedium: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val mediumLarge: Dp = 20.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
    val xxLarge: Dp = 48.dp,

    // Grid & Layout
    val gridSpacing: Dp = 16.dp,
    val screenPadding: Dp = 16.dp,
    val cardPadding: Dp = 12.dp,
    val sectionSpacing: Dp = 24.dp,

    // Component Sizing
    val buttonHeight: Dp = 56.dp,
    val smallButtonHeight: Dp = 40.dp,
    val textFieldHeight: Dp = 52.dp,
    val iconSize: Dp = 24.dp,
    val largeIconSize: Dp = 32.dp,
    val smallIconSize: Dp = 18.dp,

    // Radius
    val radiusSmall: Dp = 8.dp,
    val radiusMedium: Dp = 12.dp,
    val radiusLarge: Dp = 16.dp,
    val radiusXLarge: Dp = 20.dp,
    val radiusRound: Dp = 50.dp,

    // Elevation & Shadow
    val cardElevation: Dp = 2.dp,
    val bottomSheetElevation: Dp = 16.dp,

    // Card Sizing
    val productCardWidth: Dp = 160.dp,
    val productCardHeight: Dp = 220.dp,
    val productImageHeight: Dp = 140.dp,

    // Other
    val dividerHeight: Dp = 1.dp,
    val badgeSize: Dp = 24.dp
)

val SmallDimens = Dimens(
    small = 4.dp,
    medium = 8.dp,
    large = 16.dp,
    screenPadding = 12.dp,
    sectionSpacing = 16.dp
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
