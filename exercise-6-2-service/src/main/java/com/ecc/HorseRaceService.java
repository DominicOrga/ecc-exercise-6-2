package com.ecc.service;

import com.ecc.model.Horse;

import org.apache.commons.lang3.SerializationUtils;

import java.util.List;
import java.util.ArrayList;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Consumer;
import java.lang.IllegalArgumentException;

public class HorseRaceService {

	private float trackDistance;

	private List<Horse> horses;
	private List<Horse> healthyHorseRacers;
	private Horse horseWinner;

	private int finishPosition;

	private List<Consumer<String>> raceStateReportListeners = new ArrayList<>();

	public HorseRaceService(
		int horseCount, int minHealthyHorseCount, float maxStartDistance, float trackDistance) {

		if (horseCount < 1) {
			throw new IllegalArgumentException("Horse count should at least be one.");
		}

		if (minHealthyHorseCount < 0) {
			throw new IllegalArgumentException("Minimum healthy horse count must be a whole number.");
		}

		if (maxStartDistance < 0) {
			throw new IllegalArgumentException("Max start distance must be a positive float number.");
		}

		if (trackDistance <= 0) {
			throw new IllegalArgumentException("Track distance must be greater than 0.");
		}

		if (horseCount < minHealthyHorseCount) {
			throw new IllegalArgumentException("Number of horses must be greater than or equal " + 
											   "to the min number of healthy horses.");
		}

		if (trackDistance <= maxStartDistance) {
			throw new IllegalArgumentException("Track distance must be greater than max start distance.");
		}

		this.trackDistance = trackDistance;

		generateHorses(horseCount, minHealthyHorseCount);
		setupRacersInitialDistance(maxStartDistance);
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

	public List<Horse> getHorseRacerSnapshot() {
		return this.healthyHorseRacers.stream()
									  .map(SerializationUtils::clone)
									  .collect(Collectors.toList());
	}

	private void setupRacersInitialDistance(float maxStartDistance) {
		this.healthyHorseRacers.forEach(horse -> {
			horse.run((float) Math.random() * maxStartDistance);
		});
	}

	public boolean isRaceFinished() {
		for (Horse horse : this.healthyHorseRacers) {
			if (!isHorseFinished(horse)) {
				return false;
			} 
		}

		return true;
	}

	public boolean isHorseFinished(Horse horse) {
		return horse.getDistanceTravelled() >= this.trackDistance;
	}

	public Horse getHorseWinner() {
		return this.horseWinner;
	}

	public void runProgressive() {
		if (isRaceFinished()) {
			return;
		}

		this.healthyHorseRacers.forEach(horse -> {
			if (isHorseFinished(horse)) {
				return;
			}

			List<Horse> horseRacerSnapshot = getHorseRacerSnapshot();
			boolean isLastHorse = true;

			for (Horse horseSnapshot : getHorseRacerSnapshot()) {
				if (horseSnapshot.getDistanceTravelled() <= horse.getDistanceTravelled() &&
					!horseSnapshot.equals(horse)) {

					isLastHorse = false;
					break;
				}
			}

			float distance = 0;

			if (isLastHorse) {
				distance = (float) Math.random() * 20;
			}
			else {
				distance = (float) Math.random() * 10;
			}

			horse.run(distance);

			if (isHorseFinished(horse) && getHorseWinner() == null) {
				this.horseWinner = horse;
			}

			dispatchReportRaceState(horse, distance, isLastHorse, horseRacerSnapshot);
		});
	}

	public void runComplete() {
		while (!isRaceFinished()) {
			runProgressive();
		}
	}

	private void dispatchReportRaceState(
		final Horse horse, final float distanceTravelled, 
		final boolean isBoosted, List<Horse> horseRacersSnapshot) {

		StringBuilder sb = new StringBuilder();

		if (isBoosted) {
			sb.append(String.format("%s needs a boost. ", horse.getName()));
			sb.append("[");
			sb.append(horseRacersSnapshot.stream()
										 .map(horseSnapshot -> 
										     horseSnapshot.getName() + ": " + 
										     horseSnapshot.getDistanceTravelled())
										 .collect(Collectors.joining(", ")));
			sb.append("]\n");
		}

		if (isHorseFinished(horse)) {
			int newFinishPosition = ++finishPosition;

			sb.append(String.format(
				"%s has finished race in %d%s place.", 
				horse.getName(), 
				newFinishPosition, 
				newFinishPosition % 10 == 1 ? "st" :
					newFinishPosition % 10 == 2 ? "nd" : 
						newFinishPosition == 3 ? "rd" : "th"));

			if (newFinishPosition == 1) {
				sb.append(String.format(" [Warcry: %s]", horse.getWarcry()));
			}
		}
		else {
			sb.append(String.format("%s ran %,.2f units. %,.2f units left.", 
						horse.getName(), 
						distanceTravelled, 
						this.trackDistance - horse.getDistanceTravelled()));			
		}

		for (Consumer<String> listener : this.raceStateReportListeners) {
			listener.accept(sb.toString());
		}
	}

	public void addRaceStateReportListener(Consumer<String> listener) {
		this.raceStateReportListeners.add(listener);
	}

	public void removeRaceStateReportListener(Consumer<String> listener) {
		this.raceStateReportListeners.remove(listener);
	}
}