package com.airlineTickets.service;

import com.airlineTickets.dao.AirlineDAO;
import com.airlineTickets.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AirlineService {
    private final AirlineDAO airlineDAO;

    @Autowired
    public AirlineService(@Qualifier("postgres") AirlineDAO airlineDAO)
    {
        this.airlineDAO = airlineDAO;
    }

    public int addFlight(Flight flight)
    {
        boolean hasRole = AirlineService.checkRoles(List.of(UserType.Agent, UserType.Admin));
        if (hasRole){
            return airlineDAO.insertFlight(flight);
        }

        return -1;
    }

    public int addUser(User user)
    {
        boolean hasRole = AirlineService.checkRoles(List.of(UserType.Admin));
        if (hasRole){
            return airlineDAO.insertUser(user);
        }

        return -1;
    }

    public List<Flight> getAllFlights()
    {
        boolean hasRole = AirlineService.checkRoles(List.of(UserType.Admin, UserType.Visitor, UserType.Agent));
        if (hasRole){
            return airlineDAO.getAllFlights();
        }

        return null;
    }

    public List<Flight> getFlightsByDestination(FlightDestination from, FlightDestination to)
    {
        boolean hasRole = AirlineService.checkRoles(List.of(UserType.Visitor, UserType.Admin));
        if (hasRole){
            return airlineDAO.getFlightsByDestination(from, to);
        }

        return null;
    }

    public void approveReservation(UUID flightId, UUID userId) {
        boolean hasRole = AirlineService.checkRoles(List.of(UserType.Agent, UserType.Admin));
        if (hasRole){
            airlineDAO.approveResrevation(flightId, userId);
        }
    }

    public int reserveFlight(UUID userId, UUID flightId, int numOfSeats) {
        boolean hasRole = AirlineService.checkRoles(List.of(UserType.Visitor, UserType.Admin));
        if (hasRole){
            return airlineDAO.reserveFlight(userId, flightId, numOfSeats);
        }

        return -1;
    }

    public int cancelFlight(UUID flightId) {
        boolean hasRole = AirlineService.checkRoles(List.of(UserType.Admin));
        if (hasRole){
            return airlineDAO.cancelFlight(flightId);
        }

        return -1;
    }

    public List<Reservation> getReservationsById(UUID userId) {
        boolean hasRole = AirlineService.checkRoles(List.of(UserType.Visitor, UserType.Admin));
        if (hasRole){
            return airlineDAO.getReservationsById(userId);
        }

        return null;
    }

    public Optional<User> getUserByName(String name) {
        return airlineDAO.getUserByName(name);
    }

    public static boolean checkRoles(List<UserType> types)
    {
        boolean hasRole = false;
        for (UserType userType: types) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Set<String> roles = auth.getAuthorities().stream()
                    .map(r -> r.getAuthority()).collect(Collectors.toSet());
            hasRole = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals(userType.toString()));
            if (hasRole)
            {
                return hasRole;
            }
        }

        return hasRole;
    }
}
