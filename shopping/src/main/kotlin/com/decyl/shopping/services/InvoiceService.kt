package com.decyl.shopping.services

import com.decyl.shopping.models.Invoice

interface InvoiceService {
    fun findAll(): List<Invoice>
    fun create(invoice: Invoice): Invoice
    fun update(id:Long, invoice: Invoice): Invoice?
    fun delete(id: Long): Invoice?
    fun findById(id: Long): Invoice?
}