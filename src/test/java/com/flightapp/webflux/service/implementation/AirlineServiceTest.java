package com.flightapp.webflux.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightapp.webflux.entity.Airline;
import com.flightapp.webflux.repository.AirLineRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class AirlineServiceTest {
	@Mock
	private AirLineRepository airlineRepo;
	@InjectMocks
	private AirLineServiceImplementation airlineService;
	@Test
	void testGetAllAirlinesSuccess() {
		Airline airline1=new Airline();
		airline1.setId("1");
		airline1.setName("Indigo");
		Airline airline2=new Airline();
		airline2.setId("12");
		airline2.setName("Air India");
		when(airlineRepo.findAll()).thenReturn(Flux.just(airline1,airline2));
		var airlines=airlineService.getAllAirlines().collectList().block();
		assertNotNull(airlines);
		assertEquals(2,airlines.size());
		assertEquals("Indigo",airlines.get(0).getName());
		assertEquals("Air India",airlines.get(1).getName());
	}
	@Test
	void testGetAllAirlinesFailure() {
		when(airlineRepo.findAll()).thenReturn(Flux.empty());
		var airlines=airlineService.getAllAirlines().collectList().block();
		assertNotNull(airlines);
		assertEquals(0, airlines.size());
	}
	@Test
	void testAddAirlineSuccess() {
		Airline airline1=new Airline();
		airline1.setId("1");
		airline1.setName("Indigo");
		when(airlineRepo.findByName("Indigo")).thenReturn(Mono.empty());
		when(airlineRepo.save(any(Airline.class))).thenReturn(Mono.just(airline1));
		ResponseEntity<Void> res=airlineService.addAirline(airline1).block();
		assertNotNull(res);
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
	}
	@Test
	void testAddAirlineFailure() {
		Airline airline1=new Airline();
		airline1.setId("1");
		airline1.setName("Indigo");
		when(airlineRepo.findByName("Indigo")).thenReturn(Mono.just(airline1));
		when(airlineRepo.save(any(Airline.class))).thenReturn(Mono.just(airline1));
		ResponseEntity<Void> res=airlineService.addAirline(airline1).block();
		assertNotNull(res);
		assertEquals(HttpStatus.CONFLICT, res.getStatusCode());
	}
}
