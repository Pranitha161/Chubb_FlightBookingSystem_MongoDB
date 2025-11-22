package com.flightapp.webflux.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
	private String fromPlace;
	private String toPlace;
	private LocalDate date;
}
