package ru.uds.upgradeplann.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping
    public ResponseEntity<UpgradePlanResponseDto> createUpgradePlan(@RequestBody UpgradePlanRequestDto upgradePlan) {

        final UpgradePlanResponseDto response = upgradePlanService.saveUpgradePlan(upgradePlan);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
