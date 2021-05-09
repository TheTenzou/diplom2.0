package ru.thetenzou.tsoddservice.model.tsodd

import javax.persistence.*

@Entity
@Table(name = "tsodd_conditions")
data class TsoddCondition(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @Column(name = "name")
    var name: String,
)
