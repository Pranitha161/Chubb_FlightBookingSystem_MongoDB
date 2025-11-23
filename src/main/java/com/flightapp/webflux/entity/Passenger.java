package com.flightapp.webflux.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "passengers")
public class Passenger {
	@Id
	private String id;
	private String name;
	private String gender;
	private int age;
	private String email;
	private String bookingId;
}
