package com.flightapp.webflux.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	private String airlineId;
	private List<Booking> bookings;
//	private List<Seat> seats=new ArrayList<>();
//	public void initializeSeats(int rows, int cols) { 
//		
//		char[] seatLetters = {'A','B','C','D','E','F'}; 
//		for (int row=1; row<=rows; row++) 
//		{for(int col=0;col<cols;col++) 
//		{ 
//			Seat seat=new Seat(); 
//			seat.setSeatNumber(row+""+seatLetters[col]); 
//		seat.setAvailable(true); 
//		seat.setFlightId(id); 
//		seats.add(seat); 
//		} }
//		this.availableSeats = seats.size(); 
//		}

}
