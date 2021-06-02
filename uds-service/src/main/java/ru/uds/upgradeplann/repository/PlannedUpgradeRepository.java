package ru.uds.upgradeplann.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.uds.upgradeplann.model.PlannedUpgrade;
import ru.uds.upgradeplann.model.UpgradePlan;

public interface PlannedUpgradeRepository extends JpaRepository<PlannedUpgrade, Long> {

    Page<PlannedUpgrade> getByPlan(UpgradePlan upgradePlan, Pageable pageable);
}
