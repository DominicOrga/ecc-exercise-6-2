package com.ecc.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import java.lang.IllegalArgumentException;

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
	public void givenMinHealthyHorseCountThenGenerateGreaterThanOrEqualNumberOfHealthyHorses() {
		assertThat(this.service.getHealthyHorseCount()).isGreaterThanOrEqualTo(this.minHealthyHorseCount);
	}

	@Test
	public void givenHorseCountThenGenerateEqualNumberOfHorses() {
		assertThat(this.service.getHorseCount()).isEqualTo(this.horseCount);
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