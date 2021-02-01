package com.decyl.customer.repositories

import com.decyl.customer.model.Customer
import com.decyl.customer.model.Region
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByNumberId(numberId: String): Customer?
    fun findByLastName(lastName: String): List<Customer>
    fun findByRegion(region: Region): List<Customer>
}