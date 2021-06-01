package ru.uds.upgradeplann.service;

import ru.uds.upgradeplann.dto.request.UpgradePlanRequestDto;
import ru.uds.upgradeplann.dto.response.UpgradePlanResponseDto;

public interface UpgradePlanService {
    UpgradePlanResponseDto saveUpgradePlan(UpgradePlanRequestDto upgradePlan);
}
