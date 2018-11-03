package com.ecc.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecc.model.Horse;

import org.mockito.runners.MockitoJUnitRunner;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import java.lang.IllegalArgumentException;
import java.util.List;
import java.util.function.Consumer;

@RunWith(MockitoJUnitRunner.class)
public class HorseRaceServiceTest {
	
	private int horseCount;
	private int minHealthyHorseCount;
	private float maxStartDistance;
	private float trackDistance;
	private HorseRaceService service;

	@Before
	public void setDefaultConstructorArgs() {
		this.horseCount = 100;
		this.minHealthyHorseCount = 10;
		this.maxStartDistance = 10f;
		this.trackDistance = 100f;

		this.service = new HorseRaceService(
			this.horseCount, this.minHealthyHorseCount, this.maxStartDistance, this.trackDistance);
	}

	@Test
	public void whenMinHealthyHorseCountIsGreaterThanHorseCountWhenGenerateHorsesThenThrowException() {
		this.horseCount = 10;
		this.minHealthyHorseCount = 11;

		Throwable thrown = catchThrowable(() -> {
			this.service = new HorseRaceService(
				this.horseCount, this.minHealthyHorseCount, this.maxStartDistance, this.trackDistance);
		});

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
						  .hasMessage("Number of horses must be greater than or equal to the min " + 
						  			  "number of healthy horses.");
	}

	@Test
	public void whenMinHealthyHorseCountIsNegativeThenThrowException() {
		this.minHealthyHorseCount = -1;

		Throwable thrown = catchThrowable(() -> {
			this.service = new HorseRaceService(
				this.horseCount, this.minHealthyHorseCount, this.maxStartDistance, this.trackDistance);
		});

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
						  .hasMessage("Minimum healthy horse count must be a whole number.");	
	}

	@Test
	public void whenHorseCountIsLessThanOneThenThrowException() {
		this.horseCount = 0;

		Throwable thrown = catchThrowable(() -> {
			this.service = new HorseRaceService(
				this.horseCount, this.minHealthyHorseCount, this.maxStartDistance, this.trackDistance);
		});

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
						  .hasMessage("Horse count should at least be one.");
	}

	@Test
	public void whenMaxStartDistanceIsNegativeThenThrowException() {
		this.maxStartDistance = -1;	
		
		Throwable thrown = catchThrowable(() -> {
			this.service = new HorseRaceService(
				this.horseCount, this.minHealthyHorseCount, this.maxStartDistance, this.trackDistance);
		});

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
						  .hasMessage("Max start distance must be a positive float number.");	
	}

	@Test
	public void whenTrackDistanceIsLessThanOrEqualToZeroThenThrowException() {
		this.trackDistance = 0;	
		
		Throwable thrown = catchThrowable(() -> {
			this.service = new HorseRaceService(
				this.horseCount, this.minHealthyHorseCount, this.maxStartDistance, this.trackDistance);
		});

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
						  .hasMessage("Track distance must be greater than 0.");			
	}

	@Test
	public void whenMaxStartDistanceIsGreaterThanOrEqualToTrackDistanceThenThrowException() {
		this.maxStartDistance = 11;
		this.trackDistance = 10;

		Throwable thrown = catchThrowable(() -> {
			this.service = new HorseRaceService(
				this.horseCount, this.minHealthyHorseCount, this.maxStartDistance, this.trackDistance);
		});

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
						  .hasMessage("Track distance must be greater than max start distance.");	
	}

	@Test
	public void givenMinHealthyHorseCountThenGenerateGreaterThanOrEqualNumberOfHealthyHorses() {
		assertThat(this.service.getHealthyHorseCount()).isGreaterThanOrEqualTo(this.minHealthyHorseCount);
	}

	@Test
	public void givenHorseCountThenGenerateEqualNumberOfHorses() {
		assertThat(this.service.getHorseCount()).isEqualTo(this.horseCount);
	}

	@Test
	public void whenHorseRaceInitThenHorsesStartDistanceMustBeBetweenZeroAndMaxStartDistance() {
		this.service.getHorseRacerSnapshot().forEach(horse -> {
			assertThat(horse.getDistanceTravelled()).isLessThan(this.maxStartDistance);
			assertThat(horse.getDistanceTravelled()).isGreaterThan(0);
		});
	}

	@Test
	public void whenHorsesRunThenIncreaseTheirDistance() {
		List<Horse> horsePositions = this.service.getHorseRacerSnapshot();

		float distanceTravelled = horsePositions.get(0).getDistanceTravelled();

		this.service.runProgressive();

		List<Horse> newHorsePositions = this.service.getHorseRacerSnapshot();

		assertThat(newHorsePositions.get(0).getDistanceTravelled()).isGreaterThan(distanceTravelled);
	}

	@Test
	public void whenHorseRanThenReceiveEqualNumberOfMessages() {
		Consumer raceStateReportCallback = mock(Consumer.class);

		this.service.addRaceStateReportListener(raceStateReportCallback);
		this.service.runProgressive();

		verify(raceStateReportCallback, times(this.minHealthyHorseCount)).accept(any(String.class));
	}
}