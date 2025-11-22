package com.flightapp.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.webflux.entity.Seat;

import reactor.core.publisher.Flux;

public interface SeatRepository extends ReactiveMongoRepository<Seat, String> {
	Flux<Seat> findByFlightId(String id);
}
