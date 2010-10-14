package dk.jnie.dragunzip.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import dk.csc.util.file.FileUtil;
import dk.csc.util.properties.SortedProperties;

public class Property {
	
	private static Logger logger = Logger.getLogger("dk.jnie.dragunzip.property");
	private static final String PROPERTYFILE = "src/main/resources/monitor.properties";
	public static String MONITORFOLDER = "monitorfolder";
	public static String TIMER = "timer";
	public static final String OUTPUTFOLDER = "tmpfolder";
	public static String CLEARMONITORFOLDER = "clearmonitorfolder";
	public static String RESOURCEBUNDLE = "resourcebundle";
	public static final String LAF = "laf";
	private static SortedProperties props = null;
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	
	private static Property property = new Property();
	
	private Property() {
		initialize();
	}
	
	public static Property getInstance() {
		if (property == null) {
			property = new Property();
			
		}
		return property;
	}
	
	public static SortedProperties getProps() {
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

	public void setMonitorfolder(String monitorfolder) {
		if(monitorfolder == null)
	    {
	      throw new NullPointerException("No monitorfolder provided.");
	    }
	    if(!monitorfolder.equals(props.getProperty(MONITORFOLDER)))
	    {
	    	String oldMonitorfolder = props.getProperty(MONITORFOLDER);
			props.setProperty(MONITORFOLDER,monitorfolder);
		
		    pcs.firePropertyChange(TIMER,
		    		oldMonitorfolder,
		    		  monitorfolder);
	    

	    }
	}

	public String getTimer() {
		return props.getProperty(TIMER);
	}

	public void setTimer(String timer) {
		if (timer==null) {
			throw new NullPointerException("No timer provided.");
		}
		if (!timer.equals(props.getProperty(TIMER))) {
			String oldTimer = props.getProperty(TIMER);
			props.setProperty(TIMER,timer);
		
		    pcs.firePropertyChange(TIMER,
		    		oldTimer,
		    		  timer);
			
		}
	}

	public String getClearmonitorfolder() {
		return CLEARMONITORFOLDER;
	}

	public void setClearmonitorfolder(String clearmonitorfolder) {
		CLEARMONITORFOLDER = clearmonitorfolder;
		if (clearmonitorfolder == null) {
			throw new NullPointerException("No timer provided.");
		}
		if (!clearmonitorfolder.equals(props.getProperty(CLEARMONITORFOLDER))) {
			String oldClearmonitorfolder = props.getProperty(CLEARMONITORFOLDER);
			props.setProperty(CLEARMONITORFOLDER,clearmonitorfolder);
		
		    pcs.firePropertyChange(CLEARMONITORFOLDER,
		    		oldClearmonitorfolder,
		    		clearmonitorfolder);
			
		}
	}

	public static String getResourcebundle() {
		return props.getProperty(RESOURCEBUNDLE);
	}

	public void setResourcebundle(String resourcebundle) {
		if (resourcebundle==null) {
			throw new NullPointerException("No resourcebundle provided.");
		}
		if (!resourcebundle.equals(props.getProperty(RESOURCEBUNDLE))) {
			String oldResourcebundle = props.getProperty(RESOURCEBUNDLE);
			props.setProperty(RESOURCEBUNDLE,resourcebundle);
		
		    pcs.firePropertyChange(RESOURCEBUNDLE,
		    		oldResourcebundle,
		    		resourcebundle);
			
		}
	}

	 /**Add a property change listener for a specific property.
	  @param propertyName The name of the property to listen on.
	  @param listener The <code>PropertyChangeListener</code>
	      to be added.
	  */
	  public void addPropertyChangeListener(final String propertyName,
	      final PropertyChangeListener listener)
	  {
	    pcs.addPropertyChangeListener(propertyName, listener);
	  }

	  /**Remove a property change listener for a specific property.
	  @param propertyName The name of the property that was listened on.
	  @param listener The <code>PropertyChangeListener</code>
	      to be removed
	  */
	  public void removePropertyChangeListener(final String propertyName,
	      final PropertyChangeListener listener)
	  {
	    pcs.removePropertyChangeListener(propertyName, listener);
	  }

	public String toString() {
		if (props != null) {
			return props.toString();
		} else {
			return "No properties.";
		}
	}
}
