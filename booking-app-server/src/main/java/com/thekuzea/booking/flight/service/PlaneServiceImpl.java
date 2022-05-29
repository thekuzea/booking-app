package com.thekuzea.booking.flight.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thekuzea.booking.api.dto.PlanePageResource;
import com.thekuzea.booking.api.dto.PlaneResource;
import com.thekuzea.booking.api.dto.TechnicalStatus;
import com.thekuzea.booking.flight.domain.mapper.PlaneMapper;
import com.thekuzea.booking.flight.domain.model.Plane;
import com.thekuzea.booking.infrastructure.persistence.PlaneRepository;
import com.thekuzea.booking.support.alert.AlertCode;
import com.thekuzea.booking.support.alert.AlertService;

@Service
@RequiredArgsConstructor
public class PlaneServiceImpl implements PlaneService {

    private final PlaneMapper planeMapper = Mappers.getMapper(PlaneMapper.class);

    private final PlaneRepository planeRepository;

    private final AlertService alertService;

    @Override
    public PlaneResource getPlaneById(final String id) {
        final Plane plane = validateAndGetPlaneById(id);
        return planeMapper.modelToResource(plane);
    }

    @Override
    public PlanePageResource getAllPlanesByParameters(final int size, final int page) {
        final Pageable pageRequest = PageRequest.of(page, size);
        final Page<Plane> planePage = planeRepository.findAll(pageRequest);

        final List<PlaneResource> planeResources = planePage.getContent()
                .stream()
                .map(planeMapper::modelToResource)
                .collect(Collectors.toList());

        return new PlanePageResource()
                .size(size)
                .page(page)
                .total(planePage.getTotalElements())
                .planes(planeResources);
    }

    @Override
    public void saveNewPlane(final PlaneResource planeResource) {
        final TechnicalStatus technicalStatus = TechnicalStatus.AVAILABLE;
        final boolean planeExists = planeRepository.existsByParameters(
                planeResource.getName(),
                planeResource.getPlaneNumber(),
                planeResource.getOriginCountryCode(),
                technicalStatus
        );

        if (planeExists) {
            alertService.logAndThrowException(AlertCode.B101, IllegalArgumentException.class, LogLevel.WARN);
        }

        final Plane mappedPlane = planeMapper.resourceToModel(planeResource);
        mappedPlane.setTechnicalStatus(technicalStatus);
        planeRepository.save(mappedPlane);
    }

    @Override
    public void decommissionPlaneById(final String id) {
        final Plane currentPlane = validateAndGetPlaneById(id);
        currentPlane.setTechnicalStatus(TechnicalStatus.DECOMMISSIONED);

        planeRepository.save(currentPlane);
    }

    private Plane validateAndGetPlaneById(final String id) {
        final Optional<Plane> foundPlane = planeRepository.findById(id);
        if (!foundPlane.isPresent()) {
            alertService.logAndThrowException(AlertCode.B102, IllegalArgumentException.class, LogLevel.WARN, id);
        }

        return foundPlane.get();
    }
}
