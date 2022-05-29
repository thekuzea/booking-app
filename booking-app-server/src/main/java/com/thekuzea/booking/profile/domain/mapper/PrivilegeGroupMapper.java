package com.thekuzea.booking.profile.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thekuzea.booking.api.dto.PrivilegeGroupResource;
import com.thekuzea.booking.profile.domain.model.PrivilegeGroup;

@Mapper
public interface PrivilegeGroupMapper {

    @Mapping(source = "isDefaultGroup", target = "defaultGroup")
    PrivilegeGroup resourceToModel(PrivilegeGroupResource resource);

    @Mapping(source = "defaultGroup", target = "isDefaultGroup")
    PrivilegeGroupResource modelToResource(PrivilegeGroup model);
}
