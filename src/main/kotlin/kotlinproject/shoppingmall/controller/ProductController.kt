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
class ProductController(private val productService: ProductService){

    //상품등록 화면이동
    @GetMapping("/addProductView")
    fun showaddProductView(@RequestParam user_id: String): ModelAndView {
        val modelAndView = ModelAndView("add_product_view")
        modelAndView.addObject("user_id", user_id)

        return modelAndView
    }

    //상품등록
    @PostMapping("/addProduct")
    fun addProduct(@ModelAttribute product: Product, @RequestParam("image") file: MultipartFile): ResponseEntity<String>{
        product.image_url = productService.saveImage(file)

        val isSuccess = productService.addProduct(product)
        return if (isSuccess){
            ResponseEntity.ok("상품이 등록되었습니다.") // 성공 메시지 반환
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 등록 실패")
        }
    }

    //상품관리 화면이동
    @GetMapping("/manageProductsView")
    fun showManageProductsView(@RequestParam user_id: String, model: Model): ModelAndView {
        val result: ProductResult = productService.getProductsByUserId(user_id)

        return when{
            result.success -> {
                model.addAttribute("products", result.products)
                println(result.products)
                ModelAndView("manage_products_view")
            }
            else -> {
                model.addAttribute("errorMessage", result.errorMessage)
                ModelAndView("error_view")
            }
        }
    }
}