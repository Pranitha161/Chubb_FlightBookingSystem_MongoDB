package com.flightapp.webflux.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.flightapp.webflux.entity.Airline;
import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.Price;
import com.flightapp.webflux.service.AirLineService;
import com.flightapp.webflux.service.FlightService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(AirLineController.class)
class AirLineControllerTest {
	@Autowired
	WebTestClient webtestClient;
	@MockBean
	FlightService flightService;
	@MockBean
	AirLineService airlineService;

	@Test
	void testAddInventorySuccessfully() {
		Flight flight = new Flight();
		flight.setId("1");
		flight.setAirlineId("20");
		flight.setArrivalTime(LocalDateTime.of(2025, 11, 23, 11, 30));
		flight.setArrivalTime(LocalDateTime.of(2025, 11, 23, 12, 30));
		flight.setFromPlace("Hyderabad");
		flight.setToPlace("Mumbai");
		Price price = new Price();
		price.setOneWay(2000);
		price.setRoundTrip(1000);
		flight.setPrice(price);
		Airline airline = new Airline();
		airline.setId("20");
		airline.setName("Indigo");
		airline.setLogoUrl("http://logourl.com");
		airline.setFlights(List.of(flight));
		when(flightService.addFlight(any(Flight.class)))
				.thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
		webtestClient.post().uri("/api/v1.0/flight/airline/inventory/add").bodyValue(flight).exchange().expectStatus()
				.isCreated();
	}

	@Test
	void testAddInventoryFailure() {
		Flight emptyFlight = new Flight();
		when(flightService.addFlight(any(Flight.class)))
				.thenReturn(Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
		webtestClient.post().uri("/api/v1.0/flight/airline/inventory/add").bodyValue(emptyFlight).exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	void testAirlines() {
		Airline airline = new Airline("1", "Indigo", "http://logo.com", Collections.emptyList());
		when(airlineService.getAllAirlines()).thenReturn(Flux.just(airline));
		webtestClient.get().uri("/api/v1.0/flight/airline/get").exchange().expectStatus().isOk()
				.expectBodyList(Airline.class).contains(airline);
	}

	@Test
	void testAirlineEmpty() {
		when(airlineService.getAllAirlines()).thenReturn(Flux.empty());
		webtestClient.get().uri("/api/v1.0/flight/airline/get").exchange().expectStatus().isOk()
				.expectBodyList(Airline.class).hasSize(0);
	}

	@Test
	void testAddAirlineSuccessfull() {
		Airline airline = new Airline("1", "Indigo", "http://logo.com", Collections.emptyList());
		when(airlineService.addAirline(any(Airline.class)))
				.thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
		webtestClient.post().uri("/api/v1.0/flight/airline/add").bodyValue(airline).exchange().expectStatus()
				.isCreated();
	}

	@Test
	void testAddAirlineFailure() {
		Airline airline = new Airline();
		when(airlineService.addAirline(any(Airline.class)))
				.thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build()));
		webtestClient.post().uri("/api/v1.0/flight/airline/add").bodyValue(airline).exchange().expectStatus()
				.is4xxClientError();
	}

}
