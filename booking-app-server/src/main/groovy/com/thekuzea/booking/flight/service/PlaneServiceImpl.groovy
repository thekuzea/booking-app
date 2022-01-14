package com.thekuzea.booking.flight.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.logging.LogLevel
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

import com.thekuzea.booking.api.dto.PlanePageResource
import com.thekuzea.booking.api.dto.PlaneResource
import com.thekuzea.booking.api.dto.TechnicalStatus
import com.thekuzea.booking.flight.domain.mapper.PlaneMapper
import com.thekuzea.booking.flight.domain.model.Plane
import com.thekuzea.booking.infrastructure.persistence.PlaneRepository
import com.thekuzea.booking.support.alert.AlertCode
import com.thekuzea.booking.support.alert.AlertService

@Service
class PlaneServiceImpl implements PlaneService {

    @Autowired
    PlaneRepository planeRepository

    @Autowired
    AlertService alertService

    @Override
    PlaneResource getPlaneById(final String id) {
        final Plane plane = validateAndGetPlaneById(id)
        PlaneMapper.modelToResource(plane)
    }

    @Override
    PlanePageResource getAllPlanesByParameters(final int size, final int page) {
        final Pageable pageRequest = PageRequest.of(page, size)
        final Page<Plane> planePage = planeRepository.findAll(pageRequest)

        final List<PlaneResource> planeResources = planePage.getContent()
                .collect { PlaneMapper.modelToResource(it) }

        new PlanePageResource()
                .size(size)
                .page(page)
                .total(planePage.getTotalElements())
                .planes(planeResources)
    }

    @Override
    void saveNewPlane(final PlaneResource planeResource) {
        final TechnicalStatus technicalStatus = TechnicalStatus.AVAILABLE
        final boolean planeExists = planeRepository.existsByParameters(
                planeResource.getName(),
                planeResource.getPlaneNumber(),
                planeResource.getOriginCountryCode(),
                technicalStatus
        )

        if (planeExists) {
            alertService.logAndThrowException(AlertCode.B101, IllegalArgumentException.class, LogLevel.WARN)
        }

        final Plane mappedPlane = PlaneMapper.resourceToModel(planeResource)
        mappedPlane.setTechnicalStatus(technicalStatus)
        planeRepository.save(mappedPlane)
    }

    @Override
    void decommissionPlaneById(final String id) {
        final Plane currentPlane = validateAndGetPlaneById(id)
        currentPlane.setTechnicalStatus(TechnicalStatus.DECOMMISSIONED)

        planeRepository.save(currentPlane)
    }

    private Plane validateAndGetPlaneById(final String id) {
        final Optional<Plane> foundPlane = planeRepository.findById(id)
        if (!foundPlane.isPresent()) {
            alertService.logAndThrowException(AlertCode.B102, IllegalArgumentException.class, LogLevel.WARN, id)
        }

        foundPlane.get()
    }
}
