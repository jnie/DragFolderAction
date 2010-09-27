package dk.jnie.dragunzip.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import dk.csc.util.file.FileUtil;
import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.monitor.Monitor;

public class Property {
	
	private static Logger logger = Logger.getLogger("dk.jnie.dragunzip.property");
	private static final String PROPERTYFILE = "src/main/resources/monitor.properties";
	public static final String MONITORFOLDER = "monitorfolder";
	public static final String TIMER = "timer";
	public static final String OUTPUTFOLDER = "tmpfolder";
	public static final String CLEARMONITORFOLDER = "clearmonitorfolder";
	private static SortedProperties props = null;
	private Property property = new Property();
	
	private Property() {}
	
	public static SortedProperties getProps() {
		if(props == null) {
			initialize();
		}
		return props;
	}
	
	private static void initialize() {
		FileInputStream fis;
		try {
			// StreamSource ss = FileUtil.getStreamSource(PROPERTYFILE);
			URL url = FileUtil.getURL(PROPERTYFILE);
			// fis = new FileInputStream(PROPERTYFILE);
			props = new SortedProperties();
			props.load(url.openStream());
		} catch (FileNotFoundException e) {
			logger.warning("Could not find the file: " + PROPERTYFILE);
			if (logger.isLoggable(Level.ALL)) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			logger.warning(e.getMessage());
			if (logger.isLoggable(Level.ALL)) {
				e.printStackTrace();
			}
		}
	}
	

	public String toString() {
		if (props != null) {
			return props.toString();
		} else {
			return "No properties.";
		}
	}
}
