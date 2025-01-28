package SHOPPING_MALL_SYSTEM.shoppingmall.model

import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderItem

data class OrderRequest(
    val user_id: Int,
    val orderItems: List<OrderItem>
)