package ru.uds.udsupgradeevents.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import ru.uds.udsupgradeevents.model.Difficulty;
import ru.uds.udsupgradeevents.model.Type;
import ru.uds.udsupgradeevents.model.UpgradeEvent;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpgradeEventRepresentation extends RepresentationModel<UpgradeEventRepresentation> {

    private Long id;

    private String name;

    private String description;

    private String difficulty;

    private String type;

    private Double resourceRequirements;

    public UpgradeEventRepresentation(UpgradeEvent upgradeEvent) {
        this.id = upgradeEvent.getId();
        this.name = upgradeEvent.getName();
        this.description = upgradeEvent.getDescription();
        this.difficulty = upgradeEvent.getDifficulty().name();
        this.type = upgradeEvent.getType().name();
        this.resourceRequirements = upgradeEvent.getResourceRequirements();
    }
}
