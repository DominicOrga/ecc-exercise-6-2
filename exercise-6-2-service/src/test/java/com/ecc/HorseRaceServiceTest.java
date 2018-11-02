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

		this.service = new HorseRaceService(numberOfHorses, minHealthyHorses, maxStartDistance, trackDistance);
	}

	@Test
	public void givenMinNumberOfHealthyHorsesThenGenerateGreaterThanOrEqualNumberOfHealthyHorses() {
		assertThat(this.service.getHealthyHorseCount()).isGreaterThanOrEqualTo(this.minHealthyHorses);
	}

	@Test
	public void givenNumberOfHorsesThenGenerateEqualNumberOfHorses() {
		assertThat(this.service.getHorseCount()).isEqualTo(this.numberOfHorses);
	}

	@Test
	public void whenMinHealthyHorsesIsGreaterThanNumberOfHorsesWhenGenerateHorsesThenThrowException() {
		this.numberOfHorses = 10;
		this.minHealthyHorses = 11;

		Throwable thrown = catchThrowable(() -> {
			this.service = new HorseRaceService(numberOfHorses, minHealthyHorses, maxStartDistance, trackDistance);
		});

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
						  .hasMessage("Number of horses must be greater than or equal to the min number of healthy horses.");
	}

	@Test
	@Ignore
	public void whenMaxStartDistanceIsGreaterThanOrEqualToTrackDistanceThenThrowException() {

	}
}