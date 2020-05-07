package com.cdvtech.talibanairlines.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "planes")
class Plane {

    @Id
    String id

    String name

    @DBRef
    List<Trip> trips

}
