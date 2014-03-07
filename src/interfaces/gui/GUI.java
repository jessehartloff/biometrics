package interfaces.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import settings.AllSettings;
import settings.SettingsIO;


public class GUI {
	
	public static final Color BACKGROUNDCOLOR = Color.BLUE;
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Biometrics");
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setBackground(Color.WHITE);
		
		JMenuBar menuBar;
		JMenu menu;

		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		JMenuItem item = new JMenuItem("save");
		item.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SettingsIO.saveSettingsToFile("file1");
		        System.out.println("settings saved");
		    }
		});
		menu.add(item);
		
		JMenuItem item2 = new JMenuItem("load");
		item2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SettingsIO.loadSettingsFromFile("file1");
		        System.out.println("settings loaded");
		    }
		});
		menu.add(item2);
		
		menuBar.add(menu);

		menu = new JMenu("Edit");
		menuBar.add(menu);
		
		menu = new JMenu("Window");
		menuBar.add(menu);
		
		menu = new JMenu("Help");
		menuBar.add(menu);
		
		
		frame.setJMenuBar(menuBar);
		JPanel panel = new JPanel();


//		
		panel.setBackground(BACKGROUNDCOLOR);
		
		AllSettings.getInstance();
		
// binning: 0.07286560557237248
//     pca: 
		
		panel.add(AllSettings.getInstance().getJPanel());
//		frame.setPreferredSize(new Dimension(1400,900));
		JScrollPane scr = new JScrollPane(panel);
		scr.getVerticalScrollBar().setUnitIncrement(15);
		frame.add(scr);
		
		frame.pack();
		frame.setVisible(true);
	}
	
}
