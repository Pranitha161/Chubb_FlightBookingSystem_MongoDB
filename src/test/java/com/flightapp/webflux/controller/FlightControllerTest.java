package com.flightapp.webflux.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.SearchRequest;
import com.flightapp.webflux.service.FlightService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(FlightController.class)
class FlightControllerTest {
	@Autowired
	WebTestClient webtestClient;
	@MockBean FlightService flightService;
	
	@Test
	void testsearchFlightSuccessfully() {
		SearchRequest searchRequest=new SearchRequest("Hyderabad","Mumbai",LocalDate.of(2025, 11, 23));
		when(flightService.search(any(SearchRequest.class))).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
		webtestClient.post().uri("/api/v1.0/flight/search").bodyValue(searchRequest).exchange().expectStatus().isOk();
	}
	@Test
	void testsearchFlightFailure() {
		SearchRequest searchRequest=new SearchRequest();
		when(flightService.search(any(SearchRequest.class))).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build()));
		webtestClient.post().uri("/api/v1.0/flight/search").bodyValue(searchRequest).exchange().expectStatus().is4xxClientError();
	}
	@Test
	void testGetFlightbyIdSuccessfully() {
		when(flightService.getFlightById("123")).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
		webtestClient.get().uri("/api/v1.0/flight/get/{flightId}",123).exchange().expectStatus().isOk();
	}
	@Test
	void testGetFlightbyIdFailure() {
		when(flightService.getFlightById("123")).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
		webtestClient.get().uri("/api/v1.0/flight/get/{flightId}",123).exchange().expectStatus().is4xxClientError();
	}
	@Test
	void testGetAllFlightsSuccessfully() {
		Flight flight=new Flight();
		flight.setAirlineId("20");
		flight.setId("1");
		flight.setFromPlace("Hyderabad");
		flight.setToPlace("Mumbai");
		when(flightService.getFlights()).thenReturn(Flux.just(flight));
		webtestClient.get().uri("/api/v1.0/flight/get/flights").exchange().expectStatus().isOk().expectBodyList(Flight.class).contains(flight);
		
	}
	@Test
	void testGetAllFightsEmpty() {
		when(flightService.getFlights()).thenReturn(Flux.empty());
		webtestClient.get().uri("/api/v1.0/flight/get/flights").exchange().expectStatus().isOk().expectBodyList(Flight.class).hasSize(0);
	}
}
