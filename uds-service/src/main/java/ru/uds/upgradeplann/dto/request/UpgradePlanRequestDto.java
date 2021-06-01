package ru.uds.upgradeplann.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class UpgradePlanRequestDto {
    private String name;
    private Double resourceLimit;
}
