package projectgroep.parkeergarage.runner;

import projectgroep.parkeergarage.main.Simulator;

public class SimulatorRunner {

	public static void main(String[] args) {
		Simulator simulator = new Simulator(3, 6, 30);
		simulator.getParkeerLogic().run();
	}
}