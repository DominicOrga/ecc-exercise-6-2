package com.ecc.service;

import java.util.List;
import java.util.ArrayList;

import java.util.stream.Stream;
import java.util.stream.Collectors;

public class HorseRaceService {

	List<Horse> horses;
	List<Horse> healthyHorseRacers;

	public HorseRaceService(
		int horseCount, int minHealthyHorse, float maxStartDistance, float trackDistance) {

		generateHorses(horseCount, minHealthyHorse);
		pickhealthyHorseRacers(minHealthyHorse);
	}

	private void generateHorses(horseCount, minHealthyHorse) {
		horses = Stream.generate(Horse::new)
			  		   .limit(horseCount)
			  		   .collect(Collectors.toList());
	}

}