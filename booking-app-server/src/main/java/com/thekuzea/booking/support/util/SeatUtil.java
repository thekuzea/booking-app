package com.thekuzea.booking.support.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.thekuzea.booking.flight.domain.model.Seat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SeatUtil {

    public static Seat[][] createSeatList(final int rowsAmount, final int columnsAmount) {
        final int startingColumnCode = 65;
        final Seat[][] seats = new Seat[rowsAmount][columnsAmount];

        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < columnsAmount; j++) {
                final Seat seat = new Seat();
                seat.setRow(i + 1);
                seat.setColumn((char) (startingColumnCode + j));
                seat.setAvailable(true);

                seats[i][j] = seat;
            }
        }

        return seats;
    }

    public static Seat resolveSeat(final Seat[][] seats, final int row, final String column) {
        final int startingColumnCode = 65;
        final int intColumn = column.charAt(0);

        final int resolvedColumn = intColumn % startingColumnCode;
        return seats[row - 1][resolvedColumn];
    }
}
