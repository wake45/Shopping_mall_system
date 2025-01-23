package SHOPPING_MALL_SYSTEM.shoppingmall.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@RestController
class MainController {

    //상품등록
    @GetMapping("/addProduct")
    fun showaddProductView(): ModelAndView {
        return ModelAndView("add_product_view") // login_view.html을 반환
    }

    //상품관리
    @GetMapping("/manageProducts")
    fun showManageProductsView(): ModelAndView {
        return ModelAndView("manage_products_view") // login_view.html을 반환
    }

    //주문하기
    @GetMapping("/newOrder")
    fun showNewOrderView(): ModelAndView {
        return ModelAndView("new_order_view") // login_view.html을 반환
    }

    //주문관리
    @GetMapping("/manageOrders")
    fun showManageOrdersView(): ModelAndView {
        return ModelAndView("manage_orders_view") // login_view.html을 반환
    }
}