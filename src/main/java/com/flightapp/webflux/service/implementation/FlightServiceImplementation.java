package com.flightapp.webflux.service.implementation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.SearchRequest;
import com.flightapp.webflux.repository.FlightRepository;
import com.flightapp.webflux.repository.SeatRepository;
import com.flightapp.webflux.service.FlightService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FlightServiceImplementation implements FlightService {

	private final FlightRepository flightRepo;

	public Flux<ResponseEntity<Flight>> search(SearchRequest searchRequest) {
		return flightRepo.getFightByFromPlaceAndToPlace(searchRequest.getFromPlace(), searchRequest.getToPlace())
				.filter(flight -> flight.getArrivalTime().toLocalDate().equals(searchRequest.getDate()))
				.map(flight -> ResponseEntity.ok(flight));
	}

	public Mono<ResponseEntity<Flight>> addFlight(Flight flight) {
		return flightRepo.findById(flight.getId())
				.flatMap(existing -> Mono.just(ResponseEntity.badRequest().<Flight>build())) // Flight already exists
				.switchIfEmpty(
						flightRepo.save(flight).map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved)) // Flight
																													// saved
				);
	}
}
