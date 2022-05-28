package com.thekuzea.booking.infrastructure.persistence;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import com.thekuzea.booking.api.dto.TripClassType;
import com.thekuzea.booking.flight.domain.model.Trip;

@RepositoryDefinition(domainClass = Trip.class, idClass = String.class)
public interface TripRepository {

    Optional<Trip> findById(String id);

    Page<Trip> findAll(Pageable pageable);

    @Query(value = "{'tripClassType': ?0, 'countryCodeOfDeparture': ?1, 'cityOfDeparture': ?2, 'departureDateTime': ?3, 'countryCodeOfArrival': ?4, 'cityOfArrival': ?5, 'dateTimeOfArrival': ?6, 'planeId': ?7}", exists = true)
    boolean existsByParameters(TripClassType tripClassType,
                               String countryCodeOfDeparture,
                               String cityOfDeparture,
                               OffsetDateTime departureDateTime,
                               String countryCodeOfArrival,
                               String cityOfArrival,
                               OffsetDateTime dateTimeOfArrival,
                               String planeId);

    void save(Trip trip);
}
