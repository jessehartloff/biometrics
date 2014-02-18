package interfaces.gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import settings.AllSettings;

public class LoadSettings {

	public static void main(String[] args){
		JFrame frame = new JFrame("Biometrics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setBackground(Color.WHITE);
		
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

	//	panel2.setLayout(new FlowLayout());
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
		panel2.setBackground(Color.WHITE);
//		panel2.add(AllSettings.getInstance().getTopJPanel());
		panel2.add(AllSettings.getInstance().getJPanel());
//		frame.setPreferredSize(new Dimension(1400,900));
		JScrollPane scr = new JScrollPane(panel2);
		frame.add(scr);
		
		frame.pack();
		frame.setVisible(true);
	}
	
}
