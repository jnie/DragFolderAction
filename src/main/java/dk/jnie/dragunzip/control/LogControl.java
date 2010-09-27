package dk.jnie.dragunzip.control;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogControl {
	
	private static Logger logger = Logger.getLogger("dk.jnie.dragunzip");
	
	public LogControl() {
		prepareLog();
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
