package dk.jnie.dragunzip;

import dk.csc.util.prompt.ConsoleReader;
import dk.jnie.dragunzip.model.Property;

public class MonitorConsole implements Runnable {

	Thread t = null;
	public MonitorConsole(Thread t) {
		this.t = t;
	}
	
	@Override
	public void run() {
		String keyStroke;
		while (true) {
			ConsoleReader cr = new ConsoleReader();
			keyStroke = cr.getInput("Key 's' = stop monitoring.");
			if ("s".equals(keyStroke)) {
				t.interrupt();
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt(); // very important
				break;
			}

		}
	}

}
