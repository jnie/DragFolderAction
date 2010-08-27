package dk.jnie.dragunzip.monitor;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.initializer.Property;

public class Monitor implements Runnable {

	private Logger logger = Logger.getLogger("dk.jnie.dragunzip");
	private static Monitor monitorInstance = new Monitor();

	private static SortedProperties props;
	private volatile boolean doRun = true;
	private static String argFolder;

	static Thread t;
	private MonitorActionController mac; 
	private static ArrayList<File> existingFiles = new ArrayList<File>();
	
	/**
	 * non instantiable
	 */
	private Monitor() {
		mac = new MonitorActionController();
	}

	public static Monitor getInstance() {
		if (props == null) {
			props = Property.getProps();
		}
		if (monitorInstance != null) {
			return monitorInstance;
		} else {
			return new Monitor();
		}
	}

	@Override
	public void run() {
		// Thread thisThread = Thread.currentThread();
		Date startTime = new Date();
		if (props == null || !doRun) {
			return;
		}
		String folder = argFolder;
		//This property is either set from property or from java start main method argument.
		if ("".equals(folder)) {
			folder =  props.getProperty(Property.MONITORFOLDER, argFolder);
		}
		if (folder == null) {
			logger.severe("I need a folder name to monitor!");
			return; //I need a Folder
		}
		

		File folderObj = new File(folder);
		boolean isDirectory = folderObj.isDirectory();
		
		while (doRun) {

			if(isDirectory) {
				File[] fileList = folderObj.listFiles();
				File[] newFileList = checkForNewFiles(fileList);
				logger.info("No. of new files: "+ newFileList.length);
				for (File file: newFileList) {
					logger.info("Trying to route... file: "+ file.getAbsolutePath());
					mac.routeAction(file);
				}
			} else {
				//If not a directory create one.
				folderObj.mkdir();
			}
			
			try {
				// See if folder is setup to be cleared
				clearFolder(folder);
				String sTimer = props.getProperty(Property.TIMER, "5"); //5sek
				int timeToSleep = (Integer.parseInt(sTimer))*1000;// times 1000 millis to sec.
				Thread.sleep(timeToSleep);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt(); // very important
				break;
			}
			
		}
	}

	
	private File[] checkForNewFiles(File[] fileList) {
		ArrayList<File> newFileList = new ArrayList<File>();
		boolean exist = false;
		for (File file:fileList) {
			newFileList.add(file);
		}
		if (existingFiles.isEmpty()) {
			existingFiles = newFileList;
		} else {
			for (File file: fileList) {
				if(existingFiles.contains(file)) {
					newFileList.remove(file);
				} else {
					existingFiles.add(file);
				}
			}

			File[] files = new File[newFileList.size()];
			newFileList.toArray(files);
			return files;			
		}
		return fileList;
	}

	/**
	 * Check the property CLEARMONITORFOLDER for true|false and clear the folder
	 * accordingly
	 * @param folder
	 */
	private void clearFolder(String folder) {
		if (Boolean.parseBoolean(props.getProperty(Property.CLEARMONITORFOLDER))) {
			File f = new File(props.getProperty(Property.MONITORFOLDER));
			int currentFilecount = f.listFiles().length;
			if (currentFilecount > 0) {
				File[] existingFiles = f.listFiles();
				for (File file: existingFiles) {
					logger.info("The file: " + file.getAbsolutePath() + " is getting deleted? " + file.delete());
				}
			}
		}
		
	}

	public boolean isDoRun() {
		return doRun;
	}

	public void setFolder(String folder) {
		argFolder = folder;
	}
}
