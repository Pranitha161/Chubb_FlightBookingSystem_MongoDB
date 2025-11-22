package com.flightapp.webflux.service;

import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.SearchRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FlightService {
	Flux<Flight> serach(SearchRequest searchRequest);

	Mono<Flight> addFlight(Flight flight);

}
