package com.flightapp.webflux.service;

import com.flightapp.webflux.entity.Passenger;

import reactor.core.publisher.Mono;

public interface PassengerService {
	Mono<Passenger> getPassengerById(String passengerId);

	Mono<Passenger> getPassengerByEmail(String email);

	Mono<Passenger> savePassenger(Passenger passenger);

	Mono<Passenger> updateById(String id, Passenger passenger);

	Mono<Void> deleteById(String passengerId);

}
