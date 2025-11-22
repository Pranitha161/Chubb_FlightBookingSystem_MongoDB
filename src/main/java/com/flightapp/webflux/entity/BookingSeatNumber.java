package com.flightapp.webflux.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "booking_seat_numbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingSeatNumber {
	@Id
	private Integer id;
	private Integer bookingId;
	private String seatNumber;
}
