package interfaces.gui;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import system.allcommonclasses.settings.Settings;
import system.coordinator.CoordinatorFactory;
import system.coordinator.CoordinatorFactory.HistogramCoordinatorEnumerator;
import system.coordinator.CoordinatorFactory.IndexingCoordinatorEnumerator;
import system.coordinator.CoordinatorFactory.MatchingCoordinatorEnumerator;
import system.coordinator.tests.TestGeneratorFactory;
import system.coordinator.tests.TestGeneratorFactory.TestGeneratorEnumerator;
import system.hasher.HasherFactory;
import system.hasher.HasherFactory.HasherEnumerator;
import system.makefeaturevector.fingerprintmethods.FingerprintMethodFactory;
import system.makefeaturevector.fingerprintmethods.FingerprintMethodFactory.FingerPrintEnumerator;

public class GUI {

	{}// TODO Matt - GUI
	
	private Settings settings;
	private JFrame frame;
	private JPanel outerPanel;
	private CardLayout cards;
	private LinkedHashMap<JComboBox, Method> globalSettingsMap;

	public GUI(){
		frame = new JFrame("Biometrics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new CardLayout();
		outerPanel = new JPanel();
		frame.add(outerPanel);
		outerPanel.setLayout(cards);
		settings = new Settings();
		frame.pack();
		JPanel globalSettingsPanel = new JPanel();
		try {
			globalSettingsPanel = this.setGlobalSettings();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		outerPanel.add(globalSettingsPanel, "Global Settings Panel");
		JPanel methodSettingsPanel = this.setMethodSettings();
		outerPanel.add(methodSettingsPanel,"Method Settings Panel");
		
//		TriangleSettings currentSettings = settings.triangleSettings.getInstance();
//		HashMap<String, Method> setterMap= new HashMap<String, Method>();
//		
//		
//		ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(currentSettings.getClass().getFields()));
//		ArrayList<Method> methods = new ArrayList<Method>(Arrays.asList(currentSettings.getClass().getMethods()));
//		for(Method method : methods){
//			System.out.println(method.getName());
//		}

		//System.out.println(fields);
//		for (Field f : fields){
//			System.out.println(f.getName());
//			JPanel panel = new JPanel();
//			panel.setLayout(new GridLayout(1,2));
//			panel.setSize(50, 500);
//			JLabel label = new JLabel(f.getName());
//			label.setHorizontalAlignment(SwingConstants.CENTER);
//			panel.add(label);
//			JTextField textField = new JTextField();
//			textField.setText(new Integer(4).toString());
//			panel.add(textField);
//			//frame.add(panel);
//		}
//		JButton run = new JButton("Go");
//		run.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				frame.dispose();
//				Processor processor = new Processor();
//
//				//processor.go(settings);
//
//			}
//			
//		});
//		
//		JButton load = new JButton("Run");
//		load.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("Stop pushing my buttons.");
//			}
//			
//		});
//		
//		frame.add(run);
//		frame.add(load);
		
		

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
	
	private JPanel setGlobalSettings() throws SecurityException, NoSuchMethodException{
		final JPanel globalSettingsPanel = new JPanel();
		
		int amountOfGlobalSettingsElements = 7;
		globalSettingsPanel.setLayout(new GridLayout(amountOfGlobalSettingsElements+1,2));
		
		FingerPrintEnumerator[] fingerprintMethodEnums = FingerprintMethodFactory.FingerPrintEnumerator.values();
		JComboBox fingerprintMethodValuesBox = new JComboBox(fingerprintMethodEnums);
		
		MatchingCoordinatorEnumerator[] matchingCoordinatorEnums = CoordinatorFactory.MatchingCoordinatorEnumerator.values();
		JComboBox matchingCoordinatorValuesBox = new JComboBox(matchingCoordinatorEnums);
		
		IndexingCoordinatorEnumerator[] indexingCoordinatorEnums = CoordinatorFactory.IndexingCoordinatorEnumerator.values(); 
		JComboBox indexingCoordinatorValuesBox = new JComboBox(indexingCoordinatorEnums);
		
		HistogramCoordinatorEnumerator[] histogramCoordinatorEnums = CoordinatorFactory.HistogramCoordinatorEnumerator.values();
		JComboBox histogramCoordinatorValuesBox = new JComboBox(histogramCoordinatorEnums);
		
		HasherEnumerator[] hasherEnums = HasherFactory.HasherEnumerator.values();
		JComboBox hasherValuesBox = new JComboBox(hasherEnums);
		
		TestGeneratorEnumerator[] testGeneratorEnums = TestGeneratorFactory.TestGeneratorEnumerator.values();
		JComboBox testGeneratorValuesBox = new JComboBox(testGeneratorEnums);
		
		String[] datasetValues = {"FVC2002DB1", "FVC2002DB2", "FVC2002DB3", "FVC2002DB4",
				"FVC2004DB1", "FVC2004DB2", "FVC2004DB3", "FVC2004DB4", 
				"FVC2006DB1", "FVC2006DB2", "FVC2006DB3", "FVC2006DB4", "other"};
		JComboBox datasetValuesBox = new JComboBox(datasetValues);

		fingerprintMethodValuesBox.setSelectedItem(FingerprintMethodFactory.FingerPrintEnumerator.valueOf("TRIANGLES"));
		matchingCoordinatorValuesBox.setSelectedItem(CoordinatorFactory.MatchingCoordinatorEnumerator.valueOf("NONE"));
		indexingCoordinatorValuesBox.setSelectedItem(CoordinatorFactory.IndexingCoordinatorEnumerator.valueOf("NONE"));
		histogramCoordinatorValuesBox.setSelectedItem(CoordinatorFactory.HistogramCoordinatorEnumerator.valueOf("HISTOGRAM"));
		hasherValuesBox.setSelectedItem(HasherFactory.HasherEnumerator.valueOf("STRAIGHTHASHER"));
		testGeneratorValuesBox.setSelectedItem(TestGeneratorFactory.TestGeneratorEnumerator.valueOf("GENERATEFVCSTYLETESTS"));
		datasetValuesBox.setSelectedItem("FVC2002DB1");
		for(Method m : settings.globalSettings.getClass().getMethods()){
			System.out.println(m.getName());
		}
		globalSettingsPanel.add(new JLabel("Fingerprint Method:"));
		globalSettingsPanel.add(fingerprintMethodValuesBox);
		globalSettingsMap.put(fingerprintMethodValuesBox, settings.globalSettings.getClass().getMethod("setFingerprintMethodString", String.class));
		
		globalSettingsPanel.add(new JLabel("Matching Coordinator:"));
		globalSettingsPanel.add(matchingCoordinatorValuesBox);
		globalSettingsMap.put(fingerprintMethodValuesBox, settings.globalSettings.getClass().getMethod("setMatchingCoordinator", String.class));

		
		globalSettingsPanel.add(new JLabel("Indexing Coordinator:"));
		globalSettingsPanel.add(indexingCoordinatorValuesBox);
		globalSettingsMap.put(fingerprintMethodValuesBox, settings.globalSettings.getClass().getMethod("setIndexingCoordinator", String.class));

		
		globalSettingsPanel.add(new JLabel("Histogram Coordinator:"));
		globalSettingsPanel.add(histogramCoordinatorValuesBox);
		globalSettingsMap.put(fingerprintMethodValuesBox, settings.globalSettings.getClass().getMethod("setHistogramCoordinator", String.class));

		
		globalSettingsPanel.add(new JLabel("Hasher:"));
		globalSettingsPanel.add(hasherValuesBox);		
		globalSettingsMap.put(fingerprintMethodValuesBox, settings.globalSettings.getClass().getMethod("setHasher", String.class));

		
		globalSettingsPanel.add(new JLabel("Test Generator:"));
		globalSettingsPanel.add(testGeneratorValuesBox);
		globalSettingsMap.put(fingerprintMethodValuesBox, settings.globalSettings.getClass().getMethod("setTestGenerator", String.class));

		
		globalSettingsPanel.add(new JLabel("Dataset:"));
		globalSettingsPanel.add(datasetValuesBox);
		globalSettingsMap.put(fingerprintMethodValuesBox, settings.globalSettings.getClass().getMethod("setDataset", String.class));

		
		JButton next = new JButton("Next");
		
		next.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cards.next(outerPanel);
			}
		});
		
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Stop pushing my buttons.");
			}
		});
		
		globalSettingsPanel.add(next);
		globalSettingsPanel.add(load);
		return globalSettingsPanel;
	}
	
	private JPanel setMethodSettings(){
		JPanel methodSettingsPanel = new JPanel();
		return methodSettingsPanel;
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
}
