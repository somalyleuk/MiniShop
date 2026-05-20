package com.app.minishop.presentation.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.minishop.ui.theme.AppIcons
import com.app.minishop.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(
    onBackClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Simulated Map Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE5E7EB)) // Light gray for map background
        ) {
            // Placeholder for Google Map
            Text(
                "Map View Placeholder",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Gray
            )
        }

        // Top Bar
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(MaterialTheme.dimens.medium)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(AppIcons.Back, contentDescription = "Back")
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = MaterialTheme.dimens.cardElevation)
            ) {
                Icon(AppIcons.Help, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.extraSmall))
                Text("Help")
            }
        }

        // Bottom Tracking Card
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.large)
                    .navigationBarsPadding()
            ) {
                Text(
                    text = "Your order is on the way",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Rider is heading to your location",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))

                // Progress Stepper
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TrackingStep("Confirmed", isCompleted = true)
                    TrackingStep("Preparing", isCompleted = true)
                    TrackingStep("On the way", isCompleted = true, isCurrent = true)
                    TrackingStep("Delivered", isCompleted = false)
                }

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraLarge))

                // Rider Info
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(MaterialTheme.dimens.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.medium))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("John Doe", fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(AppIcons.Location, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                            Text("Delivery Partner", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    ) {
                        Icon(AppIcons.Call, contentDescription = "Call", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun TrackingStep(label: String, isCompleted: Boolean, isCurrent: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(
                    if (isCompleted) MaterialTheme.colorScheme.primary 
                    else Color.LightGray
                )
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
            color = if (isCompleted) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}
