package SHOPPING_MALL_SYSTEM.shoppingmall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing

@SpringBootApplication
@EnableBatchProcessing
class ShoppingmallApplication

fun main(args: Array<String>) {
	runApplication<ShoppingmallApplication>(*args)
}
