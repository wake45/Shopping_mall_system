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
    fun newOrder(@RequestBody orderRequest: OrderRequest): ResponseEntity<String> {
        val items = orderRequest.orderItems ?: emptyList()

        for(item in items){
            if(item.quantity <= 0) {
                val productName = productService.getProductNameById(item.product_id)
                return ResponseEntity.badRequest().body("$productName: 수량은 0보다 커야 합니다.")
            }
        }

        val outOfStockProductIds: List<Int> = productService.getStockCount(items)
        if(outOfStockProductIds.isNotEmpty()){
            val productNames = outOfStockProductIds.joinToString(", ") { productService.getProductNameById(it) }
            return ResponseEntity.badRequest().body("$productNames 제품의 수량이 부족합니다.")
        }

        val order = Order(
            user_id = orderRequest.user_id ?: return ResponseEntity.badRequest().body("유저 ID가 필요합니다."),
            total_amount = items.sumOf { it.quantity },
            total_price = items.fold(BigDecimal.ZERO) { acc, item -> 
                acc + item.price
            },
            status = "completed"
        )

       val isSuccess = orderService.newOrder(order, items)
        return if (isSuccess){
            productService.updateProductStock(items) //재고 수량 업데이트

            ResponseEntity.ok("구매 완료되었습니다.") // 성공 메시지 반환
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 구매 실패")
        }
    }

    //주문관리
    @GetMapping("/manageOrdersView")
    fun showManageOrdersView( 
        @RequestParam user_id: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int,
        model: Model
    ): ModelAndView {
        val result: OrderResult = orderService.getOrdersByUserId(user_id, page, size)

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
                model.addAttribute("orders", result.orders)
                model.addAttribute("total", result.total)
                model.addAttribute("pageNumbers", pageNumbers)
                model.addAttribute("currentPage", page)                
                ModelAndView("manage_orders_view")
            }
            else -> {
                model.addAttribute("errorMessage", result.errorMessage)
                ModelAndView("error_view")
            }
        }
    }

    //주문상세조회
    @PostMapping("/getOrderItems")
    fun getOrderItems(@RequestBody orderRequest: OrderRequest): ResponseEntity<OrderResult> {
        val orderId = orderRequest.order_id ?: return ResponseEntity.badRequest().body(
            OrderResult(success = false, errorMessage = "주문 ID가 필요합니다.")
        )
        val result: OrderResult = orderService.getOrderItemsByOrderId(orderId)

        if(!result.success){
            return ResponseEntity.badRequest().body(result)
        }

        val productArray = productService.getProductImageUrlsByProductId(result.orderItems)
        val updatedResult = result.copy(products = productArray)

        return ResponseEntity.ok(updatedResult)
    }
}