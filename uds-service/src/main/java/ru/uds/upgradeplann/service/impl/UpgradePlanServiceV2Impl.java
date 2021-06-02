package ru.uds.upgradeplann.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import ru.uds.upgradeplann.dto.response.UpgradePlanRepresentation;
import ru.uds.upgradeplann.model.UpgradePlan;
import ru.uds.upgradeplann.repository.UpgradePlanRepository;
import ru.uds.upgradeplann.service.assembler.UpgradePlanRepresentationAssembler;
import ru.uds.upgradeplann.service.UpgradePlanServiceV2;

@Service
public class UpgradePlanServiceV2Impl implements UpgradePlanServiceV2 {

    private final UpgradePlanRepository upgradePlanRepository;
    private final PagedResourcesAssembler<UpgradePlan> pagedResourcesAssembler;
    private final UpgradePlanRepresentationAssembler representationAssembler;

    @Autowired
    public UpgradePlanServiceV2Impl(
            UpgradePlanRepository upgradePlanRepository,
            PagedResourcesAssembler<UpgradePlan> pagedResourcesAssembler,
            UpgradePlanRepresentationAssembler representationAssembler
    ) {
        this.upgradePlanRepository = upgradePlanRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.representationAssembler = representationAssembler;
    }

    @Override
    public PagedModel<UpgradePlanRepresentation> getAllUpgradePlans(int page, int size) {
        PageRequest paging = PageRequest.of(page, size);

        Page<UpgradePlan> upgradePlanList = upgradePlanRepository.findAll(paging);

        return pagedResourcesAssembler.toModel(upgradePlanList, representationAssembler);
    }
}
