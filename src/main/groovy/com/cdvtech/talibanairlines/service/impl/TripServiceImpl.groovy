package com.cdvtech.talibanairlines.service.impl

import com.cdvtech.talibanairlines.model.entity.Trip
import com.cdvtech.talibanairlines.repository.TripRepository
import com.cdvtech.talibanairlines.service.TripService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository

    @Override
    List<Trip> getAll() {
        return tripRepository.findAll()
    }

    @Override
    Trip getById(String id) {
        return tripRepository.findById(id).get()
    }

    @Override
    List<Trip> getSpecifiedOneway(Trip trip) {
        return tripRepository.searchSpecifiedTrip(trip.placeOfDeparture, trip.placeOfArrival, trip.departureDate,
                trip.tripClass)
    }

    @Override
    List<List<Trip>> getSpecifiedWithReturn(Trip trip) {
        List<List<Trip>> doubleList = new ArrayList<>()

        List<Trip> listTo = tripRepository.searchSpecifiedTrip(trip.placeOfDeparture, trip.placeOfArrival,
                trip.departureDate, trip.tripClass)
        List<Trip> listFrom = tripRepository.searchSpecifiedTrip(trip.placeOfArrival, trip.placeOfDeparture,
                trip.returningDate, trip.tripClass)

        doubleList.add(listTo)
        doubleList.add(listFrom)

        return doubleList
    }

    @Override
    Trip save(Trip trip) {
        List<Trip> tempTripList = tripRepository.findAll()

        for(def it in tempTripList) {
            if(it == trip) {
                return null
            }
        }

        tripRepository.save(trip)
        return trip
    }

    @Override
    Trip update(String id, Trip trip) {
        Trip currentTrip

        try {
            currentTrip = tripRepository.findById(id).get()
        } catch (NoSuchElementException ignored) {
            return null
        }

        if (currentTrip != null) {
            if (trip.plane != null) {
                currentTrip.plane = trip.plane
            }
            if (trip.departureDate != null) {
                currentTrip.departureDate = trip.departureDate
            }
            if (trip.placeOfDeparture != null) {
                currentTrip.placeOfDeparture = trip.placeOfDeparture
            }
            if (trip.placeOfArrival != null) {
                currentTrip.placeOfArrival = trip.placeOfArrival
            }
            if (trip.tripClass != null) {
                currentTrip.tripClass = trip.tripClass
            }
            if (trip.passengers != null) {
                currentTrip.passengers = trip.passengers
            }

            tripRepository.save(currentTrip)
            return currentTrip
        }

        return null
    }

    @Override
    Trip delete(String id) {
        Optional<Trip> trip = tripRepository.findById(id)

        if (!trip.isPresent()) {
            tripRepository.delete(trip.get())

            return trip.get()
        }

        return null
    }
}
