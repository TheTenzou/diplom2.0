package ru.uds.upgradeplann.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import ru.uds.upgradeplann.model.PlannedUpgrade;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlannedUpgradeRepresentation extends RepresentationModel<PlannedUpgradeRepresentation> {
    private Long id;

    public PlannedUpgradeRepresentation(PlannedUpgrade upgrade) {
        this.id = upgrade.getId();
    }
}
