package projectgroep.parkeergarage.runner;

import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.logic.Settings;
import projectgroep.parkeergarage.main.Simulator;

public class SimulatorRunner {
    public static void main(String[] args) {
    	Simulator simulator = new Simulator(SettingsRepository.loadSettings());
        simulator.getParkeerLogic().run();
    }
    
	/**
	 * Kills the simulator and restarts it with the new settings
	 */
    public static void restart() {
    	Thread currenThread = Thread.currentThread();
    	Thread newThread = new Thread(() ->  {
    		currenThread.interrupt();
    		main(null);
    	});
    	
    	newThread.start();
    }

}