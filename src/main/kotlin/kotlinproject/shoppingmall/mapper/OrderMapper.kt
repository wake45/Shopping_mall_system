package SHOPPING_MALL_SYSTEM.shoppingmall.mapper

import org.apache.ibatis.annotations.Mapper
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Order
import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderItem
import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderResult

@Mapper
interface OrderMapper{
    fun newOrder(order: Order)
    fun newOrderItem(orderItem: OrderItem)
    fun getOrdersByUserId(user_id: Int, limit: Int, offset: Int): List<Order>
    fun countOrdersByUserId(user_id: Int): Int
    fun getOrderItemsByOrderId(order_id: Int): List<OrderItem>
}