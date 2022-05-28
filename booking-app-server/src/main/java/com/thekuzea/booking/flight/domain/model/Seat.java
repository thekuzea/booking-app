package com.thekuzea.booking.flight.domain.model;

import lombok.Data;

@Data
public class Seat {

    private char column;

    private int row;

    private boolean isAvailable;

    private String passengerId;
}
