package com.flightapp.webflux.entity;

import java.util.List;
import org.springframework.data.annotation.Id;
import com.flightapp.webflux.enums.MealPreference;
import com.flightapp.webflux.enums.TripType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email cannot be blank")
	private String email;
	@Positive(message = "Seat count must be positive")
	@Max(value = 10, message = "cannot book more than 10 tickets")
	private int seatCount;
	@NotNull(message = "Trip type is required")
	private TripType tripType;
	@NotNull(message = "Meal preference is required")
	private MealPreference mealPrefernce;
	private String flightId;
	@NotNull(message = "Passengers list cannot be null")
	@Size(min = 1, message = "At least one passenger must be included")
	private List<Passenger> passengers;
	@NotNull(message = "Seat numbers list cannot be null")
	@Size(min = 1, message = "At least one seat number must be provided")
	private List<String> seatNumbers;
	private float totalAmount;
}
