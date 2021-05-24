package ru.thetenzou.tsoddservice.crew.model

import javax.persistence.*

@Entity
@Table(name = "crews")
data class Crew(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @Column(name = "name")
    var name: String,
)
