package com.ecc.service;

import com.ecc.model.Horse;

import java.util.List;
import java.util.ArrayList;

import java.util.stream.Stream;
import java.util.stream.Collectors;

public class HorseRaceService {

	List<Horse> horses;
	List<Horse> healthyHorseRacers;

	public HorseRaceService(
		int horseCount, int minHealthyHorseCount, float maxStartDistance, float trackDistance) {

		generateHorses(horseCount, minHealthyHorseCount);
	}

	public int getHorseCount() {
		return this.horses.size();
	}

	public int getHealthyHorseCount() {
		return this.healthyHorseRacers.size();
	}

	private void generateHorses(int horseCount, int minHealthyHorseCount) {

		this.healthyHorseRacers = Stream.generate(() -> new Horse(true))
										.limit(minHealthyHorseCount)
										.collect(Collectors.toList());

		List<Horse> randomHorses = Stream.generate(Horse::new)
				  		   				 .limit(horseCount - minHealthyHorseCount)
				  		   				 .collect(Collectors.toList());

	   	this.horses = Stream.concat(this.healthyHorseRacers.stream(), randomHorses.stream())
	   						.collect(Collectors.toList());
	}

}