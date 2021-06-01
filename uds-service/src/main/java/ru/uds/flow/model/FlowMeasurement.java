package ru.uds.flow.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "flow_measurement")
public class FlowMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flow_id")
    private Flow flow;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "density")
    private Double density;
}
