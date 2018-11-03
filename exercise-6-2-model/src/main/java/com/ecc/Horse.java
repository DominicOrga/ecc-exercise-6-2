package com.ecc.model;

import java.util.Random;
import java.io.Serializable;
import org.apache.commons.lang3.RandomStringUtils;

public class Horse implements Serializable {
	private static int instanceCount = 0; 

	private static String[] warcries;

	static {
		warcries = new String[]{ "Yee ha!", "Iiiiieeeee!", "EZ PZ", "Good game well played", null };	
	}

	private boolean isHealthy;
	private float distanceTravelled;
	private int horseNumber;
	private String name;
	private String warcry;

	public Horse() {
		this(Math.random() > 0.5);
	}

	public Horse(boolean isHealthy) {
		this.isHealthy = isHealthy;
		horseNumber = ++instanceCount;

		this.warcry = warcries[new Random().nextInt(warcries.length)];
		this.name = RandomStringUtils.random(5, true, false);
	}

	public boolean isHealthy() {
		return this.isHealthy;
	}

	public int getHorseNumber() {
		return this.horseNumber;
	}

	public String getName() {
		return this.name;
	}

	public String getWarcry() {
		return this.warcry;
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