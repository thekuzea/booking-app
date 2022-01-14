package com.thekuzea.booking.flight.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import com.thekuzea.booking.api.TripApi
import com.thekuzea.booking.api.dto.TripPageResource
import com.thekuzea.booking.api.dto.TripResource
import com.thekuzea.booking.api.dto.TripStatus
import com.thekuzea.booking.flight.service.TripService

@RestController
class TripController implements TripApi {

    @Autowired
    TripService tripService

    @Override
    ResponseEntity<TripResource> getTripById(final String id) {
        ResponseEntity.ok(tripService.getTripById(id, false))
    }

    @Override
    ResponseEntity<TripResource> getDetailedTripById(final String id) {
        ResponseEntity.ok(tripService.getTripById(id, true))
    }

    @Override
    ResponseEntity<TripPageResource> getAllTripsByParameters(final Integer size, final Integer page) {
        ResponseEntity.ok(tripService.getAllTripsByParameters(size, page))
    }

    @Override
    ResponseEntity<Void> saveNewTrip(final TripResource trip) {
        tripService.saveNewTrip(trip)
        ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @Override
    ResponseEntity<Void> updateTripById(final String id, final TripResource trip) {
        tripService.updateTripById(id, trip)
        ResponseEntity.accepted().build()
    }

    @Override
    ResponseEntity<Void> orderTrip(final String tripId, final String passengerId) {
        tripService.addPassengerToTrip(tripId, passengerId)
        ResponseEntity.accepted().build()
    }

    @Override
    ResponseEntity<Void> cancelTripById(final String id) {
        tripService.changeTripStatusById(id, TripStatus.CANCELED)
        ResponseEntity.accepted().build()
    }
}
