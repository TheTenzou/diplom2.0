package ru.uds.upgradeplann.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.uds.upgradeplann.model.UpgradePlan;

public interface UpgradePlanRepository extends JpaRepository<UpgradePlan, Long> {
}
