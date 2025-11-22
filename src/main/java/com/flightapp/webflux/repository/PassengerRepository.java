package com.flightapp.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.webflux.entity.Passenger;

public interface PassengerRepository extends ReactiveMongoRepository<Passenger, String> {

}
