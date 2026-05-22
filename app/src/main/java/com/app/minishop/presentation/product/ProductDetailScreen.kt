package com.app.minishop.presentation.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.minishop.core.network.ApiResult
import com.app.minishop.ui.component.loading.FullScreenLoading
import com.app.minishop.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClicked: () -> Unit,
    onAddToCart: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableStateOf(1) }
    var isFavorite by remember { mutableStateOf(false) }
    val productState by viewModel.productState.collectAsState()

    LaunchedEffect(productId) { viewModel.loadProduct(productId) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Product Detail", fontSize = 17.sp, fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) ErrorRed else GrayDark
                        )
                    }
                }
            )
        },
        bottomBar = {
            when (val state = productState) {
                is ApiResult.Success -> {
                    Surface(modifier = Modifier.fillMaxWidth(), tonalElevation = 6.dp, color = White) {
                        Row(
                            modifier = Modifier.padding(16.dp).navigationBarsPadding(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(GrayLight, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 4.dp)
                            ) {
                                IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                    Text("−", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkText)
                                }
                                Text(
                                    quantity.toString(),
                                    modifier = Modifier.padding(horizontal = 12.dp),
                                    fontWeight = FontWeight.Bold,
                                    color = DarkText,
                                    fontSize = 16.sp
                                )
                                IconButton(onClick = { quantity++ }) {
                                    Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
                                }
                            }
                            Button(
                                onClick = {
                                    viewModel.addToCart(state.data, quantity)
                                    onAddToCart()
                                },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("Add to Cart", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = White)
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    ) { innerPadding ->
        when (val state = productState) {
            is ApiResult.Loading -> FullScreenLoading()
            is ApiResult.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.message ?: "Failed to load product", color = ErrorRed)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { viewModel.loadProduct(productId) }) { Text("Retry") }
                    }
                }
            }
            is ApiResult.Success -> {
                val product = state.data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(White)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Product Image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(GrayLighter),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = product.imageUrl,
                            contentDescription = product.name,
                            modifier = Modifier.fillMaxSize().padding(24.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        // Category chip
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(PrimaryGreenLight)
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                product.category.replaceFirstChar { it.uppercase() },
                                fontSize = 12.sp,
                                color = PrimaryGreen,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Name
                        Text(
                            product.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText,
                            lineHeight = 28.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Rating + price row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("⭐ ${String.format("%.1f", product.rating)}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text(" (${product.reviewCount} reviews)", color = GrayDark, fontSize = 12.sp)
                            }
                            Text(
                                "$${String.format("%.2f", product.price)}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = BorderGray)
                        Spacer(modifier = Modifier.height(16.dp))

                        // Description
                        Text("Description", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DarkText)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            product.description,
                            color = GrayDark,
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}
