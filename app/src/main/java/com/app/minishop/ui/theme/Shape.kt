package com.app.minishop.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

object ShapeTokens {
    val ExtraSmallRadius = RoundedCornerShape(4.dp)
    val SmallRadius = RoundedCornerShape(8.dp)
    val MediumRadius = RoundedCornerShape(12.dp)
    val LargeRadius = RoundedCornerShape(16.dp)
    val ExtraLargeRadius = RoundedCornerShape(20.dp)
    val CircleRadius = RoundedCornerShape(50.dp)

    val BottomSheetRadius = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    val DialogRadius = RoundedCornerShape(16.dp)
    val CardRadius = RoundedCornerShape(12.dp)
    val ButtonRadius = RoundedCornerShape(12.dp)
}