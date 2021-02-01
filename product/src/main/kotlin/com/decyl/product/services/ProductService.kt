package com.decyl.product.services

import com.decyl.product.model.Category
import com.decyl.product.model.Product

interface ProductService {
    fun findAll(): List<Product>
    fun findById(id : Long): Product?
    fun findByCategory(category: Category) : List<Product>
    fun createProduct(product: Product): Product
    fun updateProduct(id : Long, product: Product): Product?
    fun updateStock(id : Long, quantity : Double) : Product?
    fun deleteProduct(id : Long): Product?
}