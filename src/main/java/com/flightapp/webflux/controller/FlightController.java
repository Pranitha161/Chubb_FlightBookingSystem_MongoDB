package com.flightapp.webflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.SearchRequest;
import com.flightapp.webflux.service.FlightService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/flight/")
public class FlightController {
	private final FlightService flightService;

	@PostMapping("/search")
	public Flux<ResponseEntity<Flight>> searchFlight(@RequestBody SearchRequest searchRequest) {
		return flightService.search(searchRequest);
	}

	@GetMapping("/get")
	public Mono<ResponseEntity<Flight>> getFlight(Flight flight) {
		return flightService.addFlight(flight);
	}
}
