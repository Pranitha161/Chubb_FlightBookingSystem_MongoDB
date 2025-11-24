package com.flightapp.webflux.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flightapp.webflux.entity.Seat;
import com.flightapp.webflux.repository.SeatRepository;

import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {
	@Mock
	private SeatRepository seatRepo;
	@InjectMocks
	private SeatServiceImplementation seatService;
	@Test
	void initializeSeatsSuccess() {
		String flightId="12";
		int rows=1;
		int cols=6;
		Seat s1=new Seat();
		s1.setId("1A");
		s1.setFlightId(flightId);
		s1.setAvailable(true);
		Seat s2=new Seat();
		s2.setId("1A");
		s2.setFlightId(flightId);
		s2.setAvailable(true);
		List<Seat> seats=List.of(s1,s2);
		when(seatRepo.saveAll(anyList())).thenReturn(Flux.fromIterable(seats));
		List<Seat> res=seatService.initialiszeSeats(flightId, rows, cols).collectList().block();
		assertEquals(2, res.size());
	}
	@Test
	void testInitializeSeatsFailure() {
		String flightId="12";
		int rows=1;
		int cols=6;
		when(seatRepo.saveAll(anyList())).thenReturn(Flux.empty());
		List<Seat> res=seatService.initialiszeSeats(flightId, rows, cols).collectList().block();
		assertNotNull(res);
		assertTrue(res.isEmpty());
	}
}
