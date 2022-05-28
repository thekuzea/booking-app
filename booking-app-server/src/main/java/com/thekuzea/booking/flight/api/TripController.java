package com.thekuzea.booking.flight.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.thekuzea.booking.api.TripApi;
import com.thekuzea.booking.api.dto.TripPageResource;
import com.thekuzea.booking.api.dto.TripResource;
import com.thekuzea.booking.api.dto.TripStatus;
import com.thekuzea.booking.flight.service.TripService;

@RestController
@RequiredArgsConstructor
public class TripController implements TripApi {

    private final TripService tripService;

    @Override
    public ResponseEntity<TripResource> getTripById(final String id) {
        return ResponseEntity.ok(tripService.getTripById(id, false));
    }

    @Override
    public ResponseEntity<TripResource> getDetailedTripById(final String id) {
        return ResponseEntity.ok(tripService.getTripById(id, true));
    }

    @Override
    public ResponseEntity<TripPageResource> getAllTripsByParameters(final Integer size, final Integer page) {
        return ResponseEntity.ok(tripService.getAllTripsByParameters(size, page));
    }

    @Override
    public ResponseEntity<Void> saveNewTrip(final TripResource trip) {
        tripService.saveNewTrip(trip);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> updateTripById(final String id, final TripResource trip) {
        tripService.updateTripById(id, trip);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> orderTrip(final String tripId, final String passengerId) {
        tripService.addPassengerToTrip(tripId, passengerId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> cancelTripById(final String id) {
        tripService.changeTripStatusById(id, TripStatus.CANCELED);
        return ResponseEntity.accepted().build();
    }
}
