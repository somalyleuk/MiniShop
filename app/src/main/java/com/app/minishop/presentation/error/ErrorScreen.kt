package com.app.minishop.presentation.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorScreen(
    title: String = "Something went wrong",
    message: String = "We couldn't complete your request. Please try again.",
//    iconRes: Int = R.drawable.ic_error,
    buttonText: String = "Try Again",
    onRetryClick: (() -> Unit)? = null,
    onDismissClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

//        Image(
//            painter = painterResource(id = iconRes),
//            contentDescription = "Error",
//            modifier = Modifier
//                .size(110.dp)
//                .alpha(0.9f)
//        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (onRetryClick != null) {
            Button(
                onClick = onRetryClick,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1F8A4E)
                )
            ) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        if (onDismissClick != null) {
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = onDismissClick) {
                Text(
                    text = "Dismiss",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}