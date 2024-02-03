package com.airlineTickets.api;

import com.airlineTickets.model.Flight;
import com.airlineTickets.model.FlightDestination;
import com.airlineTickets.model.Reservation;
import com.airlineTickets.model.User;
import com.airlineTickets.service.AirlineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/airline")
@RestController
@CrossOrigin
public class AirlineController {
    private final AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping(path = "flight")
    public void addFlight(@Valid @NonNull @RequestBody Flight flight) {
        airlineService.addFlight(flight);
    }

    @PostMapping(path = "user")
    public void addUser(@Valid @NonNull @RequestBody User user) {
        airlineService.addUser(user);
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return airlineService.getAllFlights();
    }

    @GetMapping(path = "{from}-{to}")
    public List<Flight> getFlightsByDestination(@PathVariable("from") FlightDestination from, @PathVariable("to") FlightDestination to) {
        return airlineService.getFlightsByDestination(from, to);
    }

    @PutMapping("agent/flightId:{flightId}-userId:{userId}")
    public void approveReservation(@PathVariable("flightId") UUID flightId, @PathVariable("userId") UUID userId) {
        airlineService.approveReservation(flightId, userId);
    }

    @PostMapping("reservation")
    public int reserveFlight(UUID userId, UUID flightId, int numOfSeats) {
        return airlineService.reserveFlight(userId, flightId, numOfSeats);
    }

    @PutMapping("flight")
    public int cancelFlight(UUID flightId) {
        return airlineService.cancelFlight(flightId);
    }

    @GetMapping("reservation/{id}")
    public List<Reservation> getReservationsById(@PathVariable("id") UUID userId)
    {
        return airlineService.getReservationsById(userId);
    }
}
