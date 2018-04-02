package com.cdvtech.talibanairlines.service

import com.cdvtech.talibanairlines.model.entity.Plane

interface PlaneService {

    List<Plane> getAll()

    Plane getById(String id)

    Plane getByName(String name)

    Plane save(Plane plane)

    Plane update(String id, Plane plane)

    Plane delete(String id)

}