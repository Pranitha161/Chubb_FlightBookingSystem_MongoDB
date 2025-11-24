package com.flightapp.webflux.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.flightapp.webflux.entity.Booking;
import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.Price;
import com.flightapp.webflux.entity.Seat;
import com.flightapp.webflux.enums.TripType;
import com.flightapp.webflux.repository.BookingRepository;
import com.flightapp.webflux.repository.FlightRepository;
import com.flightapp.webflux.repository.SeatRepository;
import com.flightapp.webflux.service.BookingService;

import reactor.core.publisher.Mono;

@WebFluxTest(BookingController.class)
class BookingControllerTest {
	@Autowired
	WebTestClient webtestClient;
	@MockBean
	private BookingService bookingService;
	@MockBean
	private FlightRepository flightRepo;
	@MockBean
	private SeatRepository seatRepo;
	@MockBean
	private BookingRepository bookingRepo;

	@Test
	void testGetBookingByPnrSuccess() {
		Booking booking = new Booking();
		booking.setId("20");
		booking.setEmail("hello@example.com");
		booking.setPnr("1234");
		when(bookingService.getTicketsByPnr("1234")).thenReturn(Mono.just(ResponseEntity.ok(booking)));
		webtestClient.get().uri("/api/v1.0/flight/ticket/{pnr}", "1234").exchange().expectStatus().isOk()
				.expectBody(Booking.class).isEqualTo(booking);

	}

	@Test
	void testGetBookingByPnrFailure() {
		when(bookingService.getTicketsByPnr("1234"))
				.thenReturn(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
		webtestClient.get().uri("/api/v1.0/flight/ticket/{pnr}", "1234").exchange().expectStatus().isNotFound();
	}

	@Test
	void testGetBookingByEmail() {
		Booking booking = new Booking();
		booking.setId("20");
		booking.setEmail("hello@example.com");
		booking.setPnr("1234");
		when(bookingService.getBookingsByEmail("hello@example.com")).thenReturn(Mono.just(ResponseEntity.ok(booking)));
		webtestClient.get().uri("/api/v1.0/flight/history/{emailId}", "hello@example.com").exchange().expectStatus()
				.isOk().expectBody(Booking.class).isEqualTo(booking);

	}

	@Test
	void testGetBookingByEmailFailure() {
		when(bookingService.getBookingsByEmail("hello@example.com"))
				.thenReturn(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
		webtestClient.get().uri("/api/v1.0/flight/history/{emailId}", "hello@example.com").exchange().expectStatus()
				.isNotFound();
	}

	@Test
	void testbookingTicketSuccessfully() {
		Booking booking = new Booking();
		booking.setId("20");
		booking.setEmail("hello@example.com");
		booking.setPnr("1234");
		booking.setFlightId("123");
		Flight flight = new Flight();
		flight.setId("123");
		flight.setAvailableSeats(10);
		Price price = new Price();
		price.setOneWay(2000);
		price.setRoundTrip(10000);
		flight.setPrice(price);
		Seat seat = new Seat();
		seat.setFlightId("123");
		seat.setAvailable(true);
		seat.setId("100");
		when(bookingService.bookTicket(eq("123"), any(Booking.class)))
				.thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CREATED).build()));
		webtestClient.post().uri("/api/v1.0/flight/booking/{flightId}", "123").bodyValue(booking).exchange()
				.expectStatus().isCreated();
	}

	@Test
	void testbookingTicketFlightNotFound() {
		Booking booking = new Booking();
		booking.setSeatNumbers(List.of("1A"));
		booking.setTripType(TripType.ONE_WAY);
		when(bookingService.bookTicket(eq("123"), any(Booking.class)))
				.thenReturn(Mono.just(ResponseEntity.badRequest().build()));
		webtestClient.post().uri("/api/v1.0/flight/booking/{flightId}", "123").bodyValue(booking).exchange()
				.expectStatus().isBadRequest();
	}

	@Test
	void testbookingTicketSeatNotFound() {
		Booking booking = new Booking();
		booking.setId("12");
		Flight flight = new Flight();
		flight.setId("123");
		flight.setAirlineId("1234");
		flight.setToPlace("Hyderabad");
		booking.setFlightId(flight.getId());
		Seat seat = new Seat();
		seat.setId("800");
		seat.setFlightId("123");
		seat.setSeatNumber("1A");
		seat.setAvailable(false);
		booking.setSeatNumbers(List.of("1A"));
		booking.setTripType(TripType.ONE_WAY);
		when(bookingService.bookTicket(eq("123"), any(Booking.class)))
				.thenReturn(Mono.just(ResponseEntity.notFound().build()));
		webtestClient.post().uri("/api/v1.0/flight/booking/{flightId}", "123").bodyValue(booking).exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void testDeleteSuccessfully() {
		Booking booking = new Booking();
		booking.setId("100");
		booking.setEmail("hello@example.com");
		booking.setPnr("1234");
		when(bookingService.deleteBookingByPnr("1234")).thenReturn(Mono.just(ResponseEntity.noContent().build()));
		webtestClient.delete().uri("/api/v1.0/flight/booking/cancel/{pnr}", "1234").exchange().expectStatus()
				.isNoContent();

	}

	@Test
	void testDeleteFailure() {
		when(bookingService.deleteBookingByPnr("1234")).thenReturn(Mono.just(ResponseEntity.notFound().build()));
		webtestClient.delete().uri("/api/v1.0/flight/booking/cancel/{pnr}", "1234").exchange().expectStatus()
				.isNotFound();

	}

}
