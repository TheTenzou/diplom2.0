package ru.uds.upgradeplann.service;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import ru.uds.upgradeplann.dto.response.UpgradePlanRepresentation;

public interface UpgradePlanServiceV2 {
    PagedModel<UpgradePlanRepresentation> getAllUpgradePlans(int page, int size);
}
