package ru.uds.upgradeplann.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import ru.uds.flow.dto.FlowRepresentation;
import ru.uds.flow.repository.FlowRepository;
import ru.uds.udsupgradeevents.dto.UpgradeEventRepresentation;
import ru.uds.udsupgradeevents.repository.UpgradeEventRepository;
import ru.uds.upgradeplann.model.PlannedUpgrade;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlannedUpgradeRepresentation extends RepresentationModel<PlannedUpgradeRepresentation> {
    private Long id;
    private FlowRepresentation flow;
    private UpgradeEventRepresentation upgradeEvent;

    public PlannedUpgradeRepresentation(PlannedUpgrade upgrade) {
        this.id = upgrade.getId();

        this.flow = new FlowRepresentation(upgrade.getFlow());
        this.flow.add(
                linkTo(FlowRepository.class)
                        .slash("/api/uds/flows")
                        .slash(upgrade.getFlow().getId())
                        .withSelfRel()
        );

        this.upgradeEvent = new UpgradeEventRepresentation(upgrade.getUpgradeEvent());
        this.upgradeEvent.add(
                linkTo(UpgradeEventRepository.class)
                        .slash("/api/uds/upgradeEvents")
                        .slash(upgrade.getUpgradeEvent().getId())
                        .withSelfRel()
        );
    }
}
