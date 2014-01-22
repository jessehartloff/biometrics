package interfaces.gui;

import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import system.allcommonclasses.settings.Settings;
import system.allcommonclasses.settings.TriangleSettings;

public class GUI {

	{}// TODO Jesse/Matt - GUI
	
	private Settings settings;
	
	public GUI(){
		JFrame frame = new JFrame("Biometrics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TriangleSettings ngons = TriangleSettings.getInstance();
		HashMap<String, Method> setterMap= new HashMap<String, Method>();
		
		
		ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(ngons.getClass().getFields()));
		frame.setLayout(new GridLayout(fields.size()+1,2));
		
        JMenuItem menuItem = new JMenuItem();
//        menuItem.
        
        JMenu menu = new JMenu();
        menu.add(menuItem);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
		System.out.println(fields);
		for (Field f : fields){
			System.out.println(f);
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1,2));
			panel.setSize(50, 500);
			JLabel label = new JLabel(f.getName());
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(label);
			JTextField textField = new JTextField();
			textField.setText(new Integer(4).toString());
			panel.add(textField);
			frame.add(panel);
		}
		JButton run = new JButton("Run");
		frame.add(run);
		
		

//		JLabel label1 = new JLabel("Biometrics!");
//		JLabel label2 = new JLabel("Biometrics!!");
//		JLabel label3 = new JLabel("Biometrics!!!");
//		JLabel label4 = new JLabel("Biometrics!!!!");
//		JLabel label5 = new JLabel("Biometrics!!!!!");

//		frame.getContentPane().add(label1);
//		frame.getContentPane().add(label2);
//		frame.getContentPane().add(label3);
//		frame.getContentPane().add(label4);
//		frame.getContentPane().add(label5);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	private void setDefaultValues(){
		settings = new Settings();
		
		
		settings.globalSettings.setFingerprintMethodString("NGONSALLROTATIONS");
		settings.globalSettings.setMatchingCoordinator("DEFAULTTESTINGPREQUANTIZED");
		settings.globalSettings.setMatchingCoordinator("MULTIPLEENROLLMENT");

		settings.globalSettings.setIndexingCoordinator("NONE");
		settings.globalSettings.setHistogramCoordinator("NONE");
		settings.globalSettings.setHasher("STRAIGHTHASHER");
		settings.globalSettings.setTestGenerator("GENERATEFVCSTYLETESTS");
		settings.globalSettings.setIndexingStructure("RAM");
		settings.globalSettings.setDataset("FVC2002DB1");

		
		settings.triangleSettings.theta0.setBins(4);
		settings.triangleSettings.x1.setBins(4);
		settings.triangleSettings.y1.setBins(4);
		settings.triangleSettings.theta1.setBins(4);
		settings.triangleSettings.x2.setBins(4);
		settings.triangleSettings.y2.setBins(4);
		settings.triangleSettings.theta2.setBins(4);

		settings.triangleSettings.setRotationStart(-10.0);
		settings.triangleSettings.setRotationStop(10.0);
		settings.triangleSettings.setRotationStep(5.0);

		settings.triangleSettings.setMinimumPointsForTripletOfTriangles(4L);
		settings.triangleSettings.setThresholdForTriplets(25.0);
		settings.triangleSettings.setkClosestMinutia(3L);
		settings.triangleSettings.setkClosestTriangles(3L);

		settings.pathSettings.d0.setBins(4);
		settings.pathSettings.d1.setBins(4);
		settings.pathSettings.d2.setBins(4);
		settings.pathSettings.d3.setBins(4);
		settings.pathSettings.phi1.setBins(4);
		settings.pathSettings.phi2.setBins(4);
		settings.pathSettings.phi3.setBins(4);
		settings.pathSettings.sigma0.setBins(4);
		settings.pathSettings.sigma1.setBins(4);
		settings.pathSettings.sigma2.setBins(4);
		settings.pathSettings.sigma3.setBins(4);
		
		settings.pathSettings.setkClosestMinutia(5L);

		settings.minutiaeSettings.x.setBins(8);
		settings.minutiaeSettings.y.setBins(8);
		settings.minutiaeSettings.theta.setBins(8);

		settings.minutiaeSettings.setRotationStart(-50.0);
		settings.minutiaeSettings.setRotationStop(50.0);
		settings.minutiaeSettings.setRotationStep(10.0);

		settings.minutiaeSettings.setxStart(-50L);
		settings.minutiaeSettings.setxStop(50L);
		settings.minutiaeSettings.setxStep(10L);

		settings.minutiaeSettings.setyStart(-50L);
		settings.minutiaeSettings.setyStop(50L);
		settings.minutiaeSettings.setyStep(10L);
		
		settings.fuzzyVaultSettings.setNumberOfChaffPoints(00L);
		
		settings.nGonSettings.setN(5L); //5
		settings.nGonSettings.setAllNumberOfBins(5L,5L,5L);//5,5,5 //err of 736
		settings.nGonSettings.setkClosestMinutia(7L); //7

		settings.nGonSettings.setRotationStart(-50.0);
		settings.nGonSettings.setRotationStop(50.0);
		settings.nGonSettings.setRotationStep(5.0);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
}
