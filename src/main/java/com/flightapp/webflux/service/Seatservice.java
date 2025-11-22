package com.flightapp.webflux.service;

import com.flightapp.webflux.entity.Seat;

import reactor.core.publisher.Flux;

public interface Seatservice {
	
	Flux<Seat> initialiszeSeats(String flightId, int rows, int cols);
}
