package com.cdvtech.talibanairlines.repository

import com.cdvtech.talibanairlines.model.entity.Trip
import com.cdvtech.talibanairlines.model.enums.TripClass
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

import java.time.LocalDate

@Repository
interface TripRepository extends MongoRepository<Trip, String> {

    @Query("{'placeOfDeparture' : ?0, 'placeOfArrival' : ?1, 'departureDate' : ?2, 'tripClass': ?3}")
    List<Trip> searchSpecifiedTrip(String placeOfDeparture, String placeOfArrival, LocalDate departureDate, TripClass tripClass)

}
