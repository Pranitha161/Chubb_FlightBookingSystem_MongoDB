package com.flightapp.webflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.webflux.entity.Booking;
import com.flightapp.webflux.service.BookingService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/flight")
public class BookingController {
	private final BookingService bookingService;

	@PostMapping("booking/{flightId}")
	public Mono<ResponseEntity<Void>> bookTicket(@RequestBody Booking booking, @PathVariable String flightId) {
		return bookingService.bookTicket(flightId, booking);
	}

	@GetMapping("ticket/{pnr}")
	public Mono<ResponseEntity<Booking>> getByPnr(@PathVariable String pnr) {
		return bookingService.getTicketsByPnr(pnr);
	}

	@GetMapping("/history/{emailId}")
	public Mono<ResponseEntity<Booking>> getByemailId(@PathVariable String emailId) {
		return bookingService.getBookingsByEmail(emailId);
	}

	@DeleteMapping("/booking/cancel/{pnr}")
	public Mono<ResponseEntity<Void>> cancelBooking(@PathVariable String pnr) {
		return bookingService.deleteBookingByPnr(pnr);
	}
}
