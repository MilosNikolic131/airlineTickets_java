CREATE TABLE flight (
    flightId UUID NOT NULL PRIMARY KEY,
    flightOrigin VARCHAR(100) NOT NULL,
    flightDestination VARCHAR(100) NOT NULL,
    flightStatus VARCHAR(100) NOT NULL,
    flightDate date NOT NULL,
    numOfLayovers int,
    numOfAvailableSeats int,
    numOfTotalSeats int NOT NULL
);

CREATE TABLE user_table (
    userId UUID NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE reservation (
    userId UUID NOT NULL,
    flightId UUID NOT NULL,
    numOfReservedSeats int,
    approved BIT,
    PRIMARY KEY (userId, flightId),
    FOREIGN KEY (userId) REFERENCES user_table(userId),
    FOREIGN KEY (flightId) REFERENCES flight(flightId)
);

