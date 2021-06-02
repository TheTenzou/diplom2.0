package ru.uds.upgradeplann.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
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
