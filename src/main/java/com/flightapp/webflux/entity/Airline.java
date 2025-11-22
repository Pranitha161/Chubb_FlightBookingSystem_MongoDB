package com.flightapp.webflux.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "airlines")
public class Airline {
	@Id
	private String id;
	private String name;
	private String logoUrl;
	private List<Flight> flights;
}
