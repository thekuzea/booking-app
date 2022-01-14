package com.thekuzea.booking.support.util

import com.thekuzea.booking.flight.domain.model.Seat

final class SeatUtil {

    private SeatUtil() {
    }

    static Seat[][] createSeatList(final int rowsAmount, final int columnsAmount) {
        final int startingColumnCode = 65
        final Seat[][] seats = new Seat[rowsAmount][columnsAmount]

        for (int i = 0; i < rowsAmount; i++) {
            for (int j = 0; j < columnsAmount; j++) {
                final Seat seat = new Seat()
                seat.setRow(i + 1)
                seat.setColumn((startingColumnCode + j) as char)
                seat.setIsAvailable(true)

                seats[i][j] = seat
            }
        }

        seats
    }

    static Seat resolveSeat(final Seat[][] seats, final int row, final String column) {
        final int startingColumnCode = 65
        final int intColumn = column.getChars()[0]

        final int resolvedColumn = intColumn % startingColumnCode
        seats[row - 1][resolvedColumn]
    }
}
