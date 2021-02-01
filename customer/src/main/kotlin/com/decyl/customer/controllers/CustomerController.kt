package com.decyl.customer.controllers

import com.decyl.customer.model.Customer
import com.decyl.customer.model.Region
import com.decyl.customer.services.CustomerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.stream.Collectors
import javax.validation.Valid


@RestController
@RequestMapping("/customers")
class CustomerController(
        private val customerService: CustomerService,
        private val objectMapper: ObjectMapper
        ) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun findAllCustomers(@RequestParam(name = "regionId", required = false) regionId: Long?) : ResponseEntity<List<Customer>>{
        val customers : List<Customer>
        if(regionId == null){
            customers = customerService.findAll()
            if(customers.isEmpty()) return ResponseEntity.noContent().build()
        }else{
            val region = Region().apply { id = regionId }
            customers = customerService.findByRegion(region)
            if(customers.isEmpty()){
                logger.error("Customers with Region id {} not found", regionId)
                return ResponseEntity.notFound().build()
            }
        }
        return ResponseEntity.ok(customers)
    }

    @GetMapping(value = ["/{id}"])
    fun getCustomer(@PathVariable("id") id: Long): ResponseEntity<Customer> {
        logger.info("Fetching Customer with id {}", id)
        val customer = customerService.findById(id)
        if (customer == null) {
            logger.error("Customer with id {} not found.", id)
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok(customer)
    }

    @PostMapping
    fun createCustomer(@Valid @RequestBody customer: Customer, result: BindingResult): ResponseEntity<Customer> {
        logger.info("Creating Customer : {}", customer)
        if (result.hasErrors()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result))
        }
        val customerDB: Customer = customerService.create(customer)
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDB)
    }

    @PutMapping(value = ["/{id}"])
    fun updateCustomer(@PathVariable("id") id: Long, @RequestBody customer: Customer): ResponseEntity<*> {
        logger.info("Updating Customer with id {}", id)
        var currentCustomer: Customer? = customerService.findById(id)
        if (currentCustomer == null ) {
            logger.error("Unable to update. Customer with id {} not found.", id)
            return ResponseEntity.notFound().build<Any>()
        }
        customer.id = id
        currentCustomer = customerService.update(customer)
        return ResponseEntity.ok(currentCustomer!!)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteCustomer(@PathVariable("id") id: Long): ResponseEntity<Customer?>? {
        logger.info("Fetching & Deleting Customer with id {}", id)
        var customer: Customer? = customerService.findById(id)
        if (customer == null ) {
            logger.error("Unable to delete. Customer with id {} not found.", id)
            return ResponseEntity.notFound().build()
        }
        customer = customerService.delete(customer)
        return ResponseEntity.ok(customer!!)
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