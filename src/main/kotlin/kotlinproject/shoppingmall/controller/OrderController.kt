package SHOPPING_MALL_SYSTEM.shoppingmall.controller

import SHOPPING_MALL_SYSTEM.shoppingmall.service.ProductService
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Product
import SHOPPING_MALL_SYSTEM.shoppingmall.model.ProductResult
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import org.springframework.ui.Model

@RestController
class OrderController(private val productService: ProductService){

    //주문하기 화면이동
    @GetMapping("/newOrderView")
    fun showNewOrderView(
        @RequestParam user_id: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        model: Model
    ) : ModelAndView {
        val offset = (page - 1) * size
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

}