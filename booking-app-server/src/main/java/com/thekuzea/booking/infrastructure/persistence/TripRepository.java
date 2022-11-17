package com.thekuzea.booking.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.thekuzea.booking.flight.domain.model.Trip;

@Repository
@RequiredArgsConstructor
public class TripRepository {

    private final MongoTripRepository mongoTripRepository;

    public boolean existsByExample(final Trip trip) {
        final Example<Trip> tripExample = Example.of(trip);
        final List<Trip> trips = mongoTripRepository.findAll(tripExample);

        return !CollectionUtils.isEmpty(trips);
    }

    public Optional<Trip> findById(final String id) {
        return mongoTripRepository.findById(id);
    }

    public Page<Trip> findAll(final Pageable pageable) {
        return mongoTripRepository.findAll(pageable);
    }

    public void save(final Trip trip) {
        mongoTripRepository.save(trip);
    }

    interface MongoTripRepository extends MongoRepository<Trip, String> {

    }
}
