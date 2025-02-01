package SHOPPING_MALL_SYSTEM.shoppingmall.model

import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderItem
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Order
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Product
import java.math.BigDecimal

data class OrderResult(
    val success: Boolean,
    val orders: List<Order> = emptyList(),
    val orderItems: List<OrderItem> = emptyList(),
    val total: Int? = null,
    val errorMessage: String? = null,
    val products: List<Product>? = emptyList()    
)