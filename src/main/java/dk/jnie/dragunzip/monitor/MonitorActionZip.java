package dk.jnie.dragunzip.monitor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.model.Property;

public class MonitorActionZip implements MonitorActionInterface {
	static Logger logger = Logger.getLogger("dk.jnie.dragunzip");
	static SortedProperties props;
	private File file;
	
	@Override
	public void doAction() {
		props = Property.getProps();
		String outputFolder = props.getProperty(Property.OUTPUTFOLDER, "c:\\temp\\");
		File output = new File(outputFolder);
		if (!output.exists()) {
			output.mkdir();
		}
		
		
		Enumeration<? extends ZipEntry> entries;
		ZipFile zipFile;

		if (file == null) {
			logger.info("To Unzip a zipfile, I need a filename!");
			return;
		}

		try {
			zipFile = new ZipFile(file.getAbsolutePath());

			entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				if (entry.isDirectory()) {
					// Assume directories are stored parents first then
					// children.
					logger.info("Extracting directory: " + entry.getName());
					// This is not robust, just for demonstration purposes.
					(new File(outputFolder+ entry.getName())).mkdir();
					continue;
				}

				logger.info("Extracting file: " + entry.getName());
				File outputFile = new File(outputFolder+ entry.getName());
				FileOutputStream fos = new FileOutputStream(outputFile);
				copyInputStream(zipFile.getInputStream(entry),
						new BufferedOutputStream(fos));
			}

			zipFile.close();
		} catch (IOException ioe) {
			logger.warning("Unhandled exception: " + ioe.getMessage());
			if (logger.isLoggable(Level.ALL)) {
				ioe.printStackTrace();
			}
			return;
		}
	}

	private static final void copyInputStream(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
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
