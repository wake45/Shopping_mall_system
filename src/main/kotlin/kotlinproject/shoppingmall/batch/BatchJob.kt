package SHOPPING_MALL_SYSTEM.shoppingmall.batch

import SHOPPING_MALL_SYSTEM.shoppingmall.mapper.OrderMapper
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class BatchJob(private val orderMapper: OrderMapper) {

    @PostConstruct
    fun executeBatchJob() {
        val results = orderMapper.updateOrdersStatus()
        println("Batch job executed: $results")
    }
}