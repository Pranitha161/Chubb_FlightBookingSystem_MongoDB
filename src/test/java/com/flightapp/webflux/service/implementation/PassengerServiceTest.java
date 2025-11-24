package com.flightapp.webflux.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightapp.webflux.entity.Passenger;
import com.flightapp.webflux.repository.PassengerRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {
	@InjectMocks
	private PassengerServiceImplementation passengerService;
	@Mock
	private PassengerRepository passengerRepo;
	@Test
	void testGetPassengerByIdSuccess() {
		Passenger p=new Passenger();
		p.setAge(10);
		p.setId("12");
		p.setName("Hello");
		when(passengerRepo.findById(anyString())).thenReturn(Mono.just(p));
		ResponseEntity<Passenger> res=passengerService.getPassengerById("hello@example.com").block();
		assertNotNull(res);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(p,res.getBody());
	}
	@Test
	void testGetPassengerByIdFailure() {
		when(passengerRepo.findById(anyString())).thenReturn(Mono.empty());
		ResponseEntity<Passenger> res=passengerService.getPassengerById("hello@example.com").block();
		assertNotNull(res);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertNull(res.getBody());
	}
	@Test
	void testGetPassengerByEmailSuccess() {
		Passenger p=new Passenger();
		p.setAge(10);
		p.setId("12");
		p.setName("Hello");
		p.setEmail("hello@example.com");
		when(passengerRepo.findByEmail(anyString())).thenReturn(Mono.just(p));
		ResponseEntity<Passenger> res=passengerService.getPassengerByEmail("hello@example.com").block();
		assertNotNull(res);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(p,res.getBody());
	}
	@Test
	void testGetPassengerByEmailFailure() {
		when(passengerRepo.findByEmail(anyString())).thenReturn(Mono.empty());
		ResponseEntity<Passenger> res=passengerService.getPassengerByEmail("hello@example.com").block();
		assertNotNull(res);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
		assertNull(res.getBody());
	}
	@Test
	void testSavePassengerSuccess() {
		Passenger p=new Passenger();
		p.setAge(10);
		p.setId("12");
		p.setName("Hello");
		p.setEmail("hello@example.com");
		when(passengerRepo.findByEmail(anyString())).thenReturn(Mono.empty());
		when(passengerRepo.save(any(Passenger.class))).thenReturn(Mono.just(p));
		ResponseEntity<Void> res=passengerService.savePassenger(p).block();
		assertNotNull(res);
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
	}
	@Test
	void testSavePassengerFailure() {
		Passenger p=new Passenger();
		p.setAge(10);
		p.setId("12");
		p.setName("Hello");
		p.setEmail("hello@example.com");
		when(passengerRepo.findByEmail(anyString())).thenReturn(Mono.just(p));
		when(passengerRepo.save(any(Passenger.class))).thenReturn(Mono.just(p));
		ResponseEntity<Void> res=passengerService.savePassenger(p).block();
		assertNotNull(res);
		assertEquals(HttpStatus.BAD_REQUEST,res.getStatusCode());
	}
	@Test
	void testUpdatePassengerSuccess() {
		Passenger p=new Passenger();
		p.setAge(10);
		p.setId("12");
		p.setName("Hello");
		p.setEmail("hello@example.com");
		when(passengerRepo.save(any(Passenger.class))).thenReturn(Mono.just(p));
		when(passengerRepo.findById(anyString())).thenReturn(Mono.just(p));
		ResponseEntity<Passenger> res=passengerService.updateById("1",p).block();
		assertNotNull(res);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
	void testUpdatePassengerFailure() {
		Passenger p=new Passenger();
		p.setAge(10);
		p.setId("12");
		p.setName("Hello");
		p.setEmail("hello@example.com");
		when(passengerRepo.findById(anyString())).thenReturn(Mono.empty());
		ResponseEntity<Passenger> res=passengerService.updateById("1",p).block();
		assertNotNull(res);
		assertEquals(HttpStatus.BAD_REQUEST,res.getStatusCode());
	}
}
