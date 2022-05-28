package com.thekuzea.booking.flight.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.thekuzea.booking.api.FlightApi;
import com.thekuzea.booking.api.dto.CheckInPassengerResource;
import com.thekuzea.booking.flight.service.FlightService;

@RestController
@RequiredArgsConstructor
public class FlightController implements FlightApi {

    private final FlightService flightService;

    @Override
    public ResponseEntity<Void> registerFlight(final String tripId) {
        flightService.registerFlight(tripId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> checkInPassenger(final CheckInPassengerResource checkInPassengerResource) {
        flightService.checkInPassenger(checkInPassengerResource);
        return ResponseEntity.accepted().build();
    }
}
