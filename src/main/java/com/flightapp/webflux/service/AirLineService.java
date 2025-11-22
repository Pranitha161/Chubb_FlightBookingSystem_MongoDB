package com.flightapp.webflux.service;

import com.flightapp.webflux.entity.Airline;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AirLineService {
	Flux<Airline> gerAllAirlines();

	Mono<Airline> addAirline(Airline airline);
}
