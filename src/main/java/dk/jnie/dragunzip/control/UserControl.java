package dk.jnie.dragunzip.control;

import java.util.logging.Logger;

import dk.jnie.dragunzip.monitor.Monitor;
import dk.jnie.dragunzip.view.ControlWindow;


public class UserControl {

	static Logger logger = Logger.getLogger("dk.jnie.dragunzip");

	static LogControl lc = new LogControl();
	
	public UserControl() {
		ControlWindow cw = new ControlWindow("User Control Center");
//		cw.addMouseListener(new MyMouseListener());
		
		Monitor mon = Monitor.getInstance();
		
		Thread monitor = new Thread(mon, "Monitor");
		cw.setMonitor(monitor);
		
		monitor.start();
		
//		jf.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});

//		Container contentPane = jf.getContentPane();
//		contentPane.add(new ControlWindow());

	}

	public static void main(String[] args) {
		UserControl uc = new UserControl();

	}
}
