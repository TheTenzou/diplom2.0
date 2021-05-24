package ru.thetenzou.tsoddservice.tsodd.model

import javax.persistence.*

@Entity
@Table(name = "tsodd_names")
data class TsoddName(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @Column(name = "name")
    var name: String,
    @ManyToOne
    @JoinColumn(name = "type_id")
    var tsoddType: TsoddType,
)
