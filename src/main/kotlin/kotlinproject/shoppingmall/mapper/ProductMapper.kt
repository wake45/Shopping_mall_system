package SHOPPING_MALL_SYSTEM.shoppingmall.mapper

import org.apache.ibatis.annotations.Mapper
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Product

@Mapper
interface ProductMapper{
    fun addProduct(product: Product)
    fun getProductsByUserId(user_id: String): List<Product>
}