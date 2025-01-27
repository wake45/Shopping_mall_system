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
    fun showAddProductView(@RequestParam user_id: Int): ModelAndView {
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
    fun showManageProductsView(
        @RequestParam user_id: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int,
        model: Model
    ) : ModelAndView {
        val offset = (page - 1) * size
        val result: ProductResult = productService.getProductsByUserId(user_id, page, size)

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
                ModelAndView("manage_products_view")
            }
            else -> {
                model.addAttribute("errorMessage", result.errorMessage)
                ModelAndView("error_view")
            }
        }
    }

    @PutMapping("/updateProduct")
    fun updateProduct(@ModelAttribute product: Product, @RequestParam("image", required = false) file: MultipartFile?): ResponseEntity<String>{
        if (file != null && !file.isEmpty) {
            productService.deleteImage(product.image_url)
            product.image_url = productService.saveImage(file)
        }

        val isSuccess = productService.updateProduct(product)
        return if (isSuccess){
            ResponseEntity.ok("상품이 수정되었습니다.") // 성공 메시지 반환
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 수정 실패")
        }
    }

    @DeleteMapping("/deleteProduct")
    fun deleteProduct(@RequestParam product_id: Int, @RequestParam user_id: Int): ResponseEntity<String>{
        val isSuccess = productService.deleteProduct(product_id, user_id)
        return if (isSuccess){
            ResponseEntity.ok("상품이 삭제되었습니다.") // 성공 메시지 반환
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 삭제 실패")
        }
    }
}