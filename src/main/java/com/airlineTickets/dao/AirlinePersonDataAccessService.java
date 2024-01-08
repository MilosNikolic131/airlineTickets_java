package com.airlineTickets.dao;

import com.airlineTickets.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class AirlinePersonDataAccessService implements AirlineDAO{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AirlinePersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertFlight(UUID id, Flight flight) {
        String sql = "INSERT INTO flight (flightId, flightOrigin, flightDestination, flightStatus, flightDate, numOfLayovers, numOfAvailableSeats , numOfTotalSeats) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                new Object[]{id, flight.getFlightOrigin().name(),  flight.getFlightArrival().name(),       flight.getFlightStatus().name(),
                             flight.getFlightDate(), flight.getNumOfLayovers(), flight.getNumOfAvailableSeats(), flight.getNumOfTotalSeats()});
    }

    @Override
    public List<Flight> getAllFlights() {
        String sql = "Select flightId, flightOrigin, flightDestination, flightStatus, flightDate, numOfLayovers, numOfAvailableSeats, numOfTotalSeats FROM flight";
        List<Flight> list = jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID flightId = UUID.fromString(resultSet.getString(1));
            FlightDestination flightOrigin = FlightDestination.valueOf(resultSet.getString(2));
            FlightDestination flightDestination = FlightDestination.valueOf(resultSet.getString(3));
            FlightStatus flightStatus = FlightStatus.valueOf(resultSet.getString(4));
            Date flightDate = resultSet.getDate(5);
            int numOfLayovers = resultSet.getInt(6);
            int numOfAvailableSeats = resultSet.getInt(7);
            int numOfTotalSeats = resultSet.getInt(8);

            return new Flight(flightId, flightOrigin, flightDestination, flightDate, numOfLayovers, numOfAvailableSeats, numOfTotalSeats, flightStatus);
        });

        return list;
    }

    @Override
    public List<Flight> getFlightsByDestination(FlightDestination from, FlightDestination to) {
        String sql = "Select flightId, flightOrigin, flightDestination, flightStatus, flightDate, numOfLayovers, numOfAvailableSeats, numOfTotalSeats FROM flight WHERE flightOrigin = '" + from.toString()
                + "' AND flightDestination = '" + to.toString() + "' AND numOfAvailableSeats > 0";
        List<Flight> list = jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID flightId = UUID.fromString(resultSet.getString(1));
            FlightDestination flightOrigin = FlightDestination.valueOf(resultSet.getString(2));
            FlightDestination flightDestination = FlightDestination.valueOf(resultSet.getString(3));
            FlightStatus flightStatus = FlightStatus.valueOf(resultSet.getString(4));
            Date flightDate = resultSet.getDate(5);
            int numOfLayovers = resultSet.getInt(6);
            int numOfAvailableSeats = resultSet.getInt(7);
            int numOfTotalSeats = resultSet.getInt(8);

            return new Flight(flightId, flightOrigin, flightDestination, flightDate, numOfLayovers, numOfAvailableSeats, numOfTotalSeats, flightStatus);
        });

        return list;
    }

    @Override
    public int insertUser(UUID id, User user) {
        String sql = "INSERT INTO user_table (userid, name, type, password) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                new Object[]{id, user.getName(), user.getType().toString(), user.getPassword()});
    }

    @Override
    public int cancelFlight(UUID flightId) {
        String sql = "UPDATE flight SET flighStatus = 'Cancelled' where flightid = '?'";
        return jdbcTemplate.update(sql,
                new Object[]{flightId});
    }

    @Override
    public void approveResrevation(UUID flightId, UUID userId) {
        String sql = "UPDATE reservation set approved = 1 WHERE flightId = '?' AND userId = '?'";
        jdbcTemplate.update(sql,
                new Object[]{flightId, userId});
    }

    @Override
    public int reserveFlight(UUID userId, UUID flightId, int numOfSeats) {
        Flight flight = this.getFlightById(flightId);
        Date date = new Date();
//        long diffInMillies = Math.abs(date.getTime() - flight.getFlightDate().getTime());
        long diff = ChronoUnit.DAYS.between(LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault()), LocalDate.ofInstant(flight.getFlightDate().toInstant(), ZoneId.systemDefault()));
        if (diff < 3) {
            return 1;
        }
        String sql = "INSERT INTO reservation (userid, flightid, numofreservedseats, approved) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                new Object[]{userId, flightId, numOfSeats, 0});
    }

    @Override
    public List<Reservation> getReservationsById(UUID userId) {
        String sql = "Select userid, flightid, numOfReservedSeats, approved FROM reservation WHERE userid = '" + userId + "'";
        List<Reservation> list = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Reservation(UUID.fromString(resultSet.getString(1)), UUID.fromString(resultSet.getString(2)), resultSet.getInt(3), resultSet.getInt(4) == 1);
        });

        return list;
    }

    private Flight getFlightById(UUID flightId)
    {
        String sql = "Select flightId, flightOrigin, flightDestination, flightStatus, flightDate, numOfLayovers, numOfAvailableSeats, numOfTotalSeats FROM flight WHERE flightId = '"
                + flightId + "'";
        List<Flight> list = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Flight(UUID.fromString(resultSet.getString(1)),
                              FlightDestination.valueOf(resultSet.getString(2)),
                              FlightDestination.valueOf(resultSet.getString(3)),
                              resultSet.getDate(5),
                              resultSet.getInt(6),
                              resultSet.getInt(7),
                              resultSet.getInt(8),
                              FlightStatus.valueOf(resultSet.getString(4)));
        });
        return list.get(0);
    }

    @Override
    public Optional<User> getUserByName(String name)
    {
        String sql = "Select userid, name, type, password FROM user_table WHERE name = '"
                + name + "'";
        List<User> list = jdbcTemplate.query(sql, (resultSet, i) -> {
            return new User(UUID.fromString(resultSet.getString(1)),
                    resultSet.getString(2),
                    UserType.valueOf(resultSet.getString(3)),
                    resultSet.getString(4));

        });
        return Optional.ofNullable(list.get(0));
    }
}
