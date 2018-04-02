package com.cdvtech.talibanairlines.service.impl

import com.cdvtech.talibanairlines.model.entity.Profile
import com.cdvtech.talibanairlines.repository.ProfileRepository
import com.cdvtech.talibanairlines.service.ProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder

    @Override
    Profile getByUsername(String username) {
        return profileRepository.findById(username).get()
    }

    @Override
    List<Profile> getAll() {
        return profileRepository.findAll()
    }

    @Override
    Profile save(Profile profile) {
        Optional<Profile> tempProfile = profileRepository.findById(profile.username)

        if (!tempProfile.isPresent()) {
            profile.password = bCryptPasswordEncoder.encode(profile.password)
            profile.role = "ROLE_USER"

            profileRepository.save(profile)

            return profile
        }

        return null
    }

    @Override
    Profile update(String username, Profile profile) {
        Profile currentProfile

        try {
            currentProfile = profileRepository.findById(username).get()
        } catch (NoSuchElementException ignored) {
            return null
        }

        if(currentProfile != null) {
            if(profile.firstName != null) {
                currentProfile.firstName = profile.firstName
            }
            if (profile.lastName != null) {
                currentProfile.lastName = profile.lastName
            }
            if (profile.password != null) {
                currentProfile.password = bCryptPasswordEncoder.encode(profile.password)
            }
            if (profile.phoneNumber != null) {
                currentProfile.phoneNumber = profile.phoneNumber
            }

            profileRepository.save(currentProfile)
            return currentProfile
        }

        return null
    }

    @Override
    Profile delete(String username) {
        Optional<Profile> profile = profileRepository.findById(username)

        if (profile.isPresent()) {
            profileRepository.delete(profile.get())

            return profile.get()
        }

        return null
    }
}
