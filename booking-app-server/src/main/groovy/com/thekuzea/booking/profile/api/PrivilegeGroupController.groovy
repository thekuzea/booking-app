package com.thekuzea.booking.profile.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import com.thekuzea.booking.api.PrivilegeGroupApi
import com.thekuzea.booking.api.dto.PrivilegeGroupPageResource
import com.thekuzea.booking.api.dto.PrivilegeGroupResource
import com.thekuzea.booking.profile.service.PrivilegeGroupService

@RestController
class PrivilegeGroupController implements PrivilegeGroupApi {

    @Autowired
    PrivilegeGroupService privilegeGroupService

    @Override
    ResponseEntity<PrivilegeGroupPageResource> getAllPrivilegeGroupsByParameters(final Integer size, final Integer page) {
        ResponseEntity.ok(privilegeGroupService.getAllProfilesByParameters(size, page))
    }

    @Override
    ResponseEntity<Void> saveNewPrivilegeGroup(final PrivilegeGroupResource privilegeGroupResource) {
        privilegeGroupService.saveNewPrivilegeGroup(privilegeGroupResource)
        ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @Override
    ResponseEntity<Void> updatePrivilegeGroupById(final String id, final PrivilegeGroupResource privilegeGroupResource) {
        privilegeGroupService.updatePrivilegeGroupById(id, privilegeGroupResource)
        ResponseEntity.accepted().build()
    }
}
