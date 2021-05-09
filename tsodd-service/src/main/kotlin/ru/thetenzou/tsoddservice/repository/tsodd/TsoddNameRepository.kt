package ru.thetenzou.tsoddservice.repository.tsodd

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import ru.thetenzou.tsoddservice.model.tsodd.TsoddName

@RepositoryRestResource
interface TsoddNameRepository : JpaRepository<TsoddName, Long>