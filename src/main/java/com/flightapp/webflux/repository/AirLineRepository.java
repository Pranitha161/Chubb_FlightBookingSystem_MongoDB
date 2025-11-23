package com.flightapp.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.webflux.entity.Airline;

import reactor.core.publisher.Mono;

public interface AirLineRepository extends ReactiveMongoRepository<Airline, String> {
	Mono<Airline> findByName(String name);
}
