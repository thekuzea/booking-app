package com.thekuzea.booking.flight.service

import com.thekuzea.booking.api.dto.CheckInPassengerResource

interface FlightService {

    void registerFlight(String tripId)

    void checkInPassenger(CheckInPassengerResource checkInPassengerResource)
}
