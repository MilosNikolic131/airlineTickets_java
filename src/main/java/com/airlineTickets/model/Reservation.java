package com.airlineTickets.model;

import java.util.UUID;

public class Reservation {
    private final UUID userId;
    private final UUID flightId;
    private int numOfReservedSeats;
    private boolean approved;

    public Reservation(UUID userId, UUID flightId, int numOfReservedSeats, boolean approved) {
        this.userId = userId;
        this.flightId = flightId;
        this.numOfReservedSeats = numOfReservedSeats;
        this.approved = approved;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getFlightId() {
        return flightId;
    }

    public int getNumOfReservedSeats() {
        return numOfReservedSeats;
    }

    public void setNumOfReservedSeats(int numOfReservedSeats) {
        this.numOfReservedSeats = numOfReservedSeats;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
