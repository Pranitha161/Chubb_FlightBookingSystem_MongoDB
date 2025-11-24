package com.flightapp.webflux.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightapp.webflux.entity.Booking;
import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.entity.Passenger;
import com.flightapp.webflux.entity.Price;
import com.flightapp.webflux.entity.Seat;
import com.flightapp.webflux.enums.TripType;
import com.flightapp.webflux.repository.BookingRepository;
import com.flightapp.webflux.repository.FlightRepository;
import com.flightapp.webflux.repository.PassengerRepository;
import com.flightapp.webflux.repository.SeatRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
	@Mock
	private BookingRepository bookingRepo;
	@Mock
	private FlightRepository flightRepo;
	@Mock
	private PassengerRepository passengerRepo;
	@Mock
	private SeatRepository seatRepo;
	@InjectMocks
	private BookingServiceImplementation bookingService;

	@Test
	void testGetTicketByPnrSuccess() {
		Booking booking = new Booking();
		booking.setId("12");
		booking.setPnr("PNR12");
		booking.setFlightId("1");
		when(bookingRepo.findByPnr("PNR12")).thenReturn(Mono.just(booking));
		ResponseEntity<Booking> res = bookingService.getTicketsByPnr("PNR12").block();
		assertNotNull(res);
		assertEquals(booking, res.getBody());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	void testGetTicketByPnrFailure() {
		Booking booking = new Booking();
		booking.setId("12");
		booking.setFlightId("1");
		when(bookingRepo.findByPnr("PNR12")).thenReturn(Mono.empty());
		ResponseEntity<Booking> res = bookingService.getTicketsByPnr("PNR12").block();
		assertNotNull(res);
		assertEquals(null, res.getBody());
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	void testGetTicketByEmailSuccess() {
		Booking booking = new Booking();
		booking.setId("12");
		booking.setEmail("hello@example.com");
		booking.setPnr("PNR12");
		booking.setFlightId("1");
		when(bookingRepo.findByEmail("hello@example.com")).thenReturn(Mono.just(booking));
		ResponseEntity<Booking> res = bookingService.getBookingsByEmail("hello@example.com").block();
		assertNotNull(res);
		assertEquals(booking, res.getBody());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	void testGetTicketByEmailFailure() {
		Booking booking = new Booking();
		booking.setId("12");
		booking.setFlightId("1");
		when(bookingRepo.findByEmail("hello@example.com")).thenReturn(Mono.empty());
		ResponseEntity<Booking> res = bookingService.getBookingsByEmail("hello@example.com").block();
		assertNotNull(res);
		assertEquals(null, res.getBody());
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	void testDeleteBookingSuccess() {
		Booking booking = new Booking();
		booking.setId("12");
		booking.setEmail("hello@example.com");
		booking.setPnr("PNR12");
		booking.setFlightId("1");
		when(bookingRepo.findByPnr("PNR12")).thenReturn(Mono.just(booking));
		when(bookingRepo.delete(booking)).thenReturn(Mono.empty());
		ResponseEntity<Void> res = bookingService.deleteBookingByPnr("PNR12").block();
		assertNotNull(res);
		assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
		assertNull(res.getBody());

	}

	@Test
	void testDeleteBookingFailure() {
		Booking booking = new Booking();
		booking.setId("12");
		booking.setEmail("hello@example.com");
		when(bookingRepo.findByPnr("PNR12")).thenReturn(Mono.empty());
//		when(bookingRepo.delete(booking)).thenReturn(Mono.empty());
		ResponseEntity<Void> res = bookingService.deleteBookingByPnr("PNR12").block();
		assertNotNull(res);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	void testbookTicketSuccess() {
		Passenger p1 = new Passenger();
		p1.setBookingId("12");
		p1.setAge(10);
		p1.setEmail("hello@example.com");
		p1.setName("Hello");
		Passenger p2 = new Passenger();
		p2.setBookingId("12");
		p2.setAge(10);
		p2.setEmail("hello1@example.com");
		p2.setName("HelloRoot");
		Flight flight = new Flight();
		flight.setId("12");
		flight.setFromPlace("Hyderabad");
		flight.setToPlace("Mumbai");
		Price p = new Price();
		p.setOneWay(1000);
		p.setRoundTrip(2000);
		flight.setPrice(p);
		Seat s1 = new Seat();
		s1.setId("1");
		s1.setFlightId("12");
		s1.setAvailable(true);
		s1.setSeatNumber("12A");
		Seat s2 = new Seat();
		s2.setId("2");
		s2.setFlightId("12");
		s2.setAvailable(true);
		s2.setSeatNumber("12B");
		Booking booking = new Booking();
		booking.setId("12");
		booking.setEmail("hello@example.com");
		booking.setFlightId("12");
		booking.setPassengers(List.of(p1, p2));
		booking.setSeatNumbers(List.of("12A", "12B"));
		booking.setTripType(TripType.ONE_WAY);
		when(flightRepo.findById("12")).thenReturn(Mono.just(flight));
		when(passengerRepo.saveAll(anyList())).thenReturn(Flux.fromIterable(List.of(p1, p2)));
		when(flightRepo.save(any(Flight.class))).thenReturn(Mono.just(flight));
		when(seatRepo.findByFlightId("12")).thenReturn(Flux.just(s1, s2));
		when(seatRepo.saveAll(anyList())).thenReturn(Flux.just(s1, s2));
		when(bookingRepo.save(any(Booking.class))).thenReturn(Mono.just(booking));
		ResponseEntity<Void> res = bookingService.bookTicket("12", booking).block();
		assertNotNull(res);
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
		assertNull(res.getBody());
	}

	@Test
	void testbookTicketFlightIdFailure() {
		Booking booking = new Booking();
		booking.setId("12");
		booking.setEmail("hello@example.com");
		booking.setFlightId("12");

		when(flightRepo.findById("12")).thenReturn(Mono.empty());
		ResponseEntity<Void> res = bookingService.bookTicket("12", booking).block();
		assertNotNull(res);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	void testbookTicketSeatEmptyFailure() {
		Booking booking = new Booking();
		booking.setId("12");
		booking.setEmail("hello@example.com");
		booking.setFlightId("12");
		booking.setSeatNumbers(List.of("12A", "12B"));
		Flight flight = new Flight();
		flight.setId("12");
		flight.setFromPlace("Hyderabad");
		flight.setToPlace("Mumbai");
		when(flightRepo.findById("12")).thenReturn(Mono.just(flight));
		when(seatRepo.findByFlightId("12")).thenReturn(Flux.empty());
		ResponseEntity<Void> res = bookingService.bookTicket("12", booking).block();
		assertNotNull(res);
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}
}
