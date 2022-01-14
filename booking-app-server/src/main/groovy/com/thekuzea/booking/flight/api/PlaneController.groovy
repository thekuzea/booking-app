package com.thekuzea.booking.flight.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import com.thekuzea.booking.api.PlaneApi
import com.thekuzea.booking.api.dto.PlanePageResource
import com.thekuzea.booking.api.dto.PlaneResource

import com.thekuzea.booking.flight.service.PlaneService

@RestController
class PlaneController implements PlaneApi {

    @Autowired
    PlaneService planeService

    @Override
    ResponseEntity<PlaneResource> getPlaneById(final String id) {
        ResponseEntity.ok(planeService.getPlaneById(id))
    }

    @Override
    ResponseEntity<PlanePageResource> getAllPlanesByParameters(final Integer size, final Integer page) {
        ResponseEntity.ok(planeService.getAllPlanesByParameters(size, page))
    }

    @Override
    ResponseEntity<Void> saveNewPlane(final PlaneResource plane) {
        planeService.saveNewPlane(plane)
        ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @Override
    ResponseEntity<Void> decommissionPlaneById(final String id) {
        planeService.decommissionPlaneById(id)
        ResponseEntity.accepted().build()
    }
}
