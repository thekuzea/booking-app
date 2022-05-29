package com.thekuzea.booking.flight.domain.mapper;

import java.time.OffsetDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thekuzea.booking.api.dto.TripResource;
import com.thekuzea.booking.flight.domain.model.Trip;
import com.thekuzea.booking.support.util.DateTimeUtil;

@Mapper
public interface TripMapper {

    @Mapping(source = "plane.id", target = "planeId")
    @Mapping(target = "passengerIds", ignore = true)
    Trip resourceToModel(TripResource resource);

    @Mapping(target = "plane", ignore = true)
    @Mapping(target = "passengers", ignore = true)
    TripResource modelToResource(Trip model);

    default OffsetDateTime map(String stringDateTime) {
        return DateTimeUtil.convertStringToOffsetDateTime(stringDateTime);
    }

    default String map(OffsetDateTime offsetDateTime) {
        return DateTimeUtil.convertOffsetDateTimeToString(offsetDateTime);
    }
}
