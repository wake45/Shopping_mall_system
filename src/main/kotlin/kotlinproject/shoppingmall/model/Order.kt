package SHOPPING_MALL_SYSTEM.shoppingmall.model

import java.math.BigDecimal
import java.sql.Timestamp

data class Order(
    val order_id: Int? = null,
    val user_id: Int = 0,
    val order_date: Timestamp? = null,
    val modified_date: Timestamp? = null,
    var total_amount: Int = 0,
    var total_price: BigDecimal = BigDecimal.ZERO,
    val status: String? = null
)