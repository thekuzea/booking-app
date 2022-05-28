package com.thekuzea.booking.profile.domain.mapper;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.thekuzea.booking.api.dto.PrivilegeGroupResource;
import com.thekuzea.booking.profile.domain.model.PrivilegeGroup;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PrivilegeGroupMapper {

    public static PrivilegeGroup resourceToModel(final PrivilegeGroupResource resource) {
        final PrivilegeGroup model = new PrivilegeGroup();
        model.setGroupName(resource.getGroupName());
        model.setPrivileges(List.copyOf(resource.getPrivileges()));
        model.setDefaultGroup(resource.getIsDefaultGroup() != null && resource.getIsDefaultGroup());

        return model;
    }

    public static PrivilegeGroupResource modelToResource(final PrivilegeGroup model) {
        return new PrivilegeGroupResource()
                .id(model.getId())
                .groupName(model.getGroupName())
                .privileges(List.copyOf(model.getPrivileges()))
                .isDefaultGroup(model.isDefaultGroup());
    }
}
