package SHOPPING_MALL_SYSTEM.shoppingmall.mapper

import org.apache.ibatis.annotations.Mapper
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Product

@Mapper
interface ProductMapper{
    fun addProduct(product: Product)
    fun getProductsByUserId(user_id: Int, limit: Int, offset: Int): List<Product>
    fun countProductsByUserId(user_id: Int): Int
    fun getAllProducts(limit: Int, offset: Int): List<Product>
    fun countAllProducts(): Int
    fun updateProduct(product: Product)
    fun deleteProduct(product_id: Int, user_id: Int)
}