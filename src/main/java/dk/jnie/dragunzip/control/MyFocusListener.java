package dk.jnie.dragunzip.control;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JTextField;

import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.model.Property;
import dk.jnie.dragunzip.view.ComponentID;

public class MyFocusListener implements FocusListener {

	private Logger logger = Logger.getLogger("dk.jnie.dragunzip.control");
	
	private SortedProperties props = null;
	
	private ComponentID componentID = null;

	public MyFocusListener(ComponentID id) {
		componentID = id;
	}

	@Override
	public void focusGained(FocusEvent event) {
	}

	@Override
	public void focusLost(FocusEvent event) {
		Object source = event.getSource();
		JTextField jTxtField = null;
		
		JButton actionSource = null;
		if (source instanceof JTextField) {
			jTxtField = (JTextField) source;
		}
		if (componentID != null) {
			if (componentID == ComponentID.TXTF_FOLDER) {
				props.setProperty(Property.MONITORFOLDER, jTxtField.getText());
			}
			if (componentID == ComponentID.TXTF_TIMER) {
				props.setProperty(Property.TIMER, jTxtField.getText());
			}
		} else {
			logger.info("Need a ComponentID, otherwise I cannot identify the correct source component");
		}

	}

}
