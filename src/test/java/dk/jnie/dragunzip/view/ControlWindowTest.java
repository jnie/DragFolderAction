package dk.jnie.dragunzip.view;

import junit.framework.TestCase;


public class ControlWindowTest extends TestCase {

	public final static String TEST_ALL_TEST_TYPE = "UNIT";
	
	private ControlWindow cw; 
	
	public void setUp() {
		cw = new ControlWindow("Junit Test");
	}
	
	public void testTxtFieldsPresent() {
		assertNotNull(cw.jTxtFolderMonitorName);
		assertNotNull(cw.jTxtTimer);
	}
	
	public void testLabelsPresent() {
		assertNotNull(cw.lClearFolder);
		assertNotNull(cw.lMonitor);
		assertNotNull(cw.lTimer);
	}
	
	public void testButtonsPresent() {
		assertNotNull(cw.jbnStart);
		assertNotNull(cw.jbnStop);
	}
	
	public void testInitialContext() {
		//Labels
		assertEquals("Monitor this folder",cw.lMonitor.getText());
		assertEquals("Clear folder", cw.lClearFolder.getText());
		assertEquals("Time in seconds", cw.lTimer.getText());
		
		//Buttons
		assertEquals("Start", cw.jbnStart.getText());
		assertEquals("Stop", cw.jbnStop.getText());
		
		//TxtFields
		assertNotSame("", cw.jTxtFolderMonitorName.getText());
		assertNotSame("", cw.jTxtTimer.getText());
	}
}
