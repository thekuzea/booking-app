package com.thekuzea.booking.flight.domain.model

import java.time.OffsetDateTime

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import com.thekuzea.booking.api.dto.TripClassType
import com.thekuzea.booking.api.dto.TripStatus

@Document(collection = "trips")
class Trip {

    @Id
    String id

    TripClassType tripClassType

    TripStatus tripStatus

    String countryCodeOfDeparture

    String cityOfDeparture

    OffsetDateTime departureDateTime

    String countryCodeOfArrival

    String cityOfArrival

    OffsetDateTime dateTimeOfArrival

    String planeId

    List<String> passengerIds
}
