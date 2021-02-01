package com.decyl.shopping.controllers

import com.decyl.shopping.models.Invoice
import com.decyl.shopping.services.InvoiceService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.stream.Collectors
import javax.validation.Valid


@RestController
@RequestMapping("/invoices")
class InvoiceController(
        private val invoiceService: InvoiceService,
        private val objectMapper: ObjectMapper
) {

    @GetMapping
    fun listAllInvoices(): ResponseEntity<List<Invoice>> {
        val invoices: List<Invoice> = invoiceService.findAll()
        return if (invoices.isEmpty()) {
            ResponseEntity.noContent().build()
        } else ResponseEntity.ok(invoices)
    }

    @GetMapping(value = ["/{id}"])
    fun getInvoice(@PathVariable("id") id: Long): ResponseEntity<Invoice> {
        val invoice = invoiceService.findById(id)
        return if (invoice == null) {
            ResponseEntity.notFound().build()
        }
        else{
            ResponseEntity.ok(invoice)
        }
    }

    @PostMapping
    fun createInvoice(@Valid @RequestBody invoice: Invoice, result: BindingResult): ResponseEntity<Invoice> {
        if (result.hasErrors()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result))
        }
        val invoiceDB: Invoice = invoiceService.create(invoice)
        return ResponseEntity.status(HttpStatus.CREATED).body<Invoice>(invoiceDB)
    }

    @PutMapping(value = ["/{id}"])
    fun updateInvoice(@PathVariable("id") id: Long, @RequestBody invoice: Invoice): ResponseEntity<Invoice>? {
        val invoiceUpdated = invoiceService.update(id, invoice)
        return if (invoiceUpdated == null) {
            ResponseEntity.notFound().build()
        }
        else{
            ResponseEntity.ok(invoiceUpdated)
        }
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteInvoice(@PathVariable("id") id: Long): ResponseEntity<Invoice> {
        val invoice = invoiceService.delete(id)
        return if (invoice == null) {
            ResponseEntity.notFound().build()
        }
        else{
            ResponseEntity.ok(invoice)
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