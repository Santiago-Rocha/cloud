package com.decyl.shopping.repositories

import com.decyl.shopping.models.Invoice
import org.springframework.data.jpa.repository.JpaRepository


interface InvoiceRepository : JpaRepository<Invoice, Long> {
    fun findByCustomerId(customerId: Long): List<Invoice>?
    fun findByNumberInvoice(numberInvoice: String): Invoice?
}