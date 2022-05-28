package com.thekuzea.booking.flight.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "flights")
public class Flight {

    @Id
    private String id;

    private String tripId;

    private Seat[][] seats;
}
