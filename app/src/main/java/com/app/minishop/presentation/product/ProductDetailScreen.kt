package com.app.minishop.presentation.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.minishop.core.network.ApiResult
import com.app.minishop.domain.model.Product
import com.app.minishop.ui.component.buttons.AppButton
import com.app.minishop.ui.theme.AppIcons
import com.app.minishop.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClicked: () -> Unit,
    onAddToCart: (Product) -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val productState by viewModel.productState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.getProductDetail(productId.toLong())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(imageVector = AppIcons.Back, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = productState) {
                is ApiResult.Loading -> CircularProgressIndicator()
                is ApiResult.Success -> {
                    val product = state.data
                    ProductDetailContent(
                        product = product,
                        onAddToCart = onAddToCart
                    )
                }
                is ApiResult.Error -> {
                    Text(
                        text = "Error: ${state.exception.message}",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(MaterialTheme.dimens.medium)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductDetailContent(
    product: Product,
    onAddToCart: (Product) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = product.displayImage,
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.extraLarge),
                contentScale = ContentScale.Fit
            )
        }

        Column(modifier = Modifier.padding(MaterialTheme.dimens.medium)) {
            Text(
                text = product.category.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = AppIcons.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFB200),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.extraSmall))
                Text(
                    text = "${product.ratingScore} (${product.totalReviews} reviews)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small))
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraLarge))
            
            AppButton(
                text = "Add to Cart ($${product.price})",
                onClick = { onAddToCart(product) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium))
        }
    }
}
