package projectgroep.parkeergarage;

public class Parkeergarage {

	private Simulator simulator = new Simulator();
	
	public static void main(String[] args) {
		Parkeergarage parkeergarage = new Parkeergarage();
	}
	
	public Parkeergarage() {
		simulator.run();
	}
	
}