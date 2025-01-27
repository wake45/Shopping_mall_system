package SHOPPING_MALL_SYSTEM.shoppingmall.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper{
    fun insertTestData(username: String, password: String)
    fun getUser(user_id: Int): String
}