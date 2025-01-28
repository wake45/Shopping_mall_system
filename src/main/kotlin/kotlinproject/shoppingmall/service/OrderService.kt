package SHOPPING_MALL_SYSTEM.shoppingmall.service

import SHOPPING_MALL_SYSTEM.shoppingmall.mapper.OrderMapper
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Order
import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderItem
import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderResult
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderMapper: OrderMapper){
    fun newOrder(order: Order, orderItems: List<OrderItem>): Boolean {
        return try{
            orderMapper.newOrder(order)

            orderItems.forEach { orderItem ->
                order.order_id?.let { id ->
                    orderItem.order_id = id 
                    orderMapper.newOrderItem(orderItem)
                }
            }

            true
        } catch (e: Exception) {
            println("오류 발생: ${e.message}")
            false
        }
    }

}