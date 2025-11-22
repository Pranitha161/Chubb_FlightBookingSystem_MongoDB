package com.flightapp.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.webflux.entity.Flight;

import reactor.core.publisher.Flux;

public interface FlightRepository extends ReactiveMongoRepository<Flight, String> {
	Flux<Flight> getFightByFromPlaceAndToPlace(String fromPlace, String toPlace);
}
