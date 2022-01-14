package com.thekuzea.booking.flight.domain.mapper

import com.thekuzea.booking.api.dto.PlaneResource
import com.thekuzea.booking.flight.domain.model.Plane

class PlaneMapper {

    static Plane resourceToModel(final PlaneResource resource) {
        final Plane model = new Plane()
        model.setName(resource.getName())
        model.setPlaneNumber(resource.getPlaneNumber())
        model.setOriginCountryCode(resource.getOriginCountryCode())
        model.setTechnicalStatus(resource.getTechnicalStatus())
        model.setSeatsAmount(resource.getSeatsAmount())
        model.setColumnsAmount(resource.getColumnsAmount())
        model.setRowsAmount(resource.getRowsAmount())

        model
    }

    static PlaneResource modelToResource(final Plane model) {
        new PlaneResource()
                .id(model.getId())
                .name(model.getName())
                .planeNumber(model.getPlaneNumber())
                .originCountryCode(model.getOriginCountryCode())
                .technicalStatus(model.getTechnicalStatus())
                .seatsAmount(model.getSeatsAmount())
                .columnsAmount(model.getColumnsAmount())
                .rowsAmount(model.getRowsAmount())
    }
}
