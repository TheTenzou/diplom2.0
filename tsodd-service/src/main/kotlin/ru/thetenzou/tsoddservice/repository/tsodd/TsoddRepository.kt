package ru.thetenzou.tsoddservice.repository.tsodd

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.model.tsodd.Tsodd

@RepositoryRestResource
interface TsoddRepository : JpaRepository<Tsodd, Long>