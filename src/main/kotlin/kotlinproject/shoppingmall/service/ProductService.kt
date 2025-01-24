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
import java.io.File

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

    fun deleteImage(image_url: String) {
        println(image_url)
        // URL에서 파일 이름 추출
        val fileName = image_url.substringAfterLast("/")

        // 로컬 파일 경로 생성
        val localFilePath = "src/main/resources/static/productImage/$fileName"
        val imageFile = File(localFilePath)

        // 파일이 존재하는지 확인 후 삭제
        if (imageFile.exists()) {
            if (imageFile.delete()) {
                println("이미지 파일이 성공적으로 삭제되었습니다: $localFilePath")
            } else {
                println("이미지 파일 삭제에 실패했습니다: $localFilePath")
            }
        } else {
            println("존재하지 않는 이미지 파일입니다: $localFilePath")
        }
    }

    fun addProduct(product: Product): Boolean {
        return try{
            productMapper.addProduct(product)
            true
        } catch (e: Exception) {
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

    fun updateProduct(product: Product): Boolean {
        return try{
            productMapper.updateProduct(product)
            true
        } catch (e: Exception) {
            println("오류 발생: ${e.message}")
            false
        }
    }

    fun deleteProduct(product_id: Int, user_id: Int): Boolean {
        return try{
            productMapper.deleteProduct(product_id, user_id)
            true
        } catch (e: Exception) {
            println("오류 발생: ${e.message}")
            false
        }
    }
}