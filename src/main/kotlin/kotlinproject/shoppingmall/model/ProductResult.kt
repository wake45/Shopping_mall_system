package SHOPPING_MALL_SYSTEM.shoppingmall.model

import SHOPPING_MALL_SYSTEM.shoppingmall.model.Product

data class ProductResult(
    val success: Boolean,
    val products: List<Product> = emptyList(),
    val errorMessage: String? = null
)