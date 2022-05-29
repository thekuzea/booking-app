package com.thekuzea.booking.profile.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thekuzea.booking.api.dto.ProfileResource;
import com.thekuzea.booking.api.dto.RegisterProfileResource;
import com.thekuzea.booking.profile.domain.model.Profile;

@Mapper
public interface ProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "countryCode", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "privilegeGroupId", ignore = true)
    Profile registerResourceToModel(RegisterProfileResource resource);

    @Mapping(target = "privilegeGroup", ignore = true)
    ProfileResource modelToResource(Profile model);
}
