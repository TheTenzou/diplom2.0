package ru.uds.upgradeplann.dto.response;

import lombok.Data;
import ru.uds.upgradeplann.model.UpgradePlan;

import java.time.LocalDateTime;

@Data
public class UpgradePlanResponseDto {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Double resourceLimit;

    public UpgradePlanResponseDto(UpgradePlan upgradePlan) {
        this.id = upgradePlan.getId();
        this.name = upgradePlan.getName();
        this.dateTime = upgradePlan.getDateTime();
        this.resourceLimit = upgradePlan.getResourceLimit();
    }
}
