package com.thekuzea.booking.flight.domain.mapper

import com.thekuzea.booking.api.dto.TripResource
import com.thekuzea.booking.flight.domain.model.Trip
import com.thekuzea.booking.support.util.DateTimeUtil

class TripMapper {

    static Trip resourceToModel(final TripResource resource) {
        final Trip model = new Trip()
        model.setTripClassType(resource.getTripClassType())
        model.setTripStatus(resource.getTripStatus())
        model.setCountryCodeOfDeparture(resource.getCountryCodeOfDeparture())
        model.setCityOfDeparture(resource.getCityOfDeparture())
        model.setDepartureDateTime(DateTimeUtil.convertStringToOffsetDateTime(resource.getDepartureDateTime()))
        model.setCountryCodeOfArrival(resource.getCountryCodeOfArrival())
        model.setCityOfArrival(resource.getCityOfArrival())
        model.setDateTimeOfArrival(DateTimeUtil.convertStringToOffsetDateTime(resource.getDateTimeOfArrival()))
        model.setPlaneId(resource.getPlane().getId())

        model
    }

    static TripResource modelToResource(final Trip model) {
        new TripResource()
                .id(model.getId())
                .tripClassType(model.getTripClassType())
                .tripStatus(model.getTripStatus())
                .countryCodeOfDeparture(model.getCountryCodeOfDeparture())
                .cityOfDeparture(model.getCityOfDeparture())
                .departureDateTime(DateTimeUtil.convertOffsetDateTimeToString(model.getDepartureDateTime()))
                .countryCodeOfArrival(model.getCountryCodeOfArrival())
                .cityOfArrival(model.getCityOfArrival())
                .dateTimeOfArrival(DateTimeUtil.convertOffsetDateTimeToString(model.getDateTimeOfArrival()))
    }
}
