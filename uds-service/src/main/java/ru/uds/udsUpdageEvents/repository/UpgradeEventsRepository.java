package ru.uds.udsUpdageEvents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.uds.udsUpdageEvents.model.UpgradeEvents;

public interface UpgradeEventsRepository extends JpaRepository<UpgradeEvents, Long> {
}
