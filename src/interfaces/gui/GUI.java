package interfaces.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import settings.AllSettings;


public class GUI {
	
		
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Biometrics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setBackground(Color.WHITE);
		
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
		JPanel panel = new JPanel();


//		
		panel.setBackground(Color.WHITE);
		
		panel.add(AllSettings.getInstance().getJPanel());
//		frame.setPreferredSize(new Dimension(1400,900));
		JScrollPane scr = new JScrollPane(panel);
		frame.add(scr);
		
		frame.pack();
		frame.setVisible(true);
	}
	
}
