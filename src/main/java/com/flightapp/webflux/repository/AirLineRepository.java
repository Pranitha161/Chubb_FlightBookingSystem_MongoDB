package com.flightapp.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.webflux.entity.Airline;

public interface AirLineRepository extends ReactiveMongoRepository<Airline, String> {

}
