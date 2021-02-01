package com.decyl.product.controllers

import com.decyl.product.model.Category
import com.decyl.product.model.Product
import com.decyl.product.services.ProductService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/products"])
class ProductController(
        private val productService: ProductService,
        private val objectMapper: ObjectMapper
){

    @GetMapping
    fun getAllProducts(@RequestParam("categoryId", required = false) categoryId : Long?) : ResponseEntity<List<Product>>{
        var products = productService.findAll()
        if(categoryId == null){
            if(products.isEmpty()){
                return ResponseEntity.noContent().build()
            }
        }
        else{
            products =  productService.findByCategory(Category(categoryId,""))
            if(products.isEmpty()){
                return ResponseEntity.notFound().build()
            }
        }

        return ResponseEntity.ok(products)
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long) : ResponseEntity<Product>{
        val product = productService.findById(id)
        return if(product == null){
            ResponseEntity.notFound().build()
        }
        else{
            ResponseEntity.ok(product)
        }
    }

    @PostMapping
    fun saveProduct(@Valid @RequestBody product: Product, result : BindingResult): ResponseEntity<Product>{
        if(result.hasErrors()){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result))
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product))
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: Product) : ResponseEntity<Product>{
        val productDb = productService.updateProduct(id,product)
        return if(productDb == null){
             ResponseEntity.notFound().build()
        }else{
             ResponseEntity.ok(productDb)
        }
    }

    @PutMapping("/{id}/stock")
    fun updateProductStock(@PathVariable id: Long, @RequestParam(name = "quantity", required = true) quantity: Double) : ResponseEntity<Product>{
        val productDb = productService.updateStock(id,quantity)
        return if(productDb == null){
            ResponseEntity.notFound().build()
        }else{
            ResponseEntity.ok(productDb)
        }
    }



    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) : ResponseEntity<Product> {
        val productDb = productService.deleteProduct(id)
        return if(productDb == null){
            ResponseEntity.notFound().build()
        }else{
            ResponseEntity.ok(productDb)
        }
    }

    private fun formatMessage(result: BindingResult) : String{

        val errors = result.fieldErrors.stream()
                .map { mapOf(it.field to it.defaultMessage!!) }
                .collect(Collectors.toList())

        val errorMessage = ErrorMessage().apply {
            code = "01"
            messages = errors.toList()
        }

        return objectMapper.writeValueAsString(errorMessage)
    }
}