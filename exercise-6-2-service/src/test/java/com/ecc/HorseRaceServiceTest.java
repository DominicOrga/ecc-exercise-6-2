package com.ecc.service;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

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
	public void givenAMinNumberOfHealthyHorsesThenGenerateGreaterThanOrEqualNumberOfHealthyHorses() {
		assertThat(this.service.getHealthyHorseCount()).isGreaterThanOrEqualTo(this.minHealthyHorses);
	}

	@Test
	@Ignore
	public void givenminHealthyHorsesIsGreaterThanNumberOfHorsesWhenGenerateHorsesThenThrowException() {

	}
}