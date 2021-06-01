package ru.uds.udsupgradeevents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.uds.udsupgradeevents.model.UpgradeEvent;

public interface UpgradeEventsRepository extends JpaRepository<UpgradeEvent, Long> {
}
