package dk.jnie.dragunzip.monitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Logger;

public class MonitorActionXML implements MonitorActionInterface {
	
	private static Logger logger = Logger.getLogger("dk.jnie.dragunzip");
	
	private String NL = System.getProperty("line.separator");
	private static final String ENCODING = "ISO-8859-1";
	
	@Override
	public void doAction(File file) {
		logger.entering("MonitorActionXML", "doAction(File)");
		StringBuilder text = new StringBuilder();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileReader(file));
			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine() + NL);
			}

			scanner.close();
			System.out.print(text);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

	}

}
