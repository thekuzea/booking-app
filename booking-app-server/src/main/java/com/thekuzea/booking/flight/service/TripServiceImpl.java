package com.thekuzea.booking.flight.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thekuzea.booking.api.dto.PlaneResource;
import com.thekuzea.booking.api.dto.ProfileResource;
import com.thekuzea.booking.api.dto.TripPageResource;
import com.thekuzea.booking.api.dto.TripResource;
import com.thekuzea.booking.api.dto.TripStatus;
import com.thekuzea.booking.flight.domain.mapper.TripMapper;
import com.thekuzea.booking.flight.domain.model.Trip;
import com.thekuzea.booking.infrastructure.persistence.TripRepository;
import com.thekuzea.booking.profile.service.ProfileService;
import com.thekuzea.booking.support.alert.AlertCode;
import com.thekuzea.booking.support.alert.AlertService;
import com.thekuzea.booking.support.util.DateTimeUtil;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    private final PlaneService planeService;

    private final ProfileService profileService;

    private final AlertService alertService;

    @Override
    public String getPlaneIdForTrip(final String tripId) {
        final Trip trip = validateAndGetTripById(tripId);
        return trip.getPlaneId();
    }

    @Override
    public TripResource getTripById(final String id, final boolean isDetailed) {
        final Trip trip = validateAndGetTripById(id);
        final TripResource tripResource = TripMapper.modelToResource(trip);

        if (isDetailed) {
            final PlaneResource planeResource = planeService.getPlaneById(trip.getPlaneId());
            tripResource.setPlane(planeResource);

            final List<ProfileResource> passengers = profileService.getProfilesByIds(trip.getPassengerIds());
            tripResource.setPassengers(passengers);
        }

        return tripResource;
    }

    @Override
    public TripPageResource getAllTripsByParameters(final int size, final int page) {
        final Pageable pageRequest = PageRequest.of(page, size);
        final Page<Trip> tripPage = tripRepository.findAll(pageRequest);

        final List<TripResource> tripResources = tripPage.getContent()
                .stream()
                .map(TripMapper::modelToResource)
                .collect(Collectors.toList());

        return new TripPageResource()
                .size(size)
                .page(page)
                .total(tripPage.getTotalElements())
                .trips(tripResources);
    }

    @Override
    public void saveNewTrip(final TripResource tripResource) {
        final boolean tripExists = tripRepository.existsByParameters(
                tripResource.getTripClassType(),
                tripResource.getCountryCodeOfDeparture(),
                tripResource.getCityOfDeparture(),
                DateTimeUtil.convertStringToOffsetDateTime(tripResource.getDepartureDateTime()),
                tripResource.getCountryCodeOfArrival(),
                tripResource.getCityOfArrival(),
                DateTimeUtil.convertStringToOffsetDateTime(tripResource.getDateTimeOfArrival()),
                tripResource.getPlane().getId()
        );

        if (tripExists) {
            alertService.logAndThrowException(AlertCode.B201, IllegalArgumentException.class, LogLevel.WARN);
        }

        final Trip mappedTrip = TripMapper.resourceToModel(tripResource);
        mappedTrip.setTripStatus(TripStatus.CREATED);
        mappedTrip.setPassengerIds(Collections.emptyList());

        tripRepository.save(mappedTrip);
    }

    @Override
    public void updateTripById(final String id, final TripResource tripResource) {
        final Trip trip = validateAndGetTripById(id);
        final Trip mappedTrip = TripMapper.resourceToModel(tripResource);

        trip.setTripClassType(mappedTrip.getTripClassType());

        trip.setDepartureDateTime(mappedTrip.getDepartureDateTime());
        trip.setCountryCodeOfDeparture(mappedTrip.getCountryCodeOfDeparture());
        trip.setCityOfDeparture(mappedTrip.getCityOfDeparture());

        trip.setDateTimeOfArrival(mappedTrip.getDateTimeOfArrival());
        trip.setCountryCodeOfArrival(mappedTrip.getCountryCodeOfArrival());
        trip.setCityOfArrival(mappedTrip.getCityOfArrival());

        trip.setPlaneId(tripResource.getPlane().getId());

        tripRepository.save(trip);
    }

    @Override
    public void addPassengerToTrip(final String id, final String passengerId) {
        final Trip trip = validateAndGetTripById(id);
        trip.getPassengerIds().add(passengerId);

        tripRepository.save(trip);
    }

    @Override
    public void changeTripStatusById(final String id, final TripStatus tripStatus) {
        final Trip trip = validateAndGetTripById(id);
        trip.setTripStatus(tripStatus);

        tripRepository.save(trip);
    }

    private Trip validateAndGetTripById(final String id) {
        final Optional<Trip> foundTrip = tripRepository.findById(id);
        if (!foundTrip.isPresent()) {
            alertService.logAndThrowException(AlertCode.B202, IllegalArgumentException.class, LogLevel.WARN, id);
        }

        return foundTrip.get();
    }
}
