package com.flightapp.webflux.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.flightapp.webflux.enums.MealPreference;
import com.flightapp.webflux.enums.TripType;

class BookingTest {
	@Test
	void testBookingGettersAndSetters() {
		Booking booking = new Booking();
		booking.setId("1");
		booking.setEmail("hello@example.com");
		booking.setFlightId("2");
		booking.setMealPrefernce(MealPreference.VEG);
		booking.setTripType(TripType.ONE_WAY);
		booking.setTotalAmount(6000);
		booking.setSeatNumbers(List.of("12C", "12D"));
		Passenger passenger = new Passenger();
		passenger.setId("10");
		passenger.setAge(20);
		passenger.setEmail("hello@example.com");
		passenger.setGender("female");
		booking.setPassengers(List.of(passenger));
		assertEquals("1", booking.getId());
		assertEquals("hello@example.com", booking.getEmail());
		assertEquals("2", booking.getFlightId());
		assertEquals(MealPreference.VEG, booking.getMealPrefernce());
		assertEquals(TripType.ONE_WAY, booking.getTripType());
		assertNotNull(booking.getPassengers().get(0));
		assertEquals("10", booking.getPassengers().get(0).getId());
		assertEquals(20, booking.getPassengers().get(0).getAge());
		assertEquals("hello@example.com", booking.getPassengers().get(0).getEmail());
		assertEquals("female", booking.getPassengers().get(0).getGender());
		assertEquals(1, booking.getPassengers().size());
		assertEquals(6000, booking.getTotalAmount());
	}

	@Test
	void testDefaultValues() {
		Booking booking = new Booking();
		assertNotNull(booking);
		assertNull(booking.getEmail());
		assertNull(booking.getFlightId());
		assertNull(booking.getId());
		assertNull(booking.getMealPrefernce());
		assertNull(booking.getTripType());
		assertEquals(0, booking.getSeatCount());

	}
}
