package SHOPPING_MALL_SYSTEM.shoppingmall.mapper

import org.apache.ibatis.annotations.Mapper
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Order
import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderItem

@Mapper
interface OrderMapper{
    fun newOrder(order: Order)
    fun newOrderItem(orderItem: OrderItem)
}