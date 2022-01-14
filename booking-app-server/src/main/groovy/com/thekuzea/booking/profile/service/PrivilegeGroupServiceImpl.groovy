package com.thekuzea.booking.profile.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.logging.LogLevel
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

import com.thekuzea.booking.api.dto.PrivilegeGroupPageResource
import com.thekuzea.booking.api.dto.PrivilegeGroupResource
import com.thekuzea.booking.infrastructure.persistence.PrivilegeGroupRepository
import com.thekuzea.booking.profile.domain.mapper.PrivilegeGroupMapper
import com.thekuzea.booking.profile.domain.model.PrivilegeGroup
import com.thekuzea.booking.support.alert.AlertCode
import com.thekuzea.booking.support.alert.AlertService

@Service
class PrivilegeGroupServiceImpl implements PrivilegeGroupService {

    @Autowired
    PrivilegeGroupRepository privilegeGroupRepository

    @Autowired
    AlertService alertService

    @Override
    String getDefaultPrivilegeGroupId() {
        final Optional<PrivilegeGroup> foundPrivilegeGroup = privilegeGroupRepository.findDefaultPrivilegeGroup()
        if (!foundPrivilegeGroup.isPresent()) {
            alertService.logAndThrowException(AlertCode.S004, IllegalArgumentException.class, LogLevel.ERROR)
        }

        foundPrivilegeGroup.get().getId()
    }

    @Override
    List<String> getPrivilegesById(final String id) {
        getPrivilegeGroupById(id).getPrivileges()
    }

    @Override
    PrivilegeGroupResource getPrivilegeGroupById(final String id) {
        final Optional<PrivilegeGroup> foundPrivilegeGroup = privilegeGroupRepository.findById(id)
        if (!foundPrivilegeGroup.isPresent()) {
            alertService.logAndThrowException(AlertCode.B301, IllegalArgumentException.class, LogLevel.WARN, id)
        }

        PrivilegeGroupMapper.modelToResource(foundPrivilegeGroup.get())
    }

    @Override
    PrivilegeGroupPageResource getAllProfilesByParameters(final int size, final int page) {
        final Pageable pageRequest = PageRequest.of(page, size)
        final Page<PrivilegeGroup> privilegeGroupPage = privilegeGroupRepository.findAll(pageRequest)

        final List<PrivilegeGroupResource> privilegeGroupResources = privilegeGroupPage.getContent()
                .collect { PrivilegeGroupMapper.modelToResource(it) }

        new PrivilegeGroupPageResource()
                .size(size)
                .page(page)
                .total(privilegeGroupPage.getTotalElements())
                .privilegeGroups(privilegeGroupResources)
    }

    @Override
    void saveNewPrivilegeGroup(final PrivilegeGroupResource privilegeGroupResource) {
        final String groupName = privilegeGroupResource.getGroupName()
        final boolean existsGroupName = privilegeGroupRepository.existsByGroupName(groupName)
        if (existsGroupName) {
            alertService.logAndThrowException(AlertCode.B302, IllegalArgumentException.class, LogLevel.WARN, groupName)
        }

        final boolean existsDefaultGroupPrivilege = privilegeGroupRepository.existsDefaultPrivilegeGroup()
        final boolean hasDtoDefaultPrivilegeGroupProperty = privilegeGroupResource.getIsDefaultGroup()
        if (existsDefaultGroupPrivilege && hasDtoDefaultPrivilegeGroupProperty) {
            alertService.logAndThrowException(AlertCode.B303, IllegalArgumentException.class, LogLevel.WARN)
        }

        final PrivilegeGroup privilegeGroup = PrivilegeGroupMapper.resourceToModel(privilegeGroupResource)
        privilegeGroupRepository.save(privilegeGroup)
    }

    @Override
    void updatePrivilegeGroupById(final String id, final PrivilegeGroupResource privilegeGroupResource) {
        final Optional<PrivilegeGroup> foundPrivilegeGroupById = privilegeGroupRepository.findById(id)
        if (!foundPrivilegeGroupById.isPresent()) {
            alertService.logAndThrowException(AlertCode.B301, IllegalArgumentException.class, LogLevel.WARN, id)
        }

        final Optional<PrivilegeGroup> defaultGroupPrivilege = privilegeGroupRepository.findDefaultPrivilegeGroup()
        final boolean hasDtoDefaultPrivilegeGroupProperty = privilegeGroupResource.getIsDefaultGroup()
        if (defaultGroupPrivilege.isPresent() && hasDtoDefaultPrivilegeGroupProperty) {

            if (privilegeGroupResource.getGroupName() != defaultGroupPrivilege.get().getGroupName()) {
                alertService.logAndThrowException(AlertCode.B303, IllegalArgumentException.class, LogLevel.WARN)
            }
        }

        final String groupName = privilegeGroupResource.getGroupName()
        final Optional<PrivilegeGroup> foundPrivilegeGroupByName = privilegeGroupRepository.findByGroupName(groupName)
        if (foundPrivilegeGroupByName.isPresent()) {

            if (id != foundPrivilegeGroupByName.get().getId()) {
                alertService.logAndThrowException(AlertCode.B302, IllegalArgumentException.class, LogLevel.WARN, groupName)
            }
        }

        final PrivilegeGroup privilegeGroup = foundPrivilegeGroupById.get()
        privilegeGroup.setGroupName(privilegeGroupResource.getGroupName())
        privilegeGroup.setIsDefaultGroup(privilegeGroupResource.getIsDefaultGroup())
        privilegeGroup.setPrivileges(privilegeGroupResource.getPrivileges())

        privilegeGroupRepository.save(privilegeGroup)
    }
}
