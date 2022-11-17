package com.thekuzea.booking.profile.service;

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

import com.thekuzea.booking.api.dto.PrivilegeGroupPageResource;
import com.thekuzea.booking.api.dto.PrivilegeGroupResource;
import com.thekuzea.booking.infrastructure.persistence.PrivilegeGroupRepository;
import com.thekuzea.booking.profile.domain.mapper.PrivilegeGroupMapper;
import com.thekuzea.booking.profile.domain.model.PrivilegeGroup;
import com.thekuzea.booking.support.alert.AlertCode;
import com.thekuzea.booking.support.alert.AlertService;

@Service
@RequiredArgsConstructor
public class PrivilegeGroupServiceImpl implements PrivilegeGroupService {

    private final PrivilegeGroupMapper privilegeGroupMapper = Mappers.getMapper(PrivilegeGroupMapper.class);

    private final PrivilegeGroupRepository privilegeGroupRepository;

    private final AlertService alertService;

    @Override
    public String getDefaultPrivilegeGroupId() {
        final Optional<PrivilegeGroup> foundPrivilegeGroup = privilegeGroupRepository.findDefaultPrivilegeGroup();
        if (foundPrivilegeGroup.isPresent()) {
            return foundPrivilegeGroup.get().getId();
        }

        final String errorMessage = alertService.logAlertByCode(AlertCode.S004, LogLevel.ERROR);
        throw new IllegalArgumentException(errorMessage);
    }

    @Override
    public List<String> getPrivilegesById(final String id) {
        return getPrivilegeGroupById(id).getPrivileges();
    }

    @Override
    public PrivilegeGroupResource getPrivilegeGroupById(final String id) {
        final Optional<PrivilegeGroup> foundPrivilegeGroup = privilegeGroupRepository.findById(id);
        if (foundPrivilegeGroup.isPresent()) {
            return privilegeGroupMapper.modelToResource(foundPrivilegeGroup.get());
        }

        final String errorMessage = alertService.logAlertByCode(AlertCode.B301, LogLevel.WARN, id);
        throw new IllegalArgumentException(errorMessage);
    }

    @Override
    public PrivilegeGroupPageResource getAllProfilesByParameters(final int size, final int page) {
        final Pageable pageRequest = PageRequest.of(page, size);
        final Page<PrivilegeGroup> privilegeGroupPage = privilegeGroupRepository.findAll(pageRequest);

        final List<PrivilegeGroupResource> privilegeGroupResources = privilegeGroupPage.getContent()
                .stream()
                .map(privilegeGroupMapper::modelToResource)
                .collect(Collectors.toList());

        return new PrivilegeGroupPageResource()
                .size(size)
                .page(page)
                .total(privilegeGroupPage.getTotalElements())
                .privilegeGroups(privilegeGroupResources);
    }

    @Override
    public void saveNewPrivilegeGroup(final PrivilegeGroupResource privilegeGroupResource) {
        final String groupName = privilegeGroupResource.getGroupName();
        final boolean existsGroupName = privilegeGroupRepository.existsByGroupName(groupName);
        if (existsGroupName) {
            final String errorMessage = alertService.logAlertByCode(AlertCode.B302, LogLevel.WARN, groupName);
            throw new IllegalArgumentException(errorMessage);
        }

        final boolean existsDefaultGroupPrivilege = privilegeGroupRepository.existsDefaultPrivilegeGroup();
        final boolean hasDtoDefaultPrivilegeGroupProperty = privilegeGroupResource.getIsDefaultGroup();
        if (existsDefaultGroupPrivilege && hasDtoDefaultPrivilegeGroupProperty) {
            final String errorMessage = alertService.logAlertByCode(AlertCode.B303, LogLevel.WARN);
            throw new IllegalArgumentException(errorMessage);
        }

        final PrivilegeGroup privilegeGroup = privilegeGroupMapper.resourceToModel(privilegeGroupResource);
        privilegeGroupRepository.save(privilegeGroup);
    }

    @Override
    public void updatePrivilegeGroupById(final String id, final PrivilegeGroupResource privilegeGroupResource) {
        final Optional<PrivilegeGroup> foundPrivilegeGroupById = privilegeGroupRepository.findById(id);
        if (!foundPrivilegeGroupById.isPresent()) {
            final String errorMessage = alertService.logAlertByCode(AlertCode.B301, LogLevel.WARN, id);
            throw new IllegalArgumentException(errorMessage);
        }

        final Optional<PrivilegeGroup> defaultGroupPrivilege = privilegeGroupRepository.findDefaultPrivilegeGroup();
        defaultGroupPrivilege.ifPresent(privilegeGroup -> {
            final boolean resourcePrivilegeGroupIsDefault = privilegeGroupResource.getIsDefaultGroup();
            final boolean groupNamesAreNotEqual = !privilegeGroupResource.getGroupName().equals(privilegeGroup.getGroupName());

            if (groupNamesAreNotEqual && resourcePrivilegeGroupIsDefault) {
                final String errorMessage = alertService.logAlertByCode(AlertCode.B303, LogLevel.WARN);
                throw new IllegalArgumentException(errorMessage);
            }
        });

        final String groupName = privilegeGroupResource.getGroupName();
        final Optional<PrivilegeGroup> foundPrivilegeGroupByName = privilegeGroupRepository.findByGroupName(groupName);
        foundPrivilegeGroupByName.ifPresent(privilegeGroupByName -> {
            final boolean idIsNotEqualToFoundOne = !id.equals(privilegeGroupByName.getId());

            if (idIsNotEqualToFoundOne) {
                final String errorMessage = alertService.logAlertByCode(AlertCode.B302, LogLevel.WARN, groupName);
                throw new IllegalArgumentException(errorMessage);
            }
        });

        final PrivilegeGroup privilegeGroup = foundPrivilegeGroupById.get();
        privilegeGroup.setGroupName(privilegeGroupResource.getGroupName());
        privilegeGroup.setDefaultGroup(privilegeGroupResource.getIsDefaultGroup());
        privilegeGroup.setPrivileges(privilegeGroupResource.getPrivileges());

        privilegeGroupRepository.save(privilegeGroup);
    }
}
