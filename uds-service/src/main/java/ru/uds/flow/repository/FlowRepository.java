package ru.uds.flow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.uds.flow.model.Flow;

public interface FlowRepository extends JpaRepository<Flow, Long> {
}
