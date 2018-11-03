package com.ecc.model;

import java.io.Serializable;

public class Horse implements Serializable {
	private static int instanceCount = 0; 

	private boolean isHealthy;
	private float distanceTravelled;
	private int horseNumber;

	public Horse() {
		this(Math.random() > 0.5);
	}

	public Horse(boolean isHealthy) {
		this.isHealthy = isHealthy;
		horseNumber = ++instanceCount;
	}

	public boolean isHealthy() {
		return this.isHealthy;
	}

	public int getHorseNumber() {
		return this.horseNumber;
	}

	public void run(float distance) {
		this.distanceTravelled += distance;
	}

	public float getDistanceTravelled() {
		return this.distanceTravelled;
	}

	@Override
	public boolean equals(Object o1) {
		if (o1 == this) {
			return true;
		}

		if (!(o1 instanceof Horse)) {
			return false;
		}

		Horse horse1 = (Horse) o1;
		return this.getHorseNumber() == horse1.getHorseNumber();
	}
}