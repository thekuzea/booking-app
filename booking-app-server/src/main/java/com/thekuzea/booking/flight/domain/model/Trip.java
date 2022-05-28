package com.thekuzea.booking.flight.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.thekuzea.booking.api.dto.TripClassType;
import com.thekuzea.booking.api.dto.TripStatus;

@Data
@Document(collection = "trips")
public class Trip {

    @Id
    private String id;

    private TripClassType tripClassType;

    private TripStatus tripStatus;

    private String countryCodeOfDeparture;

    private String cityOfDeparture;

    private OffsetDateTime departureDateTime;

    private String countryCodeOfArrival;

    private String cityOfArrival;

    private OffsetDateTime dateTimeOfArrival;

    private String planeId;

    private List<String> passengerIds;
}
