package com.airlineTickets.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public class Flight {


    private final UUID flightId;
    private FlightDestination flightOrigin;
    private FlightDestination flightArrival;
    private Date flightDate;
    private int numOfLayovers;
    private int numOfAvailableSeats;
    private int numOfTotalSeats;
    private FlightStatus flightStatus;

    public UUID getFlightId() {
        return flightId;
    }
    public FlightDestination getFlightOrigin() {
        return flightOrigin;
    }

    public void setFlightOrigin(FlightDestination flightOrigin) {
        this.flightOrigin = flightOrigin;
    }

    public FlightDestination getFlightArrival() {
        return flightArrival;
    }

    public void setFlightArrival(FlightDestination flightArrival) {
        this.flightArrival = flightArrival;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public int getNumOfLayovers() {
        return numOfLayovers;
    }

    public void setNumOfLayovers(int numOfLayovers) {
        this.numOfLayovers = numOfLayovers;
    }

    public int getNumOfAvailableSeats() {
        return numOfAvailableSeats;
    }

    public void setNumOfAvailableSeats(int numOfAvailableSeats) {
        this.numOfAvailableSeats = numOfAvailableSeats;
    }

    public int getNumOfTotalSeats() {
        return numOfTotalSeats;
    }

    public void setNumOfTotalSeats(int numOfTotalSeats) {
        this.numOfTotalSeats = numOfTotalSeats;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Flight(@JsonProperty("flightId")            UUID              flightId,
                  @JsonProperty("flightOrigin")        FlightDestination flightOrigin,
                  @JsonProperty("flightDestination")   FlightDestination flightArrival,
                  @JsonProperty("flightDate")          Date              flightDate,
                  @JsonProperty("numOfLayovers")       int               numOfLayovers,
                  @JsonProperty("numOfAvailableSeats") int               numOfAvailableSeats,
                  @JsonProperty("numOfTotalSeats")     int               numOfTotalSeats,
                  @JsonProperty("flightStatus")        FlightStatus      flightStatus)
    {
        this.flightId = flightId;
        this.flightOrigin = flightOrigin;
        this.flightArrival = flightArrival;
        this.flightDate = flightDate;
        this.numOfLayovers = numOfLayovers;
        this.numOfAvailableSeats = numOfAvailableSeats;
        this.numOfTotalSeats = numOfTotalSeats;
        this.flightStatus = flightStatus;
    }
}
