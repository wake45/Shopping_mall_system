package SHOPPING_MALL_SYSTEM.shoppingmall.model

import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderItem
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Order
import java.math.BigDecimal

data class OrderResult(
    val success: Boolean,
    val order: Order = Order(null, 0, null, null, 0, BigDecimal.ZERO, "pending"),
    val orderItems: List<OrderItem> = emptyList(),
    val total: Int? = null,
    val errorMessage: String? = null
)