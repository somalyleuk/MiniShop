package com.app.minishop.ui.component.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.minishop.ui.theme.ErrorRed
import com.app.minishop.ui.theme.PrimaryGreen
import com.app.minishop.ui.theme.White

enum class ButtonSize(val height: Dp, val fontSize: Int) {
    Small(40.dp, 12),
    Medium(48.dp, 14),
    Large(56.dp, 16)
}

enum class ButtonStyle {
    Primary,
    Secondary,
    Danger,
    Ghost
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    isLoading: Boolean = false,
    size: ButtonSize = ButtonSize.Large,
    style: ButtonStyle = ButtonStyle.Primary,
    containerColor: Color? = null,
    contentColor: Color? = null,
    cornerRadius: Int = 12,
    fontWeight: FontWeight = FontWeight.Bold,
    fullWidth: Boolean = true
) {
    val actualContainerColor = containerColor ?: when (style) {
        ButtonStyle.Primary -> PrimaryGreen
        ButtonStyle.Secondary -> Color(0xFFF3F4F6)
        ButtonStyle.Danger -> ErrorRed
        ButtonStyle.Ghost -> Color.Transparent
    }

    val actualContentColor = contentColor ?: when (style) {
        ButtonStyle.Primary -> White
        ButtonStyle.Secondary -> Color(0xFF111827)
        ButtonStyle.Danger -> White
        ButtonStyle.Ghost -> PrimaryGreen
    }

    val finalModifier = if (fullWidth) {
        modifier
            .fillMaxWidth()
            .height(size.height)
    } else {
        modifier.height(size.height)
    }

    Button(
        onClick = onClick,
        modifier = finalModifier,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = actualContainerColor,
            contentColor = actualContentColor,
            disabledContainerColor = actualContainerColor.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(cornerRadius.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.height(16.dp),
                color = actualContentColor,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                fontWeight = fontWeight,
                fontSize = size.fontSize.sp
            )
        }
    }
}

@Composable
fun OutlinedAppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    isLoading: Boolean = false,
    size: ButtonSize = ButtonSize.Large,
    borderColor: Color = PrimaryGreen,
    contentColor: Color = PrimaryGreen,
    cornerRadius: Int = 12,
    fontWeight: FontWeight = FontWeight.Bold
) {
    val finalModifier = modifier
        .fillMaxWidth()
        .height(size.height)

    OutlinedButton(
        onClick = onClick,
        modifier = finalModifier,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = contentColor.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(cornerRadius.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = androidx.compose.foundation.BorderStroke(1.dp, borderColor).brush
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.height(16.dp),
                color = contentColor,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                fontWeight = fontWeight,
                fontSize = size.fontSize.sp
            )
        }
    }
}