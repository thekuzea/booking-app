package com.thekuzea.booking.profile.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.thekuzea.booking.api.PrivilegeGroupApi;
import com.thekuzea.booking.api.dto.PrivilegeGroupPageResource;
import com.thekuzea.booking.api.dto.PrivilegeGroupResource;
import com.thekuzea.booking.profile.service.PrivilegeGroupService;

@RestController
@RequiredArgsConstructor
public class PrivilegeGroupController implements PrivilegeGroupApi {

    private final PrivilegeGroupService privilegeGroupService;

    @Override
    public ResponseEntity<PrivilegeGroupPageResource> getAllPrivilegeGroupsByParameters(final Integer size, final Integer page) {
        return ResponseEntity.ok(privilegeGroupService.getAllProfilesByParameters(size, page));
    }

    @Override
    public ResponseEntity<Void> saveNewPrivilegeGroup(final PrivilegeGroupResource privilegeGroupResource) {
        privilegeGroupService.saveNewPrivilegeGroup(privilegeGroupResource);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> updatePrivilegeGroupById(final String id, final PrivilegeGroupResource privilegeGroupResource) {
        privilegeGroupService.updatePrivilegeGroupById(id, privilegeGroupResource);
        return ResponseEntity.accepted().build();
    }
}
