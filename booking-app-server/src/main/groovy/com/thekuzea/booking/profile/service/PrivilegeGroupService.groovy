package com.thekuzea.booking.profile.service

import com.thekuzea.booking.api.dto.PrivilegeGroupPageResource
import com.thekuzea.booking.api.dto.PrivilegeGroupResource

interface PrivilegeGroupService {

    String getDefaultPrivilegeGroupId()

    List<String> getPrivilegesById(String id)

    PrivilegeGroupResource getPrivilegeGroupById(String id)

    PrivilegeGroupPageResource getAllProfilesByParameters(int size, int page)

    void saveNewPrivilegeGroup(PrivilegeGroupResource privilegeGroupResource)

    void updatePrivilegeGroupById(String id, PrivilegeGroupResource privilegeGroupResource)
}
