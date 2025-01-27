package SHOPPING_MALL_SYSTEM.shoppingmall.service

import SHOPPING_MALL_SYSTEM.shoppingmall.mapper.UserMapper
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(private val userMapper: UserMapper) {
    
    fun makeTestData() {
        userMapper.insertTestData("영희", "1234")
        userMapper.insertTestData("철수", "1234")
        userMapper.insertTestData("민수", "1234")
        userMapper.insertTestData("기훈", "1234")
    }

    fun getUser(user_id: Int): Optional<String>{
        return Optional.ofNullable(userMapper.getUser(user_id))
    }
}