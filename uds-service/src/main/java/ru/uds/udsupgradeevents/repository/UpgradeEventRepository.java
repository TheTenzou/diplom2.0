package ru.uds.udsupgradeevents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.uds.udsupgradeevents.model.UpgradeEvent;

public interface UpgradeEventRepository extends JpaRepository<UpgradeEvent, Long> {
}
