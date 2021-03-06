package dk.jnie.dragunzip;

import java.util.logging.Level;
import java.util.logging.Logger;

import dk.csc.util.prompt.ConsoleReader;
import dk.jnie.dragunzip.control.LogControl;
import dk.jnie.dragunzip.monitor.Monitor;

public class MonitorRunner {

	static Logger logger = Logger.getLogger("dk.jnie.dragunzip");

	static LogControl lc = new LogControl();
	
	private static void usage() {
		System.out.println("Need at least 1 argument. \n");
		System.out.println("MonitorRunner $folder$");
	}

	/**
	 * Start Thread Monitor, and start listening for keystrokes, s = stop
	 * 
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String folder = "";
		String keyStroke;
		if (args.length < 1) {
			MonitorRunner.usage();
		} else {
			folder = args[0];
		}
		
		Monitor mon = Monitor.getInstance();
		mon.setFolder(folder);
		Thread monitor = new Thread(mon, "Monitor");
		monitor.start();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			logger.fine("Could not sleep thread");
			if (logger.isLoggable(Level.ALL)) {
				e.printStackTrace();
			}
		}
//		MonitorConsole monCon = new MonitorConsole(monitor);
//		Thread monitorConsole = new Thread(monCon, "MonitorConsole");
//		monitorConsole.start();
//		
		while (true) {
			ConsoleReader cr = new ConsoleReader();
			keyStroke = cr.getInput("Key 's' = stop monitoring.");
			if ("s".equals(keyStroke)) {
				monitor.interrupt();
				break;
			}
		}
	}

}
