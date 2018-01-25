package projectgroep.parkeergarage.runner;

import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.main.Simulator;

public class SimulatorRunner {
    public static void main(String[] args) {
    	Simulator simulator = new Simulator(SettingsRepository.loadSettings());
        simulator.getParkeerLogic().start();
    }

}