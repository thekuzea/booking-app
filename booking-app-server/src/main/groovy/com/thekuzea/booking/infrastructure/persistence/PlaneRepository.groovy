package com.thekuzea.booking.infrastructure.persistence

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.RepositoryDefinition

import com.thekuzea.booking.api.dto.TechnicalStatus
import com.thekuzea.booking.flight.domain.model.Plane

@RepositoryDefinition(domainClass = Plane.class, idClass = String.class)
interface PlaneRepository {

    Optional<Plane> findById(String id)

    Page<Plane> findAll(Pageable pageable)

    @Query(value = "{name: ?0, planeNumber: ?1, originCountryCode: ?2, technicalStatus: ?3}", exists = true)
    boolean existsByParameters(String name, String planeNumber, String originCountryCode, TechnicalStatus technicalStatus)

    void save(Plane plane)
}
