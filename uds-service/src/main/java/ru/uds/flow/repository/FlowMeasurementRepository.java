package ru.uds.flow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.uds.flow.model.FlowMeasurement;

public interface FlowMeasurementRepository extends JpaRepository<FlowMeasurement, Long> {
}
