package com.decyl.shopping.services

import com.decyl.shopping.models.Invoice
import com.decyl.shopping.repositories.InvoiceItemRepository
import com.decyl.shopping.repositories.InvoiceRepository
import org.springframework.stereotype.Service

@Service
class InvoiceServiceImpl(
        private val invoiceRepository: InvoiceRepository,
        private val invoiceItemRepository: InvoiceItemRepository
) : InvoiceService {
    override fun findAll(): List<Invoice> {
        return invoiceRepository.findAll()
    }

    override fun create(invoice: Invoice): Invoice {
        val invoiceDB = invoice.numberInvoice?.let { invoiceRepository.findByNumberInvoice(it) }
        if (invoiceDB != null) {
            return invoiceDB
        }
        invoice.state = "CREATED"
        return invoiceRepository.save(invoice)
    }

    override fun update(id: Long, invoice: Invoice): Invoice? {
        var invoiceDB = invoiceRepository.findById(id).orElse(null)
        if(invoiceDB != null){
            invoiceDB.customerId = invoice.customerId
            invoiceDB.description = invoice.description
            invoiceDB.numberInvoice = invoice.numberInvoice
            invoiceDB.items = invoice.items
            invoiceDB = invoiceRepository.save(invoiceDB)
        }
        return invoiceDB
    }

    override fun delete(id: Long): Invoice? {
        var invoiceDB = invoiceRepository.findById(id).orElse(null)
        if(invoiceDB != null){
            invoiceDB.state =  "DELETED"
            invoiceDB = invoiceRepository.save(invoiceDB)
        }
        return invoiceDB
    }

    override fun findById(id: Long): Invoice? {
        return invoiceRepository.findById(id).orElse(null)
    }
}