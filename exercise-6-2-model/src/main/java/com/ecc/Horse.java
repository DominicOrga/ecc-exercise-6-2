package com.ecc.model;

public class Horse {
	private boolean isHealthy;

	public Horse() {
		this(Math.random() > 0.5);
	}

	public Horse(boolean isHealthy) {
		this.isHealthy = isHealthy;
	}

	public boolean isHealthy() {
		return this.isHealthy;
	}
}