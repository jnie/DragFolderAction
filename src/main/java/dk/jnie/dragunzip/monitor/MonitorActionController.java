package dk.jnie.dragunzip.monitor;

import java.io.File;
import java.util.logging.Logger;

public class MonitorActionController {

	static Logger logger = Logger.getLogger("dk.jnie.dragunzip");
	private boolean found = false;

	enum FileType {
		ZIP, TXT, XML
	};

	public MonitorActionController() {
	}

	public void routeAction(File file) {
		logger.info("Entering...");
		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		for (FileType type : FileType.values()) {
			logger.info("fileType = " + fileType + ", enum type name = " + type.name());
			if (fileType.equalsIgnoreCase(type.name())) {
				logger.info("Calling routeIt(File,FileType)");
				routeIt(file, type);
			}
		}
		if (!found) {
			logger.warning("Does not support filtype with extension: "
					+ fileType);
		}
		logger.exiting("MonitorActionComntroller", "routeAction");
	}

	private void routeIt(File file, FileType type) {
		logger.fine("Found an action for file type: " + type.name());
		MonitorActionInterface ma = null;

		switch (type) {
		case ZIP:
			ma = new MonitorActionZip();
			found = true;
			break;
		case TXT:
			ma = new MonitorActionTxt();
			found = true;
			break;
		case XML:
			ma = new MonitorActionXML();
			found = true;
			break;
		default:
			break;
		}
		if (ma != null) {
			ma.doAction(file.getAbsoluteFile());
		}
	}

}
