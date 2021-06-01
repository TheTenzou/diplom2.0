package ru.uds.upgradeplann.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.uds.common.dto.PagedResponse;
import ru.uds.upgradeplann.dto.request.UpgradePlanRequestDto;
import ru.uds.upgradeplann.dto.response.UpgradePlanResponseDto;
import ru.uds.upgradeplann.service.UpgradePlanService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/uds/v1/upgradePlan")
public class UpgradePlanController {

    private final UpgradePlanService upgradePlanService;


    public UpgradePlanController(UpgradePlanService upgradePlanService) {
        this.upgradePlanService = upgradePlanService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<UpgradePlanResponseDto>> getAllUpgradePlans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedResponse<UpgradePlanResponseDto> response = upgradePlanService.getAllUpgradePlans(page-1, size-1);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UpgradePlanResponseDto> createUpgradePlan(@RequestBody UpgradePlanRequestDto upgradePlan) {

        final UpgradePlanResponseDto response = upgradePlanService.saveUpgradePlan(upgradePlan);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UpgradePlanResponseDto> deleteUpgradePlan(@PathVariable Long id) {

        UpgradePlanResponseDto upgradePlan = upgradePlanService.deleteUpgradePlan(id);

        return new ResponseEntity<>(upgradePlan, HttpStatus.OK);
    }
}
