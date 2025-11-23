package com.flightapp.webflux.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.SearchRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightService {
	Mono<ResponseEntity<List<Flight>>> search(SearchRequest searchRequest);

	Mono<ResponseEntity<Void>> addFlight(Flight flight);

	Flux<Flight> getFlights();

	Mono<ResponseEntity<Flight>> getFlightById(String flightId);

}
