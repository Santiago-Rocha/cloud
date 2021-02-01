package com.decyl.shopping.repositories

import com.decyl.shopping.models.InvoiceItem
import org.springframework.data.jpa.repository.JpaRepository

interface InvoiceItemRepository: JpaRepository<InvoiceItem, Long> {
}