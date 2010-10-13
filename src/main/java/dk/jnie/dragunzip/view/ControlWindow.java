package dk.jnie.dragunzip.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.synth.SynthLookAndFeel;

import dk.csc.util.file.FileUtil;
import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.control.MyActionListener;
import dk.jnie.dragunzip.control.MyFocusListener;
import dk.jnie.dragunzip.control.MyKeyListener;
import dk.jnie.dragunzip.control.UserControl;
import dk.jnie.dragunzip.model.Property;

public class ControlWindow extends JFrame implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4801255677951046416L;

	private Logger logger = Logger.getLogger("dk.jnie.dragunzip.control");

	private Thread monitor = null;

	private static JButton jbnStop, jbnStart = null;
	private JPanel buttonPanel = null;
	private static JLabel lMonitor, lClearFolder, lTimer = null;
	private JTextField jTxtFolderMonitorName, jTxtTimer = null;

	private ResourceBundle rb = null;
	private Locale locale = Locale.getDefault();
	private SortedProperties props = null;
	private Container container;

	public ControlWindow(String title) {
		props = Property.getProps();

		locale = new Locale(props.getProperty("resourcebundle"));
		rb = ResourceBundle.getBundle("language", locale);

		this.setBackground(new Color(1, 1, 1));
		container = this.getContentPane();
		container.setLayout(new FlowLayout());
		container.setSize(500, 300);
		
		// build the window frame
		createComponents();

		this.setSize(600, 400);
		this.setLocation(300, 200);

		// Now this will set the LAF(Look & Feel)
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

	private void createComponents() {

		Box box0 = Box.createVerticalBox();
		Box box1 = Box.createVerticalBox();
		Box box2 = Box.createVerticalBox();
		Box box3 = Box.createHorizontalBox();
		Box box4 = Box.createVerticalBox();
		Box box5 = Box.createHorizontalBox();

		lMonitor = new JLabel(rb.getString("lbl_folder"));
		// lMonitor.setAlignmentX(Component.CENTER_ALIGNMENT);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lClearFolder = new JLabel(rb.getString("lbl_clear_folder"));
		lTimer = new JLabel(rb.getString("lbl_timer"));

		jTxtFolderMonitorName = new JTextField(props
				.getProperty(Property.MONITORFOLDER), 20);
		jTxtFolderMonitorName.addActionListener(new MyActionListener(
				ComponentID.TXTF_FOLDER));
		jTxtFolderMonitorName.addKeyListener(new MyKeyListener());
		jTxtFolderMonitorName.addFocusListener(new MyFocusListener(ComponentID.TXTF_FOLDER));

		jTxtTimer = new JTextField(props
				.getProperty(Property.TIMER), 20);
		jTxtTimer.addActionListener(new MyActionListener(
				ComponentID.TXTF_TIMER));
		jTxtTimer.addKeyListener(new MyKeyListener());
		jTxtTimer.addFocusListener(new MyFocusListener(ComponentID.TXTF_TIMER));
		
		JCheckBox jCBClearFolder = new JCheckBox();
		jCBClearFolder.addActionListener(new MyActionListener(
				ComponentID.CHK_CLEAR_FOLDER));
		jCBClearFolder.addKeyListener(new MyKeyListener());

		buttonPanel = new JPanel();
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(),
				rb.getString("brd_monitor_button")));
		
		jbnStop = new JButton(rb.getString("b_stop"));
		jbnStop.setMnemonic(KeyEvent.VK_P);
		jbnStart = new JButton(rb.getString("b_start"));
		jbnStart.setMnemonic(KeyEvent.VK_S);
		
		buttonPanel.add(jbnStart);
		buttonPanel.add(jbnStop);

		jbnStop.addActionListener(new MyActionListener(ComponentID.B_STOP));
		jbnStop.addKeyListener(new MyKeyListener());
		jbnStart.addActionListener(new MyActionListener(ComponentID.B_START));
		jbnStart.addKeyListener(new MyKeyListener());

		box0.add(lMonitor);
		box0.add(Box.createVerticalStrut(10));
		box0.add(lClearFolder);
		box0.add(Box.createVerticalStrut(10));
		box0.add(lTimer);

		box1.add(jTxtFolderMonitorName);
		box1.setAlignmentX(LEFT_ALIGNMENT);
		box1.add(jCBClearFolder);
		box1.add(jTxtTimer);
//		box1.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		box3.add(box0);
		box3.add(Box.createHorizontalStrut(10));
		box3.add(box1);
//		box3.add(box2);

//		box4.add(box3);
//		box4.setBorder(new BevelBorder(BevelBorder.LOWERED));
//		box4.add(box1);
		box5.add(box3);
		box5.add(Box.createHorizontalStrut(10));
//		box5.setBorder(new BevelBorder(BevelBorder.LOWERED));
		box5.add(buttonPanel);
		container.add(box5);

		ControlWindowMenuBar cwm = new ControlWindowMenuBar();
		Property.getInstance().addPropertyChangeListener(Property.RESOURCEBUNDLE, cwm);
		Property.getInstance().addPropertyChangeListener(Property.RESOURCEBUNDLE, new MyKeyListener());

		setJMenuBar(cwm);

	}

	private void updateGUILabels() {
		lMonitor.setText(rb.getString("lbl_folder"));
		lClearFolder.setText(rb.getString("lbl_clear_folder"));
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), rb
				.getString("brd_monitor_button")));
		SwingUtilities.updateComponentTreeUI(this);
		this.pack();
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
						"file:src/main/resources/synthGUI.xml")
						.getInputStream(), UserControl.class);
				UIManager.setLookAndFeel(slaf);
				return;
			}
			// Otherwise see what is possible, and choose the one from the
			// properties file
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

	/**
	 * Changes in Property, needs special attention from the gui, when resourcebundle changes
	 * all JLabels need a text change
	 */
	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		String propertyName = pce.getPropertyName();
		if (propertyName.equals(Property.RESOURCEBUNDLE)) {
			locale = new Locale(props.getProperty("resourcebundle"));
			rb = ResourceBundle.getBundle("language", locale);
			updateGUILabels();
		}

		if (logger.isLoggable(Level.INFO)) {
			String propertyOldValue = (String) pce.getOldValue();
			String propertyNewValue = (String) pce.getNewValue();
			logger.info("PropertyName = " + propertyName);
			logger.info("PropertyOldValue = " + propertyOldValue);
			logger.info("PropertyNewValue = " + propertyNewValue);
		}

	}

}
