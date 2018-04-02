package com.cdvtech.talibanairlines.controller

import com.cdvtech.talibanairlines.model.entity.Plane
import com.cdvtech.talibanairlines.repository.PlaneRepository
import com.cdvtech.talibanairlines.service.PlaneService
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
@RequestMapping("/planes")
class PlaneController {

    @Autowired
    PlaneService planeService

    @GetMapping("{id}")
    Plane getById(@PathVariable("id") String id) {
        return planeService.getById(id)
    }

    @GetMapping("{name}")
    Plane getByName(@PathVariable("name") String name) {
        return planeService.getByName(name)
    }

    @GetMapping("")
    List<Plane> getAll() {
        return planeService.getAll()
    }

    @PostMapping("")
    ResponseEntity<?> addPlane(@RequestBody Plane plane) {
        if (planeService.save(plane) != null) {
            return new ResponseEntity<>(HttpStatus.CREATED)
        }

        return new ResponseEntity<>(new String("Unable to save plane. It already exists."), HttpStatus.CONFLICT)
    }

    @PutMapping(value = "{id}")
    ResponseEntity<?> updatePlane(@PathVariable("id") String id, @RequestBody Plane plane) {
        Plane currentPlane = planeService.update(id, plane)

        if (currentPlane == null) {
            return new ResponseEntity(new String("Unable to update plane with id: " + id + ". It's not found."),
                    HttpStatus.NOT_FOUND)
        }

        return new ResponseEntity<Plane>(currentPlane, HttpStatus.OK)
    }

    @DeleteMapping(value = "{id}")
    ResponseEntity<?> deletePlane(@PathVariable("id") String id) {
        Plane deletedPlane = planeService.delete(id)

        if (deletedPlane == null) {
            return new ResponseEntity(new String("Unable to delete plane with id " + id + ". It's not found."),
                    HttpStatus.NOT_FOUND)
        }

        return new ResponseEntity<>(HttpStatus.OK)
    }
}