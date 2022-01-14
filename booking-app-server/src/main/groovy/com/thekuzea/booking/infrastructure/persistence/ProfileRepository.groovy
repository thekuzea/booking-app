package com.thekuzea.booking.infrastructure.persistence

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.RepositoryDefinition

import com.thekuzea.booking.profile.domain.model.Profile

@RepositoryDefinition(domainClass = Profile.class, idClass = String.class)
interface ProfileRepository {

    Optional<Profile> findByUsername(String username)

    List<Profile> findAllById(List<String> ids)

    Page<Profile> findAll(Pageable pageable)

    boolean existsByUsername(String username)

    void save(Profile profile)
}
