package com.decyl.product.repositories

import com.decyl.product.model.Category
import com.decyl.product.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {

    fun findByCategory(category: Category): List<Product>
}