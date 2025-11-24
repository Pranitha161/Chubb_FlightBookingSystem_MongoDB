package com.flightapp.webflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.webflux.entity.Airline;
import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.service.AirLineService;
import com.flightapp.webflux.service.FlightService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1.0/flight/airline")
@RequiredArgsConstructor
public class AirLineController {
	private final FlightService flightService;
	private final AirLineService airlineService;

	@PostMapping("/inventory/add")
	public Mono<ResponseEntity<Void>> addInventory(@RequestBody Flight flight) {
		return flightService.addFlight(flight);
	}

	@GetMapping("/get")
	public Flux<Airline> getAirlines() {
		return airlineService.getAllAirlines();
	}

	@PostMapping("/add")
	public Mono<ResponseEntity<Void>> addAirline(@RequestBody Airline airline) {
		return airlineService.addAirline(airline);
	}
	

}
