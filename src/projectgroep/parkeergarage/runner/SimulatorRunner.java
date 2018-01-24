package projectgroep.parkeergarage.runner;

import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.main.Simulator;

public class SimulatorRunner {
    public static void main(String[] args) {
    	@SuppressWarnings("unused")
		Simulator simulator = new Simulator(SettingsRepository.loadSettings());
        
    }

}