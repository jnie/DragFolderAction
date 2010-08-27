package dk.jnie.dragunzip.monitor;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MonitorActionMP3 implements MonitorActionInterface {

	private File file;
	@Override
	public void doAction() {
//		URL fileURL;
		try {
//			fileURL = FileUtil.getURL(file);


			// Player player = Manager.createPlayer(fileURL);
			// player.start();
			AudioInputStream in = AudioSystem.getAudioInputStream(file);
			AudioInputStream din = null;
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			din = AudioSystem.getAudioInputStream(decodedFormat, in);
			// Play now.
			logger.info("Going to play now!");
			rawplay(decodedFormat, din);
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void rawplay(AudioFormat targetFormat, AudioInputStream din)
			throws IOException, LineUnavailableException {
		logger.info("Entering.");
		byte[] data = new byte[4096];
		SourceDataLine line = getLine(targetFormat);
		logger.info("Got an entryLine");
		if (line != null) {
			logger.info("and it is not null");
			// Start
			line.start();
			int nBytesRead = 0, nBytesWritten = 0;
			while (nBytesRead != -1) {
				nBytesRead = din.read(data, 0, data.length);
				if (nBytesRead != -1)
					nBytesWritten = line.write(data, 0, nBytesRead);
			}
			// Stop
			line.drain();
			line.stop();
			line.close();
			din.close();
		}
	}

	private SourceDataLine getLine(AudioFormat audioFormat)
			throws LineUnavailableException {
		logger.info("Entering.");
		SourceDataLine line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		logger.info("DataLine.Info is format supported? " + info.isFormatSupported(audioFormat));
		line = (SourceDataLine) AudioSystem.getLine(info);
		logger.info("SourceDataLine BufferSize is " + line.getBufferSize());
		line.open(audioFormat);
		logger.info("SourceDataLine opened.");
		return line;
	}

	@Override
	public void run() {
		doAction();
	}

	@Override
	public void setFile(File file) {
		this.file = file; 
		
	}

}
