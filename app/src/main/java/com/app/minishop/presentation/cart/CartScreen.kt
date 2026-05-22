package com.app.minishop.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.minishop.domain.model.CartItem
import com.app.minishop.ui.component.dialogs.ConfirmDeleteDialog
import com.app.minishop.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onProceedToCheckout: () -> Unit
) {
    val cart by viewModel.cartState.collectAsState()
    var itemToDelete by remember { mutableStateOf<Int?>(null) }

    itemToDelete?.let { productId ->
        ConfirmDeleteDialog(
            onConfirm = { viewModel.removeItem(productId); itemToDelete = null },
            onDismiss = { itemToDelete = null }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart (${cart.items.size})", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        containerColor = GrayLighter
    ) { paddingValues ->
        if (cart.items.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🛒", fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Your cart is empty", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = DarkText)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Add some products to get started", fontSize = 14.sp, color = GrayDark)
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(cart.items, key = { it.productId }) { item ->
                        CartItemCard(
                            item = item,
                            onIncrease = { viewModel.updateQuantity(item.productId, item.quantity + 1) },
                            onDecrease = { viewModel.updateQuantity(item.productId, item.quantity - 1) },
                            onDelete = { itemToDelete = item.productId }
                        )
                    }
                }

                // Order Summary
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 8.dp,
                    color = White,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp).navigationBarsPadding()) {
                        Text("Order Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DarkText)
                        Spacer(modifier = Modifier.height(12.dp))

                        SummaryRow("Subtotal", "$${String.format("%.2f", cart.subtotal)}")
                        SummaryRow("Tax (10%)", "$${String.format("%.2f", cart.tax)}")
                        SummaryRow(
                            "Shipping",
                            if (cart.shipping == 0.0) "FREE" else "$${String.format("%.2f", cart.shipping)}"
                        )

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = BorderGray)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DarkText)
                            Text(
                                "$${String.format("%.2f", cart.total)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = PrimaryGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onProceedToCheckout,
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                "Checkout • $${String.format("%.2f", cart.total)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CartItemCard(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GrayLighter),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(item.productName, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkText, maxLines = 2)
            Text("$${String.format("%.2f", item.price)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onDecrease,
                    modifier = Modifier
                        .size(30.dp)
                        .background(GrayLight, RoundedCornerShape(8.dp))
                ) {
                    Text("−", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkText)
                }
                Text(
                    item.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = DarkText
                )
                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier
                        .size(30.dp)
                        .background(PrimaryGreenLight, RoundedCornerShape(8.dp))
                ) {
                    Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                }
            }
        }

        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, null, tint = ErrorRed, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = GrayDark, fontSize = 14.sp)
        Text(value, color = DarkText, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}
