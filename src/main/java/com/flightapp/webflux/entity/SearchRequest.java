package com.flightapp.webflux.entity;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class SearchRequest {
	private String fromPlace;
	private String toPlace;
	private LocalDate date;
}
