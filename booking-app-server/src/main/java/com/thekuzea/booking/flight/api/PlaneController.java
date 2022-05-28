package com.thekuzea.booking.flight.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.thekuzea.booking.api.PlaneApi;
import com.thekuzea.booking.api.dto.PlanePageResource;
import com.thekuzea.booking.api.dto.PlaneResource;
import com.thekuzea.booking.flight.service.PlaneService;

@RestController
@RequiredArgsConstructor
public class PlaneController implements PlaneApi {

    private final PlaneService planeService;

    @Override
    public ResponseEntity<PlaneResource> getPlaneById(final String id) {
        return ResponseEntity.ok(planeService.getPlaneById(id));
    }

    @Override
    public ResponseEntity<PlanePageResource> getAllPlanesByParameters(final Integer size, final Integer page) {
        return ResponseEntity.ok(planeService.getAllPlanesByParameters(size, page));
    }

    @Override
    public ResponseEntity<Void> saveNewPlane(final PlaneResource plane) {
        planeService.saveNewPlane(plane);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> decommissionPlaneById(final String id) {
        planeService.decommissionPlaneById(id);
        return ResponseEntity.accepted().build();
    }
}
