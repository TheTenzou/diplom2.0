package ru.thetenzou.tsoddservice.task.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.task.model.TaskType

@RepositoryRestResource
interface TaskRepository : JpaRepository<TaskType, Long>