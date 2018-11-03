package com.ecc.app;

import com.ecc.service.HorseRaceService;

public class App {
	public static void main(String[] args) {
		new App();	
	}

	public App() {
		int horseCount = 100;
		int healthyHorseRacerCount = 10;
		float maxStartDistance = 10;
		float trackDistance = 100; 

		HorseRaceService horseRaceService = 
			new HorseRaceService(horseCount, healthyHorseRacerCount, maxStartDistance, trackDistance);

		horseRaceService.addRaceStateReportListener(this::onRaceStateReportCallback);
		horseRaceService.runComplete();
	}

	public void onRaceStateReportCallback(String report) {
		System.out.println(report);
	}
}