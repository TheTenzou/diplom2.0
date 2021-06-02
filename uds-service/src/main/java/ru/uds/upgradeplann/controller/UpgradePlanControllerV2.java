package ru.uds.upgradeplann.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.uds.upgradeplann.dto.response.UpgradePlanDetailsRepresentation;
import ru.uds.upgradeplann.dto.response.UpgradePlanRepresentation;
import ru.uds.upgradeplann.service.UpgradePlanServiceV2;

@RestController
@RequestMapping("/api/uds/v2/upgradePlan")
public class UpgradePlanControllerV2 {

    private final UpgradePlanServiceV2 upgradePlanService;

    @Autowired
    public UpgradePlanControllerV2(UpgradePlanServiceV2 upgradePlanService) {
        this.upgradePlanService = upgradePlanService;
    }

    @GetMapping
    public PagedModel<UpgradePlanRepresentation> getAllUpgradePlans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int seize
    ) {
        return upgradePlanService.getAllUpgradePlans(page, seize);
    }

    @GetMapping("/{id}")
    public UpgradePlanDetailsRepresentation getUpgradePlanDetails(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return upgradePlanService.getByID(id, page, size);
    }
}
