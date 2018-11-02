package com.ecc.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import java.lang.IllegalArgumentException;

public class HorseRaceServiceTest {
	
	private int numberOfHorses;
	private int minHealthyHorses;
	private float maxStartDistance;
	private float trackDistance;
	private HorseRaceService service;

	@Before
	public void setDefaultConstructorArgs() {
		this.numberOfHorses = 100;
		this.minHealthyHorses = 10;
		this.maxStartDistance = 10f;
		this.trackDistance = 100f;

		this.service = new HorseRaceService(
			numberOfHorses, minHealthyHorses, maxStartDistance, trackDistance);
	}

	@Test
	public void givenMinHealthyHorseCountThenGenerateGreaterThanOrEqualNumberOfHealthyHorses() {
		assertThat(this.service.getHealthyHorseCount()).isGreaterThanOrEqualTo(this.minHealthyHorses);
	}

	@Test
	public void givenHorseCountThenGenerateEqualNumberOfHorses() {
		assertThat(this.service.getHorseCount()).isEqualTo(this.numberOfHorses);
	}

	@Test
	public void whenMinHealthyHorseCountIsGreaterThanHorseCountWhenGenerateHorsesThenThrowException() {
		this.numberOfHorses = 10;
		this.minHealthyHorses = 11;

		Throwable thrown = catchThrowable(() -> {
			this.service = new HorseRaceService(
				this.numberOfHorses, this.minHealthyHorses, this.maxStartDistance, this.trackDistance);
		});

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
						  .hasMessage("Number of horses must be greater than or equal to the min " + 
						  			  "number of healthy horses.");
	}

	@Test
	public void whenMinHealthyHorseCountIsNegativeThenThrowException() {
		this.minHealthyHorses = -1;

		Throwable thrown = catchThrowable(() -> {
			this.service = new HorseRaceService(
				this.numberOfHorses, this.minHealthyHorses, this.maxStartDistance, this.trackDistance);
		});

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
						  .hasMessage("Minimum healthy horse count must be a whole number.");	
	}

	@Test
	@Ignore
	public void whenHorseCountIsNegativeThenThrowException() {

	}

	@Test
	@Ignore
	public void whenMaxStartDistanceIsNegativeThenThrowException() {

	}

	@Test
	@Ignore
	public void whenTrackDistanceIsNegativeThenThrowException() {

	}

	@Test
	@Ignore
	public void whenMaxStartDistanceIsGreaterThanOrEqualToTrackDistanceThenThrowException() {

	}
}