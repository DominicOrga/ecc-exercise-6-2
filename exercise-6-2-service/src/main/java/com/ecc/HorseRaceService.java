package com.ecc.service;

import com.ecc.model.Horse;

import java.util.List;
import java.util.ArrayList;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.lang.IllegalArgumentException;

public class HorseRaceService {

	List<Horse> horses;
	List<Horse> healthyHorseRacers;

	public HorseRaceService(
		int horseCount, int minHealthyHorseCount, float maxStartDistance, float trackDistance) {

		if (horseCount < 1) {
			throw new IllegalArgumentException("Horse count should at least be one.");
		}

		if (minHealthyHorseCount < 0) {
			throw new IllegalArgumentException("Minimum healthy horse count must be a whole number.");
		}

		if (horseCount < minHealthyHorseCount) {
			throw new IllegalArgumentException("Number of horses must be greater than or equal " + 
											   "to the min number of healthy horses.");
		}

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