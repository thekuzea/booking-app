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
        if (!foundPrivilegeGroup.isPresent()) {
            alertService.logAndThrowException(AlertCode.S004, IllegalArgumentException.class, LogLevel.ERROR);
        }

        return foundPrivilegeGroup.get().getId();
    }

    @Override
    public List<String> getPrivilegesById(final String id) {
        return getPrivilegeGroupById(id).getPrivileges();
    }

    @Override
    public PrivilegeGroupResource getPrivilegeGroupById(final String id) {
        final Optional<PrivilegeGroup> foundPrivilegeGroup = privilegeGroupRepository.findById(id);
        if (!foundPrivilegeGroup.isPresent()) {
            alertService.logAndThrowException(AlertCode.B301, IllegalArgumentException.class, LogLevel.WARN, id);
        }

        return privilegeGroupMapper.modelToResource(foundPrivilegeGroup.get());
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
            alertService.logAndThrowException(AlertCode.B302, IllegalArgumentException.class, LogLevel.WARN, groupName);
        }

        final boolean existsDefaultGroupPrivilege = privilegeGroupRepository.existsDefaultPrivilegeGroup();
        final boolean hasDtoDefaultPrivilegeGroupProperty = privilegeGroupResource.getIsDefaultGroup();
        if (existsDefaultGroupPrivilege && hasDtoDefaultPrivilegeGroupProperty) {
            alertService.logAndThrowException(AlertCode.B303, IllegalArgumentException.class, LogLevel.WARN);
        }

        final PrivilegeGroup privilegeGroup = privilegeGroupMapper.resourceToModel(privilegeGroupResource);
        privilegeGroupRepository.save(privilegeGroup);
    }

    @Override
    public void updatePrivilegeGroupById(final String id, final PrivilegeGroupResource privilegeGroupResource) {
        final Optional<PrivilegeGroup> foundPrivilegeGroupById = privilegeGroupRepository.findById(id);
        if (!foundPrivilegeGroupById.isPresent()) {
            alertService.logAndThrowException(AlertCode.B301, IllegalArgumentException.class, LogLevel.WARN, id);
        }

        final Optional<PrivilegeGroup> defaultGroupPrivilege = privilegeGroupRepository.findDefaultPrivilegeGroup();
        final boolean hasDtoDefaultPrivilegeGroupProperty = privilegeGroupResource.getIsDefaultGroup();
        if (defaultGroupPrivilege.isPresent() && hasDtoDefaultPrivilegeGroupProperty) {

            if (!privilegeGroupResource.getGroupName().equals(defaultGroupPrivilege.get().getGroupName())) {
                alertService.logAndThrowException(AlertCode.B303, IllegalArgumentException.class, LogLevel.WARN);
            }
        }

        final String groupName = privilegeGroupResource.getGroupName();
        final Optional<PrivilegeGroup> foundPrivilegeGroupByName = privilegeGroupRepository.findByGroupName(groupName);
        if (foundPrivilegeGroupByName.isPresent()) {

            if (!id.equals(foundPrivilegeGroupByName.get().getId())) {
                alertService.logAndThrowException(AlertCode.B302, IllegalArgumentException.class, LogLevel.WARN, groupName);
            }
        }

        final PrivilegeGroup privilegeGroup = foundPrivilegeGroupById.get();
        privilegeGroup.setGroupName(privilegeGroupResource.getGroupName());
        privilegeGroup.setDefaultGroup(privilegeGroupResource.getIsDefaultGroup());
        privilegeGroup.setPrivileges(privilegeGroupResource.getPrivileges());

        privilegeGroupRepository.save(privilegeGroup);
    }
}
