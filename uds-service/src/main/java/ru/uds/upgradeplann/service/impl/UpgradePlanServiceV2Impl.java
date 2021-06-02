package ru.uds.upgradeplann.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import ru.uds.upgradeplann.dto.response.PlannedUpgradeRepresentation;
import ru.uds.upgradeplann.dto.response.UpgradePlanDetailsRepresentation;
import ru.uds.upgradeplann.dto.response.UpgradePlanRepresentation;
import ru.uds.upgradeplann.model.PlannedUpgrade;
import ru.uds.upgradeplann.model.UpgradePlan;
import ru.uds.upgradeplann.repository.PlannedUpgradeRepository;
import ru.uds.upgradeplann.repository.UpgradePlanRepository;
import ru.uds.upgradeplann.service.assembler.PlannedUpgradeRepresentationAssembler;
import ru.uds.upgradeplann.service.assembler.UpgradePlanRepresentationAssembler;
import ru.uds.upgradeplann.service.UpgradePlanServiceV2;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UpgradePlanServiceV2Impl implements UpgradePlanServiceV2 {

    private final UpgradePlanRepository upgradePlanRepository;
    private final PlannedUpgradeRepository plannedUpgradeRepository;

    private final PagedResourcesAssembler<UpgradePlan> upgradePlanPagedResourcesAssembler;
    private final PagedResourcesAssembler<PlannedUpgrade> plannedUpgradePagedResourcesAssembler;
    private final UpgradePlanRepresentationAssembler upgradePlanRepresentationAssembler;
    private final PlannedUpgradeRepresentationAssembler plannedUpgradeRepresentationAssembler;

    @Autowired
    public UpgradePlanServiceV2Impl(
            UpgradePlanRepository upgradePlanRepository,
            PlannedUpgradeRepository plannedUpgradeRepository,
            PagedResourcesAssembler<UpgradePlan> upgradePlanPagedResourcesAssembler,
            PagedResourcesAssembler<PlannedUpgrade> plannedUpgradePagedResourcesAssembler, UpgradePlanRepresentationAssembler upgradePlanRepresentationAssembler,
            PlannedUpgradeRepresentationAssembler plannedUpgradeRepresentationAssembler) {
        this.upgradePlanRepository = upgradePlanRepository;
        this.plannedUpgradeRepository = plannedUpgradeRepository;
        this.upgradePlanPagedResourcesAssembler = upgradePlanPagedResourcesAssembler;
        this.plannedUpgradePagedResourcesAssembler = plannedUpgradePagedResourcesAssembler;
        this.upgradePlanRepresentationAssembler = upgradePlanRepresentationAssembler;
        this.plannedUpgradeRepresentationAssembler = plannedUpgradeRepresentationAssembler;
    }

    @Override
    public PagedModel<UpgradePlanRepresentation> getAllUpgradePlans(int page, int size) {
        PageRequest paging = PageRequest.of(page, size);

        Page<UpgradePlan> upgradePlanList = upgradePlanRepository.findAll(paging);

        return upgradePlanPagedResourcesAssembler.toModel(upgradePlanList, upgradePlanRepresentationAssembler);
    }

    public UpgradePlanDetailsRepresentation getByID(long id, int page, int size) {
        Optional<UpgradePlan> result = upgradePlanRepository.findById(id);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("Upgrade plan with id " + id + " doesn't exist");
        }

        UpgradePlan upgradePlan = result.get();

        PageRequest paging = PageRequest.of(page, size);

        Page<PlannedUpgrade> plannedUpgrades = plannedUpgradeRepository.getByPlan(upgradePlan, paging);

        PagedModel<PlannedUpgradeRepresentation> pagedModel = plannedUpgradePagedResourcesAssembler.toModel(plannedUpgrades, plannedUpgradeRepresentationAssembler);

        return new UpgradePlanDetailsRepresentation(upgradePlan, pagedModel);
    }
}
