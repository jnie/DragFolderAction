package dk.jnie.dragunzip.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.model.Property;
import dk.jnie.dragunzip.monitor.Monitor;
import dk.jnie.dragunzip.view.ComponentID;
import dk.jnie.dragunzip.view.ControlWindow;
import dk.jnie.dragunzip.view.ControlWindowMenuBar;
import dk.jnie.dragunzip.view.MessagePopup;

public class MyActionListener implements ActionListener {

	private Logger logger = Logger.getLogger("dk.jnie.dragunzip.control");
	
	private SortedProperties props = null;
	private ResourceBundle rb = null;
	
	private ComponentID componentID = null;

	public MyActionListener(ComponentID id) {
		init();
		componentID = id;
	}
	
	public MyActionListener() {
		init();
	}
	
	/**
	 * Initialize variables
	 */
	private void init() {
		props = Property.getProps();
		
		Locale locale = Locale.getDefault();
		locale = new Locale(props.getProperty("resourcebundle"));
	    rb = ResourceBundle.getBundle("language", locale);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		JButton actionSource = null;
		if (source instanceof JButton) {
			buttonActionPerformed((JButton) source);
		} else if (source instanceof JTextField) {
			textFieldActionPerformed((JTextField) source);
		} 
		else if (source instanceof JMenuItem) {
			menuActionPerformed((JMenuItem) source);
		}
		
//		if (source == ControlWindow.getJbnStop()) {
//			try {
//				Thread.sleep(1000000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		if (source == ControlWindow.getJbnStart()) {
//			monitor.start();
//		}

	}
	
	
	private void menuActionPerformed(JMenuItem source) {
		if (componentID != null) {
			switch(componentID) { 
				case MENU_CONFIG_EDIT:
					MessagePopup.pop("This needs some work");
					break;
				case MENU_HELP_ABOUT:
					MessagePopup.pop(rb.getString("about"));
					break;
				case MENU_CONFIG_EXIT:
					EventAction.exitProgram(rb.getString("exit_question"));
					break;
				case MENU_CONFIG_STOP:
					EventAction.stopMonitor();
					break;
				case MENU_CONFIG_START:
					EventAction.startMonitor();
					break;
				case MENU_VIEW_UK:
					EventAction.changeLanguage(ComponentID.MENU_VIEW_UK);
					source.getParent().getParent().repaint();
					break;
				case MENU_VIEW_DK:
					EventAction.changeLanguage(ComponentID.MENU_VIEW_DK);
					break;
				default:
			}
		} else {
			logger.info("Need a ComponentID, otherwise I cannot identify the correct source component");
		}
		
	}

	private void textFieldActionPerformed(JTextField actionSource) {
		if (componentID != null) {
			if (componentID == ComponentID.TXTF_FOLDER) {
				props.setProperty(Property.MONITORFOLDER, actionSource.getText());
			}
		} else {
			logger.info("Need a ComponentID, otherwise I cannot identify the correct source component");
		}
		
	}

	private void buttonActionPerformed(JButton actionSource) {
		String sourceName = "";
		//Using the Component_ID
		if (componentID != null) {
			if (componentID == ComponentID.B_STOP) {
				EventAction.stopMonitor();
			}
			if (componentID == ComponentID.B_START) {
				EventAction.startMonitor();
			}

		} else {
			logger.info("Need a ComponentID, otherwise I cannot identify the correct source component");
		}
//		if (actionSource != null) {
//			sourceName = actionSource.getText();
//			logger.info("Source name pressed: " + sourceName);
//		}
//		
//		if (!"".equals(sourceName)) {
//			if (rb.getString("b_stop").equals(sourceName)) {
//				mon.setDoRun(false);
//			}
//			if (rb.getString("b_start").equals(sourceName)) {
//				if (!mon.isDoRun()) {
//					mon.setDoRun(true);
//					Thread monitor = new Thread(mon, "Monitor");
//					monitor.start();
//				}
//			}
//
//		}
		
	}

}
