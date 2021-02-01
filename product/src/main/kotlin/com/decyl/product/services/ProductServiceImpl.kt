package com.decyl.product.services

import com.decyl.product.model.Category
import com.decyl.product.model.Product
import com.decyl.product.repositories.ProductRepository
import org.springframework.stereotype.Service
import java.util.Date

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    override fun findById(id :  Long): Product? {
        return productRepository.findById(id).orElse(null)
    }

    override fun findByCategory(category: Category): List<Product> {
        return productRepository.findByCategory(category)
    }

    override fun createProduct(product: Product): Product {
        product.status =  "CREATED"
        product.createAt  =  Date()
        return productRepository.save(product)
    }

    override fun updateProduct(id : Long, product: Product): Product? {
        val productDb = productRepository.findById(id).orElse(null)
        product.name?.let { productDb.name = it }
        product.description?.let { productDb.description = it }
        product.price?.let { productDb.price = it }
        product.category?.let { productDb.category = it }
        return productRepository.save(productDb)
    }

    override fun updateStock(id: Long, quantity: Double): Product? {
        val productDb = productRepository.findById(id).orElse(null)
        productDb.stock = quantity
        return productRepository.save(productDb)
    }

    override fun deleteProduct(id : Long) : Product? {
        val productDb = productRepository.findById(id).orElse(null)
        productDb.status = "DELETED"
        return productRepository.save(productDb)
    }
}