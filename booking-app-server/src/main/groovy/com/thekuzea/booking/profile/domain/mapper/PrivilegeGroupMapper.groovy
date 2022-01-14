package com.thekuzea.booking.profile.domain.mapper

import com.thekuzea.booking.api.dto.PrivilegeGroupResource
import com.thekuzea.booking.profile.domain.model.PrivilegeGroup

class PrivilegeGroupMapper {

    static PrivilegeGroup resourceToModel(final PrivilegeGroupResource resource) {
        final PrivilegeGroup model = new PrivilegeGroup()
        model.setGroupName(resource.getGroupName())
        model.setPrivileges(List.copyOf(resource.getPrivileges()))
        model.setIsDefaultGroup(resource.getIsDefaultGroup() == null ? false : resource.getIsDefaultGroup())

        model
    }

    static PrivilegeGroupResource modelToResource(final PrivilegeGroup model) {
        new PrivilegeGroupResource()
                .id(model.getId())
                .groupName(model.getGroupName())
                .privileges(List.copyOf(model.getPrivileges()))
                .isDefaultGroup(model.getIsDefaultGroup())
    }
}
