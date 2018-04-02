package com.cdvtech.talibanairlines.service

import com.cdvtech.talibanairlines.model.entity.Trip

interface TripService {

    List<Trip> getAll()

    Trip getById(String id)

    List<Trip> getSpecifiedOneway(Trip trip)

    List<List<Trip>> getSpecifiedWithReturn(Trip trip)

    Trip save(Trip trip)

    Trip update(String id, Trip trip)

    Trip delete(String id)

}