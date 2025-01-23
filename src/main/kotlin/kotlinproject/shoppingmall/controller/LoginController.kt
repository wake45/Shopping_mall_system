package SHOPPING_MALL_SYSTEM.shoppingmall.controller

import SHOPPING_MALL_SYSTEM.shoppingmall.service.UserService
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.bind.annotation.RequestParam

@RestController
class LoginController(private val userService: UserService) {

    @GetMapping("/")
    fun showLoginView(): ModelAndView {

        //userService.makeTestData()

        return ModelAndView("login_view")
    }

    //메인화면
    @PostMapping("/main")
    fun login(@RequestParam user_id:String): ModelAndView {

        val usernameOptional = userService.getUser(user_id)

        val modelAndView = ModelAndView("main_view")
        modelAndView.addObject("user_id",user_id)
        
        return if (usernameOptional.isPresent) {
            val modelAndView = ModelAndView("main_view")
            modelAndView.addObject("user_id", user_id)
            modelAndView.addObject("username", usernameOptional.get())
            modelAndView
        } else {
            val modelAndView = ModelAndView("error_view") // 오류 페이지로 이동
            modelAndView.addObject("errorMessage", "사용자가 존재하지 않습니다.") // 오류 메시지 전달
            modelAndView
        }
    }

}
