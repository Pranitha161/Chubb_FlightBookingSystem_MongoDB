package com.flightapp.webflux.service.implementation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.webflux.entity.Booking;
import com.flightapp.webflux.entity.Seat;
import com.flightapp.webflux.repository.BookingRepository;
import com.flightapp.webflux.repository.FlightRepository;
import com.flightapp.webflux.repository.SeatRepository;
import com.flightapp.webflux.service.BookingService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookingServiceImplementation implements BookingService {
	private final BookingRepository bookingRepo;
	private final SeatRepository seatRepo;
	private final FlightRepository flightRepo;

	public Mono<ResponseEntity<Booking>> getTicketsByPnr(String pnr) {
		return bookingRepo.findByPnr(pnr).map(booking -> ResponseEntity.ok(booking));
	}

	public Mono<ResponseEntity<Booking>> getBookingsByEmail(String email) {
		return bookingRepo.findByEmail(email).map(booking -> ResponseEntity.ok(booking));
	}

	public Mono<ResponseEntity<Void>> deleteBookingByPnr(String pnr) {
		return bookingRepo.findByPnr(pnr).flatMap(
				booking -> bookingRepo.delete(booking).then(Mono.just(ResponseEntity.noContent().<Void>build())))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	public Mono<ResponseEntity<Void>> bookTicket(String flightId, Booking booking) {
		return flightRepo.findById(flightId)
				.flatMap(flight -> seatRepo.findByFlightId(flightId).collectList().flatMap(seats -> {
					List<String> seatReq = booking.getSeatNumbers();
					for (String req : seatReq) {
						Seat seat = seats.stream().filter(s -> s.getSeatNumber().equals(req)).findFirst().orElse(null);
						if (seat == null || !seat.isAvailable()) {
							return Mono.just(ResponseEntity.badRequest().<Void>build());
						}
					}
					seats.stream().filter(s -> seatReq.contains(s.getSeatNumber())).forEach(s -> s.setAvailable(false));

					flight.setAvailableSeats(flight.getAvailableSeats() - seatReq.size());
					return flightRepo.save(flight).thenMany(seatRepo.saveAll(seats)).then(bookingRepo.save(booking))
							.thenReturn(ResponseEntity.status(HttpStatus.CREATED).<Void>build());
				})).switchIfEmpty(Mono.just(ResponseEntity.badRequest().<Void>build()));
	}
}
