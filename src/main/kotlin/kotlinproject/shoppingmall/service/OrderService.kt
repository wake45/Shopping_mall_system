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

    fun getOrdersByUserId(user_id: Int, page: Int, size: Int): OrderResult {
        return try{
            val offset = page * size
            val orders: List<Order> = orderMapper.getOrdersByUserId(user_id, size, offset)
            val totalOrders = orderMapper.countOrdersByUserId(user_id)

            OrderResult(success = true, orders = orders, total = totalOrders)
        }catch(e: Exception){
            println("오류 발생: ${e.message}")
            OrderResult(success = false, errorMessage = e.message)
        }
    }

}