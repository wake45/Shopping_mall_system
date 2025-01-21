package SHOPPING_MALL_SYSTEM.shoppingmall

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/")
    fun hello(): String {
        return "Hello, World!"
    }
}
