package SHOPPING_MALL_SYSTEM.shoppingmall.model

import java.math.BigDecimal
import java.sql.Timestamp

data class Product(
    val product_id: Int? = null, // 상품 ID (등록 시에는 null, 수정 및 조회 시 사용)
    val user_id: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stock: Int,
    val created_at: Timestamp? = null, // 조회 시 사용
    val modified_at: Timestamp? = null, // 조회 시 사용
    val deleted_at: Timestamp? = null, // 조회 및 삭제 시 사용
    val status: String? = null, // 조회 및 삭제 시 사용
    var image_url: String = "" // 등록 및 수정 시 사용, 조회 시에도 사용 가능
)