package com.cdvtech.talibanairlines.model.entity

import com.cdvtech.talibanairlines.model.enums.TripClass
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

import javax.validation.constraints.NotNull
import java.time.LocalDate

@Document(collection = "trips")
class Trip {

    @Id
    String id

    @NotNull
    String placeOfDeparture

    @NotNull
    String placeOfArrival

    @NotNull
    LocalDate departureDate

    @Transient
    Boolean isOneway

    @Transient
    LocalDate returningDate

    @NotNull
    TripClass tripClass

    @DBRef
    Map<Integer, Profile> passengers = new HashMap<>()

    @DBRef
    Plane plane

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Trip)) return false

        Trip trip = (Trip) o

        if (departureDate != trip.departureDate) return false
        if (placeOfArrival != trip.placeOfArrival) return false
        if (placeOfDeparture != trip.placeOfDeparture) return false
        if (plane.id != trip.plane.id) return false
        if (tripClass != trip.tripClass) return false

        return true
    }
}


