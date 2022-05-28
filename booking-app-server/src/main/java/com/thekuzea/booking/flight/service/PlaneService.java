package com.thekuzea.booking.flight.service;

import com.thekuzea.booking.api.dto.PlanePageResource;
import com.thekuzea.booking.api.dto.PlaneResource;

public interface PlaneService {

    PlaneResource getPlaneById(String id);

    PlanePageResource getAllPlanesByParameters(int size, int page);

    void saveNewPlane(PlaneResource planeResource);

    void decommissionPlaneById(String id);
}
