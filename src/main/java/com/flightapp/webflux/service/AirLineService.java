package com.flightapp.webflux.service;

import org.springframework.http.ResponseEntity;

import com.flightapp.webflux.entity.Airline;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AirLineService {
	Flux<Airline> getAllAirlines();

	Mono<ResponseEntity<Void>> addAirline(Airline airline);
}
