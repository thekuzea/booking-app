package com.cdvtech.talibanairlines.controller

import com.cdvtech.talibanairlines.model.entity.Trip
import com.cdvtech.talibanairlines.service.TripService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trips")
class TripController {

    @Autowired
    TripService tripService

    @GetMapping("{id}")
    Trip getOne(@PathVariable("id") String id) {

        return tripService.getById(id)
    }

    @GetMapping("")
    List<Trip> getAll() {
        return tripService.getAll()
    }

    @PostMapping("specified")
    ResponseEntity<?> getSpecifiedTrip(@RequestBody Trip trip) {
        List<Trip> foundTrips
        List<List<Trip>> tripsWithReturn

        if(trip.isOneway) {
            foundTrips = tripService.getSpecifiedOneway(trip)

            if (foundTrips != null) {
                return new ResponseEntity<List<Trip>>(foundTrips, HttpStatus.OK)
            }
        } else {
            tripsWithReturn = tripService.getSpecifiedWithReturn(trip)

            if (tripsWithReturn != null) {
                return new ResponseEntity<List<List<Trip>>>(tripsWithReturn, HttpStatus.OK)
            }
        }

        return new ResponseEntity<>(new String("Unable to find trips by this data."), HttpStatus.CONFLICT)
    }

    @PostMapping("")
    ResponseEntity<?> addTrip(@RequestBody Trip trip) {
        if (tripService.save(trip) != null) {
            return new ResponseEntity<>(HttpStatus.CREATED)
        }

        return new ResponseEntity<>(new String("Unable to save trip. It already exists."), HttpStatus.CONFLICT)
    }

    @PutMapping(value = "{id}")
    ResponseEntity<?> updateTrip(@PathVariable("id") String id, @RequestBody Trip trip) {
        Trip currentTrip = tripService.update(id, trip)

        if (currentTrip == null) {
            return new ResponseEntity(new String("Unable to update trip with id: " + id + ". It's not found."),
                    HttpStatus.NOT_FOUND)
        }

        return new ResponseEntity<Trip>(currentTrip, HttpStatus.OK)
    }

    @DeleteMapping(value = "{id}")
    ResponseEntity<?> deleteTrip(@PathVariable("id") String id) {
        Trip deletedTrip = tripService.delete(id)

        if (deletedTrip == null) {
            return new ResponseEntity(new String("Unable to delete trip with id " + id + ". It's not found."),
                    HttpStatus.NOT_FOUND)
        }

        return new ResponseEntity<>(HttpStatus.OK)
    }
}
