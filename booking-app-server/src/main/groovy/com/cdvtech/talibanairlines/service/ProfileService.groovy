package com.cdvtech.talibanairlines.service

import com.cdvtech.talibanairlines.model.entity.Profile

interface ProfileService {

    Profile getByUsername(String username)

    List<Profile> getAll()

    Profile save(Profile profile)

    Profile update(String username, Profile profile)

    Profile delete (String username)

}
