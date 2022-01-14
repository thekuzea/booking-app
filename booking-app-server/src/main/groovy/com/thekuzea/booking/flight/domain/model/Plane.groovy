package com.thekuzea.booking.flight.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import com.thekuzea.booking.api.dto.TechnicalStatus

@Document(collection = "planes")
class Plane {

    @Id
    String id

    String name

    String planeNumber

    String originCountryCode

    TechnicalStatus technicalStatus

    int seatsAmount

    int columnsAmount

    int rowsAmount
}
