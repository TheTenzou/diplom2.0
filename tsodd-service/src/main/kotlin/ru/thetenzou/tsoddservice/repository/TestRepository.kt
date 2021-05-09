package ru.thetenzou.tsoddservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.model.Test

@RepositoryRestResource
interface TestRepository : JpaRepository<Test, Long>