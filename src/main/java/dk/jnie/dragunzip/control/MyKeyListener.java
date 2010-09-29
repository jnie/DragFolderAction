package dk.jnie.dragunzip.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.model.Property;
import dk.jnie.dragunzip.monitor.Monitor;
import dk.jnie.dragunzip.view.MessagePopup;

public class MyKeyListener implements KeyListener {

	private Logger logger = Logger.getLogger("dk.jnie.dragunzip.control");
	
	private SortedProperties props = null;
	private ResourceBundle rb = null;
	private Monitor mon = Monitor.getInstance();

	public MyKeyListener() {
		props = Property.getProps();
		
		Locale locale = Locale.getDefault();
		locale = new Locale(props.getProperty("resourcebundle"));
	    rb = ResourceBundle.getBundle("language", locale);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        boolean altKey = false;
        String keyTyped = "";
        if (id == KeyEvent.KEY_TYPED) {
            keyTyped = "" + e.getKeyChar();
        }
        
        logger.fine("Key typed: " + keyTyped);
        
        int modifiersEx = e.getModifiersEx();
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
        if ("Alt".equals(tmpString)) {
       		altKey = true;
        }

        logger.fine("Modifier key? " + tmpString);
        if (altKey) {
        	altKeyPressed(keyTyped);
        }
        
	}

	/**
	 * Called when [Alt] is pressed 
	 * @param keyTyped
	 */
	private void altKeyPressed(String keyTyped) {
		if ("".equals(keyTyped)) {
			return;
		}
    	if ("s".equalsIgnoreCase(keyTyped)) {
    		EventAction.startMonitor();
    	}
    	if ("p".equalsIgnoreCase(keyTyped)) {
    		EventAction.stopMonitor();
    	}
    	if ("x".equalsIgnoreCase(keyTyped)) {
    		EventAction.exitProgram(rb.getString("exit_question"));
    	}
    	if ("e".equalsIgnoreCase(keyTyped)) {
    		EventAction.editProperties();
    	}
	}
	
	public ResourceBundle getResourceBundle() {
		return rb;
	}
	
}
