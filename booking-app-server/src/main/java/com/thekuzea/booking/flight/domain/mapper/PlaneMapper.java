package com.thekuzea.booking.flight.domain.mapper;

import org.mapstruct.Mapper;

import com.thekuzea.booking.api.dto.PlaneResource;
import com.thekuzea.booking.flight.domain.model.Plane;

@Mapper
public interface PlaneMapper {

    Plane resourceToModel(PlaneResource resource);

    PlaneResource modelToResource(Plane model);
}
