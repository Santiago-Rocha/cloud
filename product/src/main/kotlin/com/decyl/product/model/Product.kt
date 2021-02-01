package com.decyl.product.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive


@Entity
@Table(name = "tbl_products")
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long,

        @field:NotEmpty(message = "El nombre no debe ser vacio")
        var name: String?,

        var description: String?,

        @field:Positive(message = "El stock debe ser mayor a cero")
        var stock : Double?,

        var price: Double?,

        var status: String?,

        @Column(name = "create_at")
        var createAt: Date?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        @field:NotNull(message = "La categoria no puede ser vacia")
        var category: Category?
)
