package com.decyl.customer.services

import com.decyl.customer.model.Customer
import com.decyl.customer.model.Region

interface CustomerService {
    fun findAll() : List<Customer>
    fun findById(id: Long): Customer?
    fun findByRegion(region: Region) : List<Customer>
    fun create(customer: Customer): Customer
    fun update(customer: Customer): Customer?
    fun delete(customer: Customer): Customer?
}