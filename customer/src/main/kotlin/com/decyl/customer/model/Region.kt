package com.decyl.customer.model

import javax.persistence.*

@Entity
@Table(name = "tbl_regions")
data class Region(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null,
    var name: String? = null

)