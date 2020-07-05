package com.cdvtech.talibanairlines.repository

import com.cdvtech.talibanairlines.model.entity.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository extends MongoRepository<Profile, String> {

}
