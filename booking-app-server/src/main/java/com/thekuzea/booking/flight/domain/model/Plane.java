package com.thekuzea.booking.flight.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.thekuzea.booking.api.dto.TechnicalStatus;

@Data
@Document(collection = "planes")
public class Plane {

    @Id
    private String id;

    private String name;

    private String planeNumber;

    private String originCountryCode;

    private TechnicalStatus technicalStatus;

    private int seatsAmount;

    private int columnsAmount;

    private int rowsAmount;
}
