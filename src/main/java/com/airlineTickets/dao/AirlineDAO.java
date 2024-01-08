package com.airlineTickets.dao;

import com.airlineTickets.model.Flight;
import com.airlineTickets.model.FlightDestination;
import com.airlineTickets.model.Reservation;
import com.airlineTickets.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirlineDAO {
    int insertFlight(UUID id, Flight flight);

    default  int insertFlight(Flight flight)
    {
        return insertFlight(UUID.randomUUID(), flight);
    }

    List<Flight> getAllFlights();

    List<Flight> getFlightsByDestination(FlightDestination from, FlightDestination to);

    int insertUser(UUID id, User user);

    default int insertUser(User user){
        return insertUser(UUID.randomUUID(), user);
    }

    int cancelFlight(UUID flightId);

    void approveResrevation(UUID flightId, UUID userId);

    int reserveFlight(UUID userId, UUID flightId, int numOfSeats);

    List<Reservation> getReservationsById(UUID userId);

    Optional<User> getUserByName(String name);
}
