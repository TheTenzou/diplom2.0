package ru.uds.upgradeplann.service;

import ru.uds.common.dto.PagedResponse;
import ru.uds.upgradeplann.dto.request.UpgradePlanRequestDto;
import ru.uds.upgradeplann.dto.response.UpgradePlanResponseDto;

public interface UpgradePlanService {
    PagedResponse<UpgradePlanResponseDto> getAllUpgradePlans(int page, int size);
    UpgradePlanResponseDto saveUpgradePlan(UpgradePlanRequestDto upgradePlan);
    UpgradePlanResponseDto deleteUpgradePlan(Long id);
}
