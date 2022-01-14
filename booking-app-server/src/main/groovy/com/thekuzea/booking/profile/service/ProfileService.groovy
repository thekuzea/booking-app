package com.thekuzea.booking.profile.service

import com.thekuzea.booking.api.dto.ProfilePageResource
import com.thekuzea.booking.api.dto.ProfileResource
import com.thekuzea.booking.api.dto.RegisterProfileResource

interface ProfileService {

    ProfileResource getProfileByUsername(String id, boolean isDetailed)

    List<ProfileResource> getProfilesByIds(List<String> profileIds)

    ProfilePageResource getAllProfilesByParameters(int size, int page)

    void registerNewProfile(RegisterProfileResource profileResource)

    void updateProfileByUsername(String id, ProfileResource profileResource)
}
