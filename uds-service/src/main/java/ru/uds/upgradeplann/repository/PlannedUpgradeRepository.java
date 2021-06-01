package ru.uds.upgradeplann.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.uds.upgradeplann.model.PlannedUpgrade;

public interface PlannedUpgradeRepository extends JpaRepository<PlannedUpgrade, Long> {
}
