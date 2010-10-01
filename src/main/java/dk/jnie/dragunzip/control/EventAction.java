package dk.jnie.dragunzip.control;

import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.model.Property;
import dk.jnie.dragunzip.monitor.Monitor;
import dk.jnie.dragunzip.view.ComponentID;
import dk.jnie.dragunzip.view.MessagePopup;

public class EventAction {
	private static Monitor mon = Monitor.getInstance();
	
	private EventAction() {
	}
	
	public static void stopMonitor() {
		mon.setDoRun(false);
	}
	
	public static void startMonitor() {
		if (!mon.isDoRun()) {
			mon.setDoRun(true);
			Thread monitor = new Thread(mon, "Monitor");
			monitor.start();
		}
	}
	
	public static void exitProgram(String question) {
		int exitCode = MessagePopup.popQuestion(question);
		if (exitCode == 0) {
			System.exit(0);
		}
	}

	public static void editProperties() {
		// TODO Auto-generated method stub
		
	}

	public static void changeLanguage(ComponentID componentId) {
		SortedProperties sp = Property.getProps();
		switch(componentId) {
		case MENU_VIEW_DK:
			sp.setProperty(Property.RESOURCEBUNDLE, "da_DK");
			break;
		case MENU_VIEW_UK:
			sp.setProperty(Property.RESOURCEBUNDLE, "en_UK");
			break;
		default:
			sp.setProperty(Property.RESOURCEBUNDLE, "en_UK");
		}
	}
}
