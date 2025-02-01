package SHOPPING_MALL_SYSTEM.shoppingmall.model

import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderItem

data class OrderRequest(
    val user_id: Int? = null,
    val order_id: Int? = null,
    val orderItems: List<OrderItem>? = emptyList()
)