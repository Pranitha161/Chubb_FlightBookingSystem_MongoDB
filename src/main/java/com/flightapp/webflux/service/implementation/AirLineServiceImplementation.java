package com.flightapp.webflux.service.implementation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.webflux.entity.Airline;
import com.flightapp.webflux.repository.AirLineRepository;
import com.flightapp.webflux.service.AirLineService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AirLineServiceImplementation implements AirLineService {
	private final AirLineRepository airlineRepo;

	public Flux<ResponseEntity<Airline>> getAllAirlines() {
		return airlineRepo.findAll().map(airline -> ResponseEntity.ok(airline));
	}

	public Mono<ResponseEntity<Airline>> addAirline(Airline airline) {
		return airlineRepo.findById(airline.getId())
				.flatMap(existing -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).<Airline>build()))
				.switchIfEmpty(
						airlineRepo.save(airline).map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved)));
	}
}
