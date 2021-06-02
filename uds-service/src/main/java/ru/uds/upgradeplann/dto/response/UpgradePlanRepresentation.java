package ru.uds.upgradeplann.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import ru.uds.upgradeplann.model.UpgradePlan;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpgradePlanRepresentation extends RepresentationModel<UpgradePlanRepresentation> {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Double resourceLimit;

    public UpgradePlanRepresentation(UpgradePlan upgradePlan) {
        this.id = upgradePlan.getId();
        this.name = upgradePlan.getName();
        this.dateTime = upgradePlan.getDateTime();
        this.resourceLimit = upgradePlan.getResourceLimit();
    }
}
