package com.ecc.model;

import java.io.Serializable;

public class Horse implements Serializable {
	private boolean isHealthy;
	private float distanceTravelled;

	public Horse() {
		this(Math.random() > 0.5);
	}

	public Horse(boolean isHealthy) {
		this.isHealthy = isHealthy;
	}

	public boolean isHealthy() {
		return this.isHealthy;
	}

	public void run(float distance) {
		this.distanceTravelled += distance;
	}

	public float getDistanceTravelled() {
		return this.distanceTravelled;
	}
}