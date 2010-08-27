package dk.jnie.dragunzip;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import dk.csc.util.prompt.ConsoleReader;
import dk.jnie.dragunzip.monitor.Monitor;

public class MonitorRunner {

	static Logger logger = Logger.getLogger("dk.jnie.dragunzip");

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

		prepareLog();
		
		Monitor mon = Monitor.getInstance();
		mon.setFolder(folder);
		Thread monitor = new Thread(mon, "Monitor");
		monitor.start();

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
	
	private static void prepareLog() {
		//Create if log folder does not exist.
		File logFolder = new File("log");
		
		if(!logFolder.exists()) {
			logFolder.mkdir();
		}
		
		System.setProperty("java.util.logging.config.file",	"src/main/resources/logging.properties");
		// This overwrites the current logging configuration
		// to the one in the configuration file.
		try {
			LogManager.getLogManager().readConfiguration();
		} catch (IOException ex) {
			logger.log(Level.WARNING, "Problem loading the logging "
					+ "configuration file", ex);
		}

	}
}
