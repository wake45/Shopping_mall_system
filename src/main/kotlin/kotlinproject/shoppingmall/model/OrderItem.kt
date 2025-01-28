package SHOPPING_MALL_SYSTEM.shoppingmall.model

import java.math.BigDecimal
import java.sql.Timestamp

data class OrderItem(
    val order_item_id: Int? = null,
    var order_id: Int = 0,
    val product_id: Int = 0,
    val quantity: Int = 0,
    val price: BigDecimal = BigDecimal.ZERO
)