package com.thekuzea.booking.flight.service

import com.thekuzea.booking.api.dto.TripPageResource
import com.thekuzea.booking.api.dto.TripResource
import com.thekuzea.booking.api.dto.TripStatus

interface TripService {

    String getPlaneIdForTrip(String tripId)

    TripResource getTripById(String id, boolean isDetailed)

    TripPageResource getAllTripsByParameters(int size, int page)

    void saveNewTrip(TripResource tripResource)

    void updateTripById(String id, TripResource tripResource)

    void addPassengerToTrip(String id, String passengerId)

    void changeTripStatusById(String id, TripStatus tripStatus)
}
