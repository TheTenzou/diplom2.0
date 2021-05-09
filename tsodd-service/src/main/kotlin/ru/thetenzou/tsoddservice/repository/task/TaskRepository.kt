package ru.thetenzou.tsoddservice.repository.task

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.model.task.Task

@RepositoryRestResource
interface TaskRepository : JpaRepository<Task, Long>