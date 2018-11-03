package com.ecc.model;

import static org.assertj.core.api.Assertions.*;

import org.apache.commons.lang3.SerializationUtils;

import org.junit.Test;

public class HorseTest {

	@Test
	public void whenHorseIsClonedThenHorseMustMatch() {
		Horse horse = new Horse();
		Horse horseClone = SerializationUtils.clone(horse);

		assertThat(horse.getHorseNumber()).isEqualTo(horseClone.getHorseNumber());
	}

}