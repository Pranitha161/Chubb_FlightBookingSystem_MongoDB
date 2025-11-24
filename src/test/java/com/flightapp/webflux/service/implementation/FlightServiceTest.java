package com.flightapp.webflux.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightapp.webflux.entity.Airline;
import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.SearchRequest;
import com.flightapp.webflux.repository.AirLineRepository;
import com.flightapp.webflux.repository.FlightRepository;
import com.flightapp.webflux.service.Seatservice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
	@Mock
	private FlightRepository flightRepo;
	@Mock
	private AirLineRepository airlineRepo;
	@Mock
	private Seatservice seatService;
	@InjectMocks
	private FlightServiceImplementation flightService;
	@Test
	void testSearchSuccess() {
		Airline airline=new Airline();
		airline.setId("123");
		airline.setName("Indigo");
		Flight flight=new Flight();
		flight.setAirlineId("123");
		flight.setId("12");
		flight.setFromPlace("Hyderabad");
		flight.setToPlace("Mumbai");
		flight.setArrivalTime(LocalDateTime.of(2025, 11, 24, 12, 30));
		SearchRequest search=new SearchRequest();
		search.setToPlace("Mumbai");
		search.setFromPlace("Hyderabad");
		search.setDate(LocalDate.of(2025, 11, 24));
//		when(airlineRepo.findById("123")).thenReturn(Mono.just(airline));
		when(flightRepo.getFightByFromPlaceAndToPlace("Hyderabad", "Mumbai")).thenReturn(Flux.just(flight));
		ResponseEntity<List<Flight>> res=flightService.search(search).block();
		assertNotNull(res);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertNotNull(res.getBody());
	}
	@Test
	void testSearchFailure() {
		SearchRequest search=new SearchRequest();
		search.setToPlace("Mumbai");
		search.setFromPlace("Hyderabad");
		search.setDate(LocalDate.of(2025, 11, 24));
		when(flightRepo.getFightByFromPlaceAndToPlace(anyString(), anyString())).thenReturn(Flux.empty());
		ResponseEntity<List<Flight>> res=flightService.search(search).block();
		assertNotNull(res);
		assertEquals(HttpStatus.NO_CONTENT,res.getStatusCode());
		assertNull(res.getBody());
	}
	@Test
	void testaddFlightSuccess() {
		Flight flight=new Flight();
		flight.setAirlineId("123");
		flight.setId("12");
		flight.setAvailableSeats(60);
		flight.setFromPlace("Hyderabad");
		flight.setToPlace("Mumbai");
		flight.setArrivalTime(LocalDateTime.of(2025, 11, 24, 12, 30));
		Airline airline=new Airline();
		airline.setId("123");
		airline.setName("Indigo");
		when(airlineRepo.findById("123")).thenReturn(Mono.just(airline));
		when(flightRepo.save(flight)).thenReturn(Mono.just(flight));
		when(seatService.initialiszeSeats("12", 10, 6)).thenReturn(Flux.empty());
		ResponseEntity<Void> res=flightService.addFlight(flight).block();
		assertNotNull(res);
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
	}
	@Test
	void testAddFlightFailure() {
		Flight flight=new Flight();
		flight.setAirlineId("123");
		flight.setId("12");
		flight.setAvailableSeats(60);
		flight.setFromPlace("Hyderabad");
		flight.setToPlace("Mumbai");
		when(airlineRepo.findById("123")).thenReturn(Mono.empty());
		ResponseEntity<Void> res=flightService.addFlight(flight).block();
		assertNotNull(res);
		assertEquals(HttpStatus.BAD_REQUEST,res.getStatusCode());
	}
	@Test
	void testAddFlightInvalidSeats() {
		Flight flight=new Flight();
		flight.setAvailableSeats(5);
		ResponseEntity<Void> res=flightService.addFlight(flight).block();
		assertEquals(HttpStatus.BAD_REQUEST,res.getStatusCode());
	}
	@Test
	void testGetFlightsSuccess() {
		Flight flight=new Flight();
		flight.setAirlineId("123");
		flight.setId("12");
		flight.setFromPlace("Hyderabad");
		flight.setToPlace("Mumbai");
		Flight flight1=new Flight();
		flight1.setAirlineId("1234");
		flight1.setId("12");
		flight1.setFromPlace("Mumbai");
		flight1.setToPlace("Hyderabad");
		when(flightRepo.findAll()).thenReturn(Flux.just(flight,flight1));
		var res=flightService.getFlights().collectList().block();
		assertNotNull(res);
		assertEquals(2, res.size());
		assertEquals("Hyderabad", res.get(0).getFromPlace());
		
	}
	@Test
	void testGetFlightsFailure() {
		when(flightRepo.findAll()).thenReturn(Flux.empty());
		var res=flightService.getFlights().collectList().block();
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}
	@Test
	void testGetFlightByIdSuccess() {
		Flight flight=new Flight();
		flight.setAirlineId("123");
		flight.setId("12");
		flight.setFromPlace("Hyderabad");
		flight.setToPlace("Mumbai");
		when(flightRepo.findById("12")).thenReturn(Mono.just(flight));
		ResponseEntity<Flight> res=flightService.getFlightById("12").block();
		assertNotNull(res);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(flight, res.getBody());
	}
	@Test
	void testGetFlightByIdFailure() {
		when(flightRepo.findById("12")).thenReturn(Mono.empty());
		ResponseEntity<Flight> res=flightService.getFlightById("12").block();
		assertNotNull(res);
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
	}
}
