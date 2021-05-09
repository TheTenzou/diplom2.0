package ru.thetenzou.tsoddservice.model

import org.locationtech.jts.geom.Point
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Test(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long,
    var point:Point,
)
