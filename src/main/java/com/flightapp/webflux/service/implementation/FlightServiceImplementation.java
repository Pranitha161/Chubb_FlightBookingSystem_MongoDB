package com.flightapp.webflux.service.implementation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.SearchRequest;
import com.flightapp.webflux.repository.AirLineRepository;
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
	private final AirLineRepository airlineRepo;

	@Override // Mono because we want to send only one https response for entire request
	public Mono<ResponseEntity<List<Flight>>> search(SearchRequest searchRequest) {
		return flightRepo.getFightByFromPlaceAndToPlace(searchRequest.getFromPlace(), searchRequest.getToPlace())
				.filter(flight -> flight.getArrivalTime().toLocalDate().equals(searchRequest.getDate())).collectList()
				.map(list -> list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list));
	}

	@Override
	public Mono<ResponseEntity<Void>> addFlight(Flight flight) {
		return airlineRepo.findById(flight.getAirlineId())
				.flatMap(exists -> flightRepo.save(flight)
						.then(Mono.just(ResponseEntity.status(HttpStatus.CREATED).<Void>build())))
				.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).<Void>build()));

	}

	@Override
	public Flux<Flight> getFlights() {
		return flightRepo.findAll();
	}

	@Override
	public Mono<ResponseEntity<Flight>> getFlightById(String flightId) {
		return flightRepo.findById(flightId).map(exists -> ResponseEntity.ok(exists))
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
