package projectgroep.parkeergarage.runner;

import projectgroep.parkeergarage.SettingsRepository;
import projectgroep.parkeergarage.main.Simulator;

public class SimulatorRunner {
    public static void main(String[] args) {
        new Thread(() -> projectgroep.parkeergarage.fx.Simulator.launch()).start();

        Simulator simulator = new Simulator(SettingsRepository.loadSettings());
        simulator.getParkeerLogic().run();
    }
}