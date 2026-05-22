package com.app.minishop.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.model.Product
import com.app.minishop.ui.component.loading.ShimmerCard
import com.app.minishop.ui.theme.*

@Composable
fun HomeScreen(
    onProductClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val productsState by viewModel.productsState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Deliver to", fontSize = 12.sp, color = GrayDark)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "New York, USA",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )
                    Icon(Icons.Default.ArrowDropDown, null, tint = DarkText)
                }
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Notifications, null, tint = DarkText)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search products...", color = GrayDark, fontSize = 14.sp) },
            leadingIcon = { Icon(Icons.Default.Search, null, tint = GrayDark) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = GrayLight,
                unfocusedContainerColor = GrayLight,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(PrimaryGreenLight)
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Text("Summer Sale", color = PrimaryGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text("Up to 50% OFF", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = DarkText)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onSeeAllClick,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(20.dp)
                ) { Text("Shop Now", fontSize = 12.sp, fontWeight = FontWeight.Medium) }
            }
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(White.copy(alpha = 0.35f), CircleShape)
                    .align(Alignment.CenterEnd)
            ) {
                Text("🛍️", fontSize = 36.sp, modifier = Modifier.align(Alignment.Center))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        val categories = listOf(
            "All" to "🏪", "Fashion" to "👗", "Electronics" to "💻",
            "Jewelry" to "💍", "Home" to "🏠"
        )
        var selectedCategory by remember { mutableStateOf("All") }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(categories) { (name, emoji) ->
                val isSelected = selectedCategory == name
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        selectedCategory = name
                        onCategoryClick(if (name == "All") "" else name.lowercase())
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) PrimaryGreenLight else GrayLight),
                        contentAlignment = Alignment.Center
                    ) { Text(emoji, fontSize = 24.sp) }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        name, fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) PrimaryGreen else GrayDark
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Flash Deals 🔥", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkText)
            Text(
                "See All", color = PrimaryGreen, fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (val state = productsState) {
            is ApiResult.Loading -> {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(4) { ShimmerCard(modifier = Modifier.width(160.dp)) }
                }
            }
            is ApiResult.Success -> {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(state.data.take(8)) { product ->
                        HomeProductCard(product = product, onClick = { onProductClick(product.id) })
                    }
                }
            }
            is ApiResult.Error -> {
                Text(
                    state.message ?: "Failed to load products",
                    color = ErrorRed, fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Best Selling ⭐", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkText)
            Text(
                "See All", color = PrimaryGreen, fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (val state = productsState) {
            is ApiResult.Success -> {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    state.data.drop(4).take(5).forEach { product ->
                        BestSellingRow(product = product, onClick = { onProductClick(product.id) })
                    }
                }
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun HomeProductCard(product: Product, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(GrayLighter)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.fillMaxSize().padding(12.dp),
                contentScale = ContentScale.Fit
            )
        }
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                product.name, maxLines = 2, fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold, color = DarkText,
                overflow = TextOverflow.Ellipsis, lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("⭐ ${String.format("%.1f", product.rating)}", fontSize = 11.sp, color = RatingYellow)
            Spacer(modifier = Modifier.height(4.dp))
            Text("$${String.format("%.2f", product.price)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
        }
    }
}

@Composable
private fun BestSellingRow(product: Product, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(GrayLighter)
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize().padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(product.name, maxLines = 2, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = DarkText, overflow = TextOverflow.Ellipsis)
            Text("⭐ ${String.format("%.1f", product.rating)} (${product.reviewCount})", fontSize = 12.sp, color = GrayDark)
            Text("$${String.format("%.2f", product.price)}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
        }
        Text("›", fontSize = 22.sp, color = Color.LightGray)
    }
}
