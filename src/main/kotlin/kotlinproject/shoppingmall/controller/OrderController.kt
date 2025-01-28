package SHOPPING_MALL_SYSTEM.shoppingmall.controller

import SHOPPING_MALL_SYSTEM.shoppingmall.service.ProductService
import SHOPPING_MALL_SYSTEM.shoppingmall.service.OrderService
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Order
import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderItem
import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderResult
import SHOPPING_MALL_SYSTEM.shoppingmall.model.OrderRequest
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Product
import SHOPPING_MALL_SYSTEM.shoppingmall.model.ProductResult
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import org.springframework.ui.Model
import java.math.BigDecimal

@RestController
class OrderController(private val productService: ProductService, private val orderService: OrderService){

    //주문하기 화면이동
    @GetMapping("/newOrderView")
    fun showNewOrderView(
        @RequestParam user_id: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        model: Model
    ) : ModelAndView {
        val result: ProductResult = productService.getAllProducts(page, size)

        // 페이지네이션 정보 추가
        val totalPages = if (result.total != null && size > 0) {
            (result.total + size - 1) / size // 총 페이지 수 계산
        } else {
            0
        }
        val pageNumbers = mutableListOf<Int>()
        val startPage = if (page <= 3) 1 else if (page + 2 >= totalPages) totalPages - 4 else page - 2
         val endPage = if (startPage + 4 > totalPages) totalPages else startPage + 4

        for (i in startPage..endPage) {
            pageNumbers.add(i)
        }

        return when{
            result.success -> {
                model.addAttribute("user_id", user_id)
                model.addAttribute("products", result.products)
                model.addAttribute("total", result.total)
                model.addAttribute("pageNumbers", pageNumbers)
                model.addAttribute("currentPage", page)                
                ModelAndView("new_order_view")
            }
            else -> {
                model.addAttribute("errorMessage", result.errorMessage)
                ModelAndView("error_view")
            }
        }
    }

    //주문하기
    @PostMapping("/newOrder")
    fun newOrder(
        @RequestBody orderRequest: OrderRequest
    ): ResponseEntity<String> {
        println(orderRequest)
        val order = Order(
            user_id = orderRequest.user_id,
            total_amount = orderRequest.orderItems.sumOf { it.quantity },
            total_price = orderRequest.orderItems.fold(BigDecimal.ZERO) { acc, item -> 
                acc + item.price
            },
            status = "completed"
        )

       val isSuccess = orderService.newOrder(order, orderRequest.orderItems)
        return if (isSuccess){
            ResponseEntity.ok("구매 완료되었습니다.") // 성공 메시지 반환
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 구매 실패")
        }
    }


}