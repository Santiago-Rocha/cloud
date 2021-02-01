package com.decyl.shopping.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import kotlin.collections.ArrayList

@Entity
@Table(name= "tbl_invoices")
data class Invoice(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @Column(name = "number_invoice")
        var numberInvoice: String?,

        var description: String?,

        @Column(name = "customer_id")
        var customerId: Long?,

        @Column(name = "create_at")
        @Temporal(TemporalType.DATE)
        var createAt : Date? = null,

        @Valid
        @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "invoice_id")
        var items : List<InvoiceItem> = ArrayList(),

        var state: String?
){
    @PrePersist
    fun prePersist(){
        createAt = Date()
    }
}
