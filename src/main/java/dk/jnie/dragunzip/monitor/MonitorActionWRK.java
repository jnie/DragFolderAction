package dk.jnie.dragunzip.monitor;

import java.io.File;
import java.util.logging.Logger;

public class MonitorActionWRK implements MonitorActionInterface {
	
	private String NL = System.getProperty("line.separator");
	private static final String ENCODING = "ISO-8859-1";
	private File file;
	
	@Override
	public void doAction() {
		logger.entering("MonitorActionTxt", "doAction(File)");
		for (int i=0;i < 30;i++) { //20 second sleep
			try {
				System.out.print(" Count " + i);
				Thread.currentThread();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void setFile(File file) {
		this.file = file; 
		
	}

	@Override
	public void run() {
		doAction();
		
	}

}
