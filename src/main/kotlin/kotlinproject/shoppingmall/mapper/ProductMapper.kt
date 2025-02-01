package SHOPPING_MALL_SYSTEM.shoppingmall.mapper

import org.apache.ibatis.annotations.Mapper
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Product
import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderItem

@Mapper
interface ProductMapper{
    fun addProduct(product: Product)
    fun getProductsByUserId(user_id: Int, limit: Int, offset: Int): List<Product>
    fun countProductsByUserId(user_id: Int): Int
    fun getAllProducts(limit: Int, offset: Int): List<Product>
    fun countAllProducts(): Int
    fun updateProduct(product: Product)
    fun deleteProduct(product_id: Int, user_id: Int)
    fun getStockCount(productIds: List<Int>): List<Int>  
    fun getProductNameById(product_id: Int): String
    fun updateProductStock(product_id: Int, quantity: Int)
    fun getProductImageUrlsByProductId(productIds: List<Int>): List<Product>
}