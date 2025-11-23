package com.flightapp.webflux.entity;

import java.util.List;
import org.springframework.data.annotation.Id;
import com.flightapp.webflux.enums.MealPreference;
import com.flightapp.webflux.enums.TripType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
	@Id
	private String id;
	private String pnr;
	private String email;
	private int seatCount;
	private TripType tripType;
	private MealPreference mealPrefernce;
	private String flightId;
	private List<Passenger> passengers;
	private List<String> seatNumbers;
	private float totalAmount;
}
