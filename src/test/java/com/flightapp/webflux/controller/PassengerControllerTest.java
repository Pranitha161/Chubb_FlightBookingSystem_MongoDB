package com.flightapp.webflux.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.flightapp.webflux.entity.Passenger;
import com.flightapp.webflux.service.PassengerService;

import reactor.core.publisher.Mono;

@WebFluxTest(PassengerController.class)
class PassengerControllerTest {
	@Autowired
	 WebTestClient webtestClient;
	@MockBean
	private PassengerService passengerService;
	@Test
	void testGetPasserngerByIdSuccess() {
		Passenger passenger=new Passenger();
		passenger.setId("123");
		passenger.setAge(20);
		passenger.setEmail("hello@example.com");
		when(passengerService.getPassengerById("123")).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
		webtestClient.get().uri("/api/v1.0/passenger/get/{passengerId}","123").exchange().expectStatus().isOk();
	}
	@Test
	void testGetPassengerByIdFailure() {
		when(passengerService.getPassengerById("123")).thenReturn(Mono.just(ResponseEntity.notFound().build()));
		webtestClient.get().uri("/api/v1.0/passenger/get/{passengerId}","123").exchange().expectStatus().isNotFound();
	}
	@Test 
	void testGetPassengerByEmailSuccess() {
		Passenger passenger=new Passenger();
		passenger.setId("123");
		passenger.setAge(20);
		passenger.setName("hello");
		passenger.setGender("Female");
		passenger.setEmail("hello@example.com");
		when(passengerService.getPassengerByEmail("hello@example.com")).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
		webtestClient.get().uri("/api/v1.0/passenger/get/email/{email}","hello@example.com").exchange().expectStatus().isOk();
	}
	@Test
	void testGetPassengerByEmailFailure() {
		when(passengerService.getPassengerByEmail("hello@example.com")).thenReturn(Mono.just(ResponseEntity.notFound().build()));
		webtestClient.get().uri("/api/v1.0/passenger/get/email/{email}","hello@example.com").exchange().expectStatus().isNotFound();
	}
	@Test 
	void testUpdatePassengerByIdSuccess() {
		Passenger passenger=new Passenger();
		passenger.setId("123");
		passenger.setAge(20);
		passenger.setName("hello");
		passenger.setGender("Female");
		passenger.setEmail("hello@example.com");
		when(passengerService.updateById(eq("123"),any(Passenger.class))).thenReturn(Mono.just(ResponseEntity.ok(passenger)));
		webtestClient.post().uri("/api/v1.0/passenger/update/{passengerId}","123").bodyValue(passenger).exchange().expectStatus().isOk().expectBody(Passenger.class).isEqualTo(passenger);
	}
	@Test
	void testUpdatetPassengerByIdFailure() {
		Passenger passenger=new Passenger();
		when(passengerService.updateById(eq("123"),any(Passenger.class))).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
		webtestClient.post().uri("/api/v1.0/passenger/update/{passengerId}","123").bodyValue(passenger).exchange().expectStatus().isBadRequest();
	}
	@Test
	void testSavePassengerSuccess() {
		Passenger passenger=new Passenger();
		passenger.setId("123");
		passenger.setAge(20);
		passenger.setName("hello");
		passenger.setGender("Female");
		passenger.setEmail("hello@example.com");
		when(passengerService.savePassenger(any(Passenger.class))).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
		webtestClient.post().uri("/api/v1.0/passenger/add").bodyValue(passenger).exchange().expectStatus().isCreated();
	}
	@Test
	void testSavePassengerFailure() {
		Passenger passenger=new Passenger();
		when(passengerService.savePassenger(any(Passenger.class))).thenReturn(Mono.just(ResponseEntity.badRequest().build()));
		webtestClient.post().uri("/api/v1.0/passenger/add").bodyValue(passenger).exchange().expectStatus().isBadRequest();
	}
	@Test
	void testDeletePassengerByIdSuccess() {
		Passenger passenger=new Passenger();
		passenger.setId("123");
		passenger.setAge(20);
		passenger.setName("hello");
		passenger.setGender("Female");
		passenger.setEmail("hello@example.com");
		when(passengerService.deleteById("123")).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
		webtestClient.delete().uri("/api/v1.0/passenger/delete/{passengerId}","123").exchange().expectStatus().isNoContent();
	}
	@Test
	void testDeletePassengerByIdFailure() {
		Passenger passenger=new Passenger();
		passenger.setId("1234");
		passenger.setAge(20);
		passenger.setName("hello");
		passenger.setGender("Female");
		passenger.setEmail("hello@example.com");
		when(passengerService.deleteById("123")).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
		webtestClient.delete().uri("/api/v1.0/passenger/delete/{passengerId}","123").exchange().expectStatus().isNotFound();
	}
	
}
