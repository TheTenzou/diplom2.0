package ru.uds.upgradeplann.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "upgrade_plan")
public class UpgradePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "resource_limit")
    private Double resourceLimit;

    @OneToMany(mappedBy = "plan")
    private List<PlannedUpgrade> plannedUpgradeList;
}
