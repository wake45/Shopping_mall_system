package SHOPPING_MALL_SYSTEM.shoppingmall.model

import java.math.BigDecimal
import java.sql.Timestamp

data class Product(
    val product_id: Int? = null,
    val user_id: Int? = null,
    val name: String = "",
    val description: String = "",
    val price: BigDecimal = BigDecimal.ZERO,
    val stock: Int = 0,
    val created_at: Timestamp? = null,
    val modified_at: Timestamp? = null,
    val deleted_at: Timestamp? = null,
    val status: String? = null,
    var image_url: String = ""
)