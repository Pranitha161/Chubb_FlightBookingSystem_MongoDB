package com.flightapp.webflux.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "flights")
public class Flight {
	private String id;
	private String fromPlace;
	private String toPlace;
	private LocalDateTime arrivalTime;
	private LocalDateTime departureTime;
	private int availableSeats;
	private Price price;
	private Airline airline;
	private List<Booking> bookings;
	private List<Seat> seats;

}
