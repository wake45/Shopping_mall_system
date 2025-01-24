package SHOPPING_MALL_SYSTEM.shoppingmall.service

import SHOPPING_MALL_SYSTEM.shoppingmall.mapper.ProductMapper
import SHOPPING_MALL_SYSTEM.shoppingmall.model.Product
import SHOPPING_MALL_SYSTEM.shoppingmall.model.ProductResult
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class ProductService(private val productMapper: ProductMapper){

    fun saveImage(file: MultipartFile): String {
        val uploadDir = "src/main/resources/static/productImage"

        val path = Paths.get(uploadDir)
        if(!Files.exists(path)){
            Files.createDirectories(path)
        }

        val fileName = System.currentTimeMillis().toString() + "_" + file.originalFilename
        val filePath = path.resolve(fileName)

        file.inputStream.use {
            inputStream -> Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
        }

        return "http://localhost:8080/productImage/$fileName"
    }

    fun addProduct(product: Product): Boolean {
        return try{
            productMapper.addProduct(product)
            true
        }catch(e: Exception){
            println("오류 발생: ${e.message}")
            false
        }
    }

    fun getProductsByUserId(user_id: String): ProductResult {
        return try{
            val products: List<Product> = productMapper.getProductsByUserId(user_id)
            ProductResult(success = true, products = products)
        }catch(e: Exception){
            println("오류 발생: ${e.message}")
            ProductResult(success = false, errorMessage = e.message)
        }
    }
}