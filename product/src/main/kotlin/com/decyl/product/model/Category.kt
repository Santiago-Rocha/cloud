package com.decyl.product.model

import javax.persistence.*

@Entity
@Table(name = "tbl_categories")
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long,
        var name: String
)
