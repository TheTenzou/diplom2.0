package ru.uds.upgradeplann.service.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.uds.upgradeplann.controller.UpgradePlanControllerV2;
import ru.uds.upgradeplann.dto.response.UpgradePlanRepresentation;
import ru.uds.upgradeplann.model.UpgradePlan;
import ru.uds.upgradeplann.repository.UpgradePlanRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UpgradePlanRepresentationAssembler implements RepresentationModelAssembler<UpgradePlan, UpgradePlanRepresentation> {

    @Override
    public UpgradePlanRepresentation toModel(UpgradePlan entity) {
        UpgradePlanRepresentation model = new UpgradePlanRepresentation(entity);
        model.add(linkTo(UpgradePlanControllerV2.class).slash(entity.getId()).withRel("details"));
        model.add(linkTo(UpgradePlanRepository.class).slash("/api/uds/upgradePlans").slash(entity.getId()).withSelfRel());
        return model;
    }
}
