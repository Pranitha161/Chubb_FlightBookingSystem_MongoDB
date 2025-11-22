package com.flightapp.webflux.service;

import java.util.Map;

import com.flightapp.webflux.entity.Booking;

import reactor.core.publisher.Mono;

public interface BookingService {
	Mono<Map<String, String>> bookTicket(String flightId, Booking booking);

	Mono<Booking> getTicketsByPnr(String pnr);

	Mono<Booking> getBookingsByEmail(String email);

	Mono<Void> deleteBookingByPnr(String pnr);

}
