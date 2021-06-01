package ru.uds.udsupgradeevents.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "update_events")
public class UpgradeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "resource_requirements")
    private Double resourceRequirements;
}
