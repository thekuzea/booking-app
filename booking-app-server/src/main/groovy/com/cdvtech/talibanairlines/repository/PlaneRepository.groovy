package com.cdvtech.talibanairlines.repository

import com.cdvtech.talibanairlines.model.entity.Plane
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaneRepository extends MongoRepository<Plane, String> {

    Plane findByName(String name)

}
