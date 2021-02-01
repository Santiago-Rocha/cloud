package com.decyl.customer.services.impl

import com.decyl.customer.model.Customer
import com.decyl.customer.model.Region
import com.decyl.customer.repositories.CustomerRepository
import com.decyl.customer.services.CustomerService
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(private val customerRepository: CustomerRepository) : CustomerService{
    override fun findAll(): List<Customer> {
        return customerRepository.findAll()
    }

    override fun findById(id: Long): Customer? {
        return customerRepository.findById(id).orElse(null)
    }

    override fun findByRegion(region: Region): List<Customer> {
       return customerRepository.findByRegion(region)
    }

    override fun create(customer: Customer): Customer {
        var customerDb = customerRepository.findByNumberId(customer.numberId)
        if(customerDb == null){
            customer.state = "CREATED"
            customerDb = customerRepository.save(customer)
        }
        return customerDb
    }

    override fun update(customer: Customer): Customer? {
        val customerDb = customerRepository.findById(customer.id).orElse(null) ?: return null
        customerDb.firstName = customer.firstName
        customerDb.lastName = customer.lastName
        customerDb.email = customer.email
        customerDb.photoUrl = customer.photoUrl

        return customerRepository.save(customerDb)
    }

    override fun delete(customer: Customer): Customer? {
        val customerDb = customerRepository.findById(customer.id).orElse(null) ?: return null
        customer.state = "DELETED"
        return customerRepository.save(customerDb)
    }
}