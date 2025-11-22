package com.flightapp.webflux.service;

import org.springframework.http.ResponseEntity;

import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.SearchRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightService {
	Flux<ResponseEntity<Flight>> search(SearchRequest searchRequest);

	Mono<ResponseEntity<Flight>> addFlight(Flight flight);

}
