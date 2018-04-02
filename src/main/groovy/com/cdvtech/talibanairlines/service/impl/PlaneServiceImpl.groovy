package com.cdvtech.talibanairlines.service.impl

import com.cdvtech.talibanairlines.model.entity.Plane
import com.cdvtech.talibanairlines.repository.PlaneRepository
import com.cdvtech.talibanairlines.service.PlaneService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlaneServiceImpl implements PlaneService {

    @Autowired
    PlaneRepository planeRepository

    @Override
    List<Plane> getAll() {
        return planeRepository.findAll()
    }

    @Override
    Plane getById(String id) {
        return planeRepository.findById(id).get()
    }

    @Override
    Plane getByName(String name) {
        return planeRepository.findByName()
    }

    @Override
    Plane save(Plane plane) {
        List<Plane> tempPlaneList = planeRepository.findAll()

        for(def it in tempPlaneList) {
            if(it == plane) {
                return null
            }
        }

        planeRepository.save(plane)
        return plane
    }

    @Override
    Plane update(String id, Plane plane) {
        Plane currentPlane

        try {
            currentPlane = planeRepository.findById(id).get()
        } catch (NoSuchElementException ignored) {
            return null
        }

        if (currentPlane != null) {
            if (plane.name != null) {
                currentPlane.name = plane.name
            }
            if (plane.trips != null) {
                currentPlane.trips = plane.trips
            }

            planeRepository.save(currentPlane)
            return currentPlane
        }
    }

    @Override
    Plane delete(String id) {
        Optional<Plane> plane = planeRepository.findById(id)

        if (!plane.isPresent()) {
            planeRepository.delete(plane.get())

            return plane.get()
        }

        return null
    }
}
