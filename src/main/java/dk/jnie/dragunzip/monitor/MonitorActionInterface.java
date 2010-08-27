package dk.jnie.dragunzip.monitor;

import java.io.File;
import java.util.logging.Logger;

public interface MonitorActionInterface extends Runnable {

	static Logger logger = Logger.getLogger("dk.jnie.dragunzip");
	
	public void doAction();
	
	public void setFile(File file);
}
