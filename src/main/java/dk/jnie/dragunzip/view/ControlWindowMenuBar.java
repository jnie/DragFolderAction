package dk.jnie.dragunzip.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import dk.csc.util.properties.SortedProperties;
import dk.jnie.dragunzip.control.MyActionListener;
import dk.jnie.dragunzip.control.MyKeyListener;
import dk.jnie.dragunzip.model.Property;

public class ControlWindowMenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Where the GUI is created:
	JMenu configMenu, viewMenu, helpMenu;
	JMenuItem menuEditItem,menuAboutItem,menuExitItem,menuStartItem,menuStopItem,menuLangUkItem,menuLangDkItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;

	public ControlWindowMenuBar() {
		SortedProperties props = Property.getProps();

		Locale locale = Locale.getDefault();
		locale = new Locale(props.getProperty("resourcebundle"));
		ResourceBundle rb = ResourceBundle.getBundle("language", locale);
		
		// Build the first menu.
		configMenu = new JMenu(rb.getString("menu_config"));
		configMenu.setMnemonic(KeyEvent.VK_O);
		configMenu.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_config_accessctx"));
		add(configMenu);

		viewMenu= new JMenu(rb.getString("menu_view"));
		configMenu.setMnemonic(KeyEvent.VK_V);
		configMenu.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_view_accessctx"));
		add(viewMenu);
		
		// Build the Help menu
		helpMenu = new JMenu(rb.getString("menu_help"));
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_help_accessctx"));
		add(helpMenu);

		// a group of JMenuItems
		menuEditItem = new JMenuItem(rb.getString("menu_config_edit"),
				KeyEvent.VK_E);
		menuEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.ALT_MASK));
		addListeners(menuEditItem, ComponentID.MENU_CONFIG_EDIT);
		menuEditItem.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_config_edit_accessctx"));

		menuStartItem = new JMenuItem(rb.getString("menu_config_start"),
				KeyEvent.VK_S);
		menuStartItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));
		addListeners(menuStartItem, ComponentID.MENU_CONFIG_START);
		menuStartItem.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_config_start_accessctx"));

		menuStopItem = new JMenuItem(rb.getString("menu_config_stop"),
				KeyEvent.VK_P);
		menuStopItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.ALT_MASK));
		addListeners(menuStopItem, ComponentID.MENU_CONFIG_STOP);
		menuStopItem.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_config_stop_accessctx"));
		
		configMenu.add(menuEditItem);
		configMenu.add(menuStartItem);
		configMenu.add(menuStopItem);
		
		configMenu.addSeparator();
		
		//Exit
		menuExitItem = new JMenuItem(rb.getString("menu_config_exit"),
				KeyEvent.VK_X);
		menuExitItem .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.ALT_MASK));
		addListeners(menuExitItem, ComponentID.MENU_CONFIG_EXIT);
			menuExitItem.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_config_exit_accessctx"));
		configMenu.add(menuExitItem);

		//build content in view menu
		//UK
		menuLangUkItem = new JMenuItem(rb.getString("menu_view_uk"),
				KeyEvent.VK_U);
		menuLangUkItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.ALT_MASK));
		menuLangUkItem.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_view_uk_accessctx"));
		addListeners(menuLangUkItem, ComponentID.MENU_VIEW_UK);
		//DK
		menuLangDkItem = new JMenuItem(rb.getString("menu_view_dk"),
				KeyEvent.VK_D);
		menuLangDkItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.ALT_MASK));
		menuLangDkItem.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_view_dk_accessctx"));
		addListeners(menuLangDkItem, ComponentID.MENU_VIEW_DK);
		
		viewMenu.add(menuLangUkItem);
		viewMenu.add(menuLangDkItem);
		
		// Build second menu in the menu bar.
		menuAboutItem = new JMenuItem(rb.getString("menu_help_about"),
				KeyEvent.VK_A);
		menuAboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.ALT_MASK));
		menuAboutItem.getAccessibleContext().setAccessibleDescription(
				rb.getString("menu_help_about_accessctx"));
		addListeners(menuAboutItem, ComponentID.MENU_HELP_ABOUT);
		helpMenu.add(menuAboutItem);

	}
	
	private void addListeners(JMenuItem menuItem, ComponentID componentId) {
		menuItem.addKeyListener(new MyKeyListener());
//		menuItem.addMouseListener(new MyMouseListener());
		menuItem.addActionListener(new MyActionListener(componentId));
	}
}
