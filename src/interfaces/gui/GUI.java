package interfaces.gui;

import javax.swing.*;

public class GUI {

	{}// TODO Jesse - GUI
	

	
	public GUI(){
		JFrame frame = new JFrame("Biometrics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int width = 1000;
		int height = new Long(Math.round(width/1.618)).intValue();
        frame.setSize(width, height);
        
        JMenu menu = new JMenu();
//      menu.
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);

		JLabel label1 = new JLabel("Biometrics!");
		JLabel label2 = new JLabel("Biometrics!!");
		JLabel label3 = new JLabel("Biometrics!!!");
		JLabel label4 = new JLabel("Biometrics!!!!");
		JLabel label5 = new JLabel("Biometrics!!!!!");

		frame.getContentPane().add(label1);
		frame.getContentPane().add(label2);
		frame.getContentPane().add(label3);
		frame.getContentPane().add(label4);
		frame.getContentPane().add(label5);

		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new GUI();
	}
	
}
