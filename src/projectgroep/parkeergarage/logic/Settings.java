package projectgroep.parkeergarage.logic;


/**
 * Dit zou een singleton of een data object kunnen worden.
 * Hij wordt straks gebruikt door de ParkeerLogic en SettingsView.
 * @author reinvdwoerd
 */
public class Settings {
	public Settings() {
		// TODO 
	}
	
	static Settings getSavedSettings () {
		return new Settings();
	}
}
