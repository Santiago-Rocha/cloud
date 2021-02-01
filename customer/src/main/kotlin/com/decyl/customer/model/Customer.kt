package com.decyl.customer.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "tbl_customers")
data class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long,

        @field:NotEmpty(message = "El numero de documento no puede ser vacio")
        @field:Size(min = 8, max = 8, message = "El tamaño del numero de coumento es 8")
        @Column(name = "number_id", unique = true, nullable = false)
        var numberId: String,

        @field:NotEmpty(message = "El nombre no puede ser vacio")
        @Column(name = "first_name", nullable = false)
        var firstName: String?,

        @field:NotEmpty(message = "El apellido no puede ser vacio")
        @Column(name = "last_name", nullable = false)
        var lastName: String?,

        @field:NotEmpty(message = "El correo no puede ser vacio")
        @field:Email(message = "No es una dirección de correo valida")
        @Column(unique = true, nullable = false)
        var email: String?,

        @Column(name = "photo_url")
        var photoUrl: String?,

        @field:NotNull(message="la region no puede ser vacia")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "region_id")
        @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
        var region: Region?,

        var state: String?
)
