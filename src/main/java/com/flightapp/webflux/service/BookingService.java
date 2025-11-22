package com.flightapp.webflux.service;

import org.springframework.http.ResponseEntity;

import com.flightapp.webflux.entity.Booking;

import reactor.core.publisher.Mono;

public interface BookingService {
	Mono<ResponseEntity<Void>> bookTicket(String flightId, Booking booking);

	Mono<ResponseEntity<Booking>> getTicketsByPnr(String pnr);

	Mono<ResponseEntity<Booking>> getBookingsByEmail(String email);

	Mono<ResponseEntity<Void>> deleteBookingByPnr(String pnr);

}
