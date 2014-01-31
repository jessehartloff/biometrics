package interfaces.gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import settings.AllSettings;
import settings.Settings;
import settings.SettingsComboBoxActionListener;
import settings.SettingsRenderer;
import settings.modalitysettings.FaceSettings;
import settings.modalitysettings.FingerprintSettings;
import settings.modalitysettings.IrisSettings;
import settings.modalitysettings.PizzaSettings;


public class GUI2E {
	
		
	public static void main(String[] args){
		JFrame frame = new JFrame("Biometrics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		//Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menuBar.add(menu);

		menu = new JMenu("Edit");
		menuBar.add(menu);
		
		menu = new JMenu("Window");
		menuBar.add(menu);
		
		menu = new JMenu("Help");
		menuBar.add(menu);
		
		
		frame.setJMenuBar(menuBar);
		
//		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
//		
//		panel.add(panel2); 
//		
//		panel2.add(new JLabel("string"));
//
//		Settings[] modalityList = new Settings[] {
//				FingerprintSettings.getInstance(),
//				new IrisSettings(),
//				new FaceSettings(),
//				new PizzaSettings()		
//		};
//		
//		JComboBox modalitiesBox = new JComboBox(modalityList);
//		modalitiesBox.addActionListener(new SettingsComboBoxActionListener(AllSettings.getInstance(), "Modality"));
//		modalitiesBox.setRenderer(new SettingsRenderer());
//	
//		panel2.add(modalitiesBox);
//		

		panel2.add(AllSettings.getInstance().getTopJPanel());

		
		frame.add(panel2);
		
		frame.pack();
		frame.setVisible(true);
	}
	
}
