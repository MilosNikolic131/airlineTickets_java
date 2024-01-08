package com.airlineTickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AirlineTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineTicketsApplication.class, args);
	}

}
