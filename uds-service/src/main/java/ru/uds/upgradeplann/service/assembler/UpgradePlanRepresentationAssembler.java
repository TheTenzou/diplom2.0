package ru.uds.upgradeplann.service.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.uds.upgradeplann.controller.UpgradePlanControllerV2;
import ru.uds.upgradeplann.dto.response.UpgradePlanRepresentation;
import ru.uds.upgradeplann.model.UpgradePlan;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UpgradePlanRepresentationAssembler implements RepresentationModelAssembler<UpgradePlan, UpgradePlanRepresentation> {

    @Override
    public UpgradePlanRepresentation toModel(UpgradePlan entity) {
        Class<UpgradePlanControllerV2> controllerClass = UpgradePlanControllerV2.class;
        UpgradePlanRepresentation model = new UpgradePlanRepresentation(entity);
        model.add(linkTo(controllerClass).slash(entity.getId()).withSelfRel());
        return model;
    }
}
