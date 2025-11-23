package com.flightapp.webflux.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class AirlineTest {
	@Test
	void testAirlineGettersSetters() {
		Airline airline = new Airline();
		airline.setId("1");
		airline.setName("Indigo");
		airline.setLogoUrl("http://logo.url");
		assertEquals("1", airline.getId());
		assertEquals("Indigo", airline.getName());
		assertEquals("http://logo.url", airline.getLogoUrl());
	}

	@Test
	void testAirlineFlightGettersSetters() {
		Airline airline = new Airline();
		airline.setId("1");
		airline.setName("Indigo");
		airline.setLogoUrl("http://logo.url");
		Flight flight1 = new Flight();
		flight1.setId("10");
		flight1.setFromPlace("Delhi");
		flight1.setToPlace("Hyderabad");

		Flight flight2 = new Flight();
		flight2.setId("20");
		flight2.setFromPlace("Mumbai");
		flight2.setToPlace("Chennai");
		airline.setFlights(List.of(flight1, flight2));
		assertNotNull(airline.getFlights());
		assertEquals(2, airline.getFlights().size());
		assertEquals("Delhi", airline.getFlights().get(0).getFromPlace());
		assertEquals("Hyderabad", airline.getFlights().get(0).getToPlace());
		assertEquals("Mumbai", airline.getFlights().get(1).getFromPlace());
		assertEquals("Chennai", airline.getFlights().get(1).getToPlace());
	}

	@Test
	void testDefaultValues() {
		Airline airline = new Airline();
		assertNotNull(airline);
		assertNull(airline.getId());
		assertNull(airline.getLogoUrl());
		assertNull(airline.getName());
		assertNull(airline.getFlights());
	}
}
