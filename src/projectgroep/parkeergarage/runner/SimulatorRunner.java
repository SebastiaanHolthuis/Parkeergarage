package projectgroep.parkeergarage.runner;

import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.main.Simulator;

public class SimulatorRunner {

	public static void main(String[] args) {
		Simulator simulator = new Simulator(new Settings()); // TODO: laden uit bestand
		simulator.getParkeerLogic().run();
	}
}