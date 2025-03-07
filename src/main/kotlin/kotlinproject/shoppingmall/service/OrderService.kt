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
        } catch (e: Exception) {
            println("오류 발생: ${e.message}")
            OrderResult(success = false, errorMessage = e.message)
        }
    }

    fun getOrderItemsByOrderId(order_id: Int): OrderResult {
        return try{
            val orderItems: List<OrderItem> = orderMapper.getOrderItemsByOrderId(order_id)

            OrderResult(success = true, orderItems = orderItems)
        } catch (e: Exception) {
            println("오류 발생: ${e.message}")
            OrderResult(success = false, errorMessage = e.message)
        }
    }

    fun updateOrderStatus(action: String, order_id: Int): Boolean {
        return try {
        when (action) {
                "returnRequest" -> orderMapper.updateOrderStatus("refunded", order_id)
                "cancelOrder" -> orderMapper.updateOrderStatus("canceled", order_id)
                else -> {
                    println("잘못된 액션 타입: $action")
                    false
                }
            }
            true
        } catch (e: Exception) {
            println("오류 발생: ${e.message}")
            false
        }
    }
}