package com.thekuzea.booking.flight.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "flights")
class Flight {

    @Id
    String id

    String tripId

    Seat[][] seats
}
