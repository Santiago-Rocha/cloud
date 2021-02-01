package com.decyl.shopping.models

import javax.persistence.*
import javax.validation.constraints.Positive


@Entity
@Table(name = "tbl_invoice_items")
data class InvoiceItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @field:Positive(message = "el stock debe ser mayor a cero")
        var quantity: Double = 0.toDouble(),

        var price: Double = 0.toDouble(),

        @Column(name = "product_id")
        var productId: Long,
){
    @Transient
    fun getSubTotal() : Double{
        return if (price > 0 && quantity > 0) {
            quantity * price
        } else {
            0.toDouble()
        }
    }
}
