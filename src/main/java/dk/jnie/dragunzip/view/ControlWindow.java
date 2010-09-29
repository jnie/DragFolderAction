package dk.jnie.dragunzip.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.synth.SynthLookAndFeel;

import dk.csc.util.file.FileUtil;
import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.control.MyActionListener;
import dk.jnie.dragunzip.control.MyKeyListener;
import dk.jnie.dragunzip.control.UserControl;
import dk.jnie.dragunzip.model.Property;

public class ControlWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4801255677951046416L;
	
	private Logger logger = Logger.getLogger("dk.jnie.dragunzip.control");

	private Thread monitor = null;

	private static JButton jbnStop, jbnStart = null;
	private SortedProperties props = null; 

	public ControlWindow(String title) {
		props = Property.getProps();
		
		Locale locale = Locale.getDefault();
		locale = new Locale(props.getProperty("resourcebundle"));
	    ResourceBundle rb = ResourceBundle.getBundle("language", locale);
	    
		this.setBackground(new Color(1,1,1));
        this.getContentPane().setLayout(new FlowLayout());
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel lMonitor = new JLabel(rb.getString("txtf_folder"));
		this.add(lMonitor);
		
		JTextField jTxtField = new JTextField(props.getProperty(Property.MONITORFOLDER),20);
		jTxtField.addActionListener(new MyActionListener(ComponentID.TXTF_FOLDER));
		jTxtField.addKeyListener(new MyKeyListener());
		
        this.getContentPane().add(jTxtField);
        jbnStop = new JButton(rb.getString("b_stop"));
        jbnStart = new JButton(rb.getString("b_start"));
		add(jbnStop);
		add(jbnStart);
		jbnStop.addActionListener(new MyActionListener(ComponentID.B_STOP));
		jbnStop.addKeyListener(new MyKeyListener());		
		jbnStart.addActionListener(new MyActionListener(ComponentID.B_START));
		jbnStart.addKeyListener(new MyKeyListener());
		
		ControlWindowMenuBar cwm = new ControlWindowMenuBar();

		setJMenuBar(cwm);
		
		this.setSize(600, 400);
		this.setLocation(300, 200);
		
		//Now this will set the LAF(Look & Feel)
		setLookAndFeel();
		SwingUtilities.updateComponentTreeUI(this);
        this.pack();
        
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (monitor != null) {
					monitor.interrupt();
				}
				System.exit(0);
			}
			});

		
		this.setVisible(true);
	}

	public void setMonitor(Thread monitor) {
		this.monitor = monitor;
	}
	
	private void setLookAndFeel() {
		String laf = props.getProperty(Property.LAF);
		final SynthLookAndFeel slaf = new SynthLookAndFeel();
		
		try {
			// Check if Synth is the chosen one.
			if ("Synth".equalsIgnoreCase(laf)) {
				slaf.load(FileUtil.getStreamSource(
					"file:src/main/resources/synthGUI.xml").getInputStream(),
					UserControl.class);
				UIManager.setLookAndFeel(slaf);
				return;
			}
			// Otherwise see what is possible, and choose the one from the properties file  
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if (laf.equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            return;
		        }
		    }
		} catch (final ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
