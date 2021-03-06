package dk.jnie.dragunzip.monitor;

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import dk.jnie.dragunzip.view.MessagePopup;

public class MonitorActionController {

	static Logger logger = Logger.getLogger("dk.jnie.dragunzip.monitor.control");
	private boolean found;

	enum FileType {
		ZIP, TXT, XML, MP3, WRK
	};

	public MonitorActionController() {
	}

	public void routeAction(File file) {
		logger.info("Entering...");
		found = false;
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
			MessagePopup mp = new MessagePopup();
			
			JOptionPane.showMessageDialog(null,mp.getRandomMessage(),
				    "With the fileextension of '" + fileType + "' what did you expect?",JOptionPane.PLAIN_MESSAGE);
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
		case MP3:
			ma = new MonitorActionMP3();
			found = true;
			break;
		case WRK:
			ma = new MonitorActionWRK();
			found = true;
			break;
		default:
			break;
		}
		if (ma != null) {
			ma.setFile(file);
			Thread t = new Thread(ma);
			t.start();
//			ma.doAction(file.getAbsoluteFile());
		}
		logger.fine("Exiting...");
	}

}
