package SHOPPING_MALL_SYSTEM.shoppingmall.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.RequestParam

@RestController
class MainController {

    //주문관리
    @GetMapping("/manageOrders")
    fun showManageOrdersView(): ModelAndView {
        return ModelAndView("manage_orders_view")
    }
}