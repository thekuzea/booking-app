package com.thekuzea.booking.infrastructure.persistence

import org.springframework.data.repository.RepositoryDefinition

import com.thekuzea.booking.flight.domain.model.Flight

@RepositoryDefinition(domainClass = Flight.class, idClass = String.class)
interface FlightRepository {

    Optional<Flight> findById(String id)

    Optional<Flight> findByTripId(String tripId)

    void save(Flight flight)
}
