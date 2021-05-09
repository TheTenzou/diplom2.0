package ru.thetenzou.tsoddservice.model.tsodd

import org.locationtech.jts.geom.GeometryCollection
import javax.persistence.*

@Entity
@Table(name = "tsodds")
data class Tsodd(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    @ManyToOne
    @JoinColumn(name = "name_id")
    var name: TsoddName,
    @Column(name = "visibility")
    var visibility: Double,
    @ManyToOne
    @JoinColumn(name = "condition_id")
    var condition: TsoddCondition,
    @Column(name = "coordinates")
    var coordinates: GeometryCollection,
)
