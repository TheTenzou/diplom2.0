package ru.uds.upgradeplann.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import ru.uds.upgradeplann.model.PlannedUpgrade;
import ru.uds.upgradeplann.model.UpgradePlan;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpgradePlanDetailsRepresentation extends RepresentationModel<UpgradePlanDetailsRepresentation> {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Double resourceLimit;
    private PagedModel<PlannedUpgradeRepresentation> plannedUpgrades;

    public UpgradePlanDetailsRepresentation(UpgradePlan upgradePlan, PagedModel<PlannedUpgradeRepresentation> plannedUpgrades) {
        this.id = upgradePlan.getId();
        this.name = upgradePlan.getName();
        this.dateTime = upgradePlan.getDateTime();
        this.resourceLimit = upgradePlan.getResourceLimit();
        this.plannedUpgrades = plannedUpgrades;
    }
}
