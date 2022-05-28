package com.thekuzea.booking.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import com.thekuzea.booking.profile.domain.model.PrivilegeGroup;

@RepositoryDefinition(domainClass = PrivilegeGroup.class, idClass = String.class)
public interface PrivilegeGroupRepository {

    Optional<PrivilegeGroup> findById(String id);

    @Query(value = "{ isDefaultGroup: true }", fields = "{ id: 1 }")
    Optional<PrivilegeGroup> findDefaultPrivilegeGroup();

    Optional<PrivilegeGroup> findByGroupName(String groupName);

    boolean existsByGroupName(String groupName);

    @Query(value = "{ isDefaultGroup: true }", exists = true)
    boolean existsDefaultPrivilegeGroup();

    Page<PrivilegeGroup> findAll(Pageable pageable);

    void save(PrivilegeGroup privilegeGroup);
}
