package com.thekuzea.booking.flight.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.logging.LogLevel
import org.springframework.stereotype.Service

import com.thekuzea.booking.api.dto.CheckInPassengerResource
import com.thekuzea.booking.api.dto.PlaneResource
import com.thekuzea.booking.api.dto.SeatResource
import com.thekuzea.booking.api.dto.TripStatus
import com.thekuzea.booking.flight.domain.model.Flight
import com.thekuzea.booking.flight.domain.model.Seat
import com.thekuzea.booking.infrastructure.persistence.FlightRepository
import com.thekuzea.booking.support.alert.AlertCode
import com.thekuzea.booking.support.alert.AlertService
import com.thekuzea.booking.support.util.SeatUtil

@Service
class FlightServiceImpl implements FlightService {

    @Autowired
    FlightRepository flightRepository

    @Autowired
    TripService tripService

    @Autowired
    PlaneService planeService

    @Autowired
    AlertService alertService

    @Override
    void registerFlight(final String tripId) {
        final Optional<Flight> foundFlight = flightRepository.findByTripId(tripId)
        if (foundFlight.isPresent()) {
            alertService.logAndThrowException(AlertCode.B401, IllegalArgumentException.class, LogLevel.WARN)
        }

        final String planeId = tripService.getPlaneIdForTrip(tripId)
        final PlaneResource plane = planeService.getPlaneById(planeId)
        final Seat[][] generatedSeats = SeatUtil.createSeatList(plane.getRowsAmount(), plane.getColumnsAmount())

        final Flight flight = new Flight()
        flight.setTripId(tripId)
        flight.setSeats(generatedSeats)

        flightRepository.save(flight)
        tripService.changeTripStatusById(tripId, TripStatus.READY)
    }

    @Override
    void checkInPassenger(final CheckInPassengerResource checkInPassengerResource) {
        final String flightId = checkInPassengerResource.getFlightId()
        final Optional<Flight> foundFlight = flightRepository.findById(flightId)
        if (!foundFlight.isPresent()) {
            alertService.logAndThrowException(AlertCode.B402, IllegalArgumentException.class, LogLevel.WARN, flightId)
        }

        final Flight flight = foundFlight.get()
        final SeatResource seatResource = checkInPassengerResource.getSeat()
        final Seat seat = SeatUtil.resolveSeat(flight.getSeats(), seatResource.getRow(), seatResource.getColumn())
        seat.setIsAvailable(false)
        seat.setPassengerId(seatResource.getPassengerId())

        flightRepository.save(flight)
    }
}