package ru.uds.upgradeplann.service.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.uds.upgradeplann.controller.UpgradePlanControllerV2;
import ru.uds.upgradeplann.dto.response.PlannedUpgradeRepresentation;
import ru.uds.upgradeplann.dto.response.UpgradePlanRepresentation;
import ru.uds.upgradeplann.model.PlannedUpgrade;
import ru.uds.upgradeplann.model.UpgradePlan;
import ru.uds.upgradeplann.repository.PlannedUpgradeRepository;
import ru.uds.upgradeplann.repository.UpgradePlanRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PlannedUpgradeRepresentationAssembler implements RepresentationModelAssembler<PlannedUpgrade, PlannedUpgradeRepresentation> {

    @Override
    public PlannedUpgradeRepresentation toModel(PlannedUpgrade entity) {
        PlannedUpgradeRepresentation model = new PlannedUpgradeRepresentation(entity);
        model.add(linkTo(PlannedUpgradeRepository.class).slash("/api/uds/plannedUpgrades").slash(entity.getId()).withSelfRel());
        return model;
    }
}
