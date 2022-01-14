package com.thekuzea.booking.flight.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import com.thekuzea.booking.api.FlightApi
import com.thekuzea.booking.api.dto.CheckInPassengerResource
import com.thekuzea.booking.flight.service.FlightService

@RestController
class FlightController implements FlightApi {

    @Autowired
    FlightService flightService

    @Override
    ResponseEntity<Void> registerFlight(final String tripId) {
        flightService.registerFlight(tripId)
        ResponseEntity.accepted().build()
    }

    @Override
    ResponseEntity<Void> checkInPassenger(final CheckInPassengerResource checkInPassengerResource) {
        flightService.checkInPassenger(checkInPassengerResource)
        ResponseEntity.accepted().build()
    }
}
