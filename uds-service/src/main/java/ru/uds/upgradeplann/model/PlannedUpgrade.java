package ru.uds.upgradeplann.model;

import lombok.Data;
import ru.uds.flow.model.Flow;
import ru.uds.udsupgradeevents.model.UpgradeEvent;

import javax.persistence.*;

@Data
@Entity
@Table(name = "planned_upgrade")
public class PlannedUpgrade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @Column(name = "plan")
    private UpgradePlan plan;

    @ManyToOne
    @Column(name = "flow_id")
    private Flow flow;

    @ManyToOne
    @Column(name = "upgrade_event_id")
    private UpgradeEvent upgradeEvent;
}
