
//import java.awt.CardLayout;
//import java.lang.reflect.Method;
//import java.util.LinkedHashMap;
//
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import settings.Settings;
//
//public class GUI {
//
//	// TODO Matt - GUI
//
//	private Settings settings;
////	private JFrame frame;
////	private JPanel outerPanel;
////	private CardLayout cards;
////	private LinkedHashMap<JComboBox, Method> globalSettingsMap;
//
////	public GUI(){
////		frame = new JFrame("Biometrics");
////		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		cards = new CardLayout();
//		//		outerPanel = new JPanel();
//		//		frame.add(outerPanel);
//		//		JPanel background = new JPanel();
//		//		background.add(new ImageIcon());
//		//		frame.add();
//		//		outerPanel.setLayout(cards);
//		//		settings = new Settings();
//		//		frame.pack();
//		//		JPanel globalSettingsPanel = new JPanel();
//		//		try {
//		//			globalSettingsPanel = this.setGlobalSettings();
//		//		} catch (Exception e) {
//		//			e.printStackTrace();
//		//		} 
//		//		outerPanel.add(globalSettingsPanel, "Global Settings Panel");
//		//		JPanel methodSettingsPanel = this.setMethodSettings();
//		//		outerPanel.add(methodSettingsPanel,"Method Settings Panel");
//		//		
//		//		TriangleSettings currentSettings = settings.triangleSettings.getInstance();
//		//		HashMap<String, Method> setterMap= new HashMap<String, Method>();
//		//		
//		//		
//		//		ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(currentSettings.getClass().getFields()));
//		//		ArrayList<Method> methods = new ArrayList<Method>(Arrays.asList(currentSettings.getClass().getMethods()));
//		//		for(Method method : methods){
//		//			System.out.println(method.getName());
//		//	}
//
//		//System.out.println(fields);
//		//		for (Field f : fields){
//		//			System.out.println(f.getName());
//		//			JPanel panel = new JPanel();
//		//			panel.setLayout(new GridLayout(1,2));
//		//			panel.setSize(50, 500);
//		//			JLabel label = new JLabel(f.getName());
//		//			label.setHorizontalAlignment(SwingConstants.CENTER);
//		//			panel.add(label);
//		//			JTextField textField = new JTextField();
//		//			textField.setText(new Integer(4).toString());
//		//			panel.add(textField);
//		//			//frame.add(panel);
//		//		}
//		//		JButton run = new JButton("Go");
//		=======
//				//package interfaces.gui;
//				//
//				//import java.awt.GridLayout;
//				//import java.awt.event.ActionEvent;
//				//import java.awt.event.ActionListener;
//				//import java.io.Serializable;
//				//import java.lang.reflect.Field;
//				//import java.lang.reflect.Method;
//				//import java.util.ArrayList;
//				//import java.util.Arrays;
//				//import java.util.LinkedHashMap;
//				//
//				//import javax.swing.JButton;
//				//import javax.swing.JComboBox;
//				//import javax.swing.JFrame;
//				//import javax.swing.JLabel;
//				//import javax.swing.JPanel;
//				//import javax.swing.JTextField;
//				//
//				//import settings.AllSettings;
//				//import settings.CoordinatorFactory;
//				//import settings.CoordinatorFactory.HistogramCoordinatorEnumerator;
//				//import settings.CoordinatorFactory.IndexingCoordinatorEnumerator;
//				//import settings.CoordinatorFactory.MatchingCoordinatorEnumerator;
//				//import settings.coordinatorsettings.TestGeneratorFactory;
//				//import settings.coordinatorsettings.TestGeneratorFactory.TestGeneratorEnumerator;
//				//import settings.hashersettings.HasherFactory;
//				//import settings.hashersettings.HasherFactory.HasherEnumerator;
//				//import settings.modalitysettings.methodsettings.fingerprintmethodsettings.FingerprintMethodFactory;
//				//import settings.modalitysettings.methodsettings.fingerprintmethodsettings.MinutiaSettings;
//				//import settings.modalitysettings.methodsettings.fingerprintmethodsettings.FingerprintMethodFactory.FingerPrintEnumerator;
//				//
//				//public class GUI {
//				//
//				//	{}// TODO Matt - GUI
//				//	
//				//	private AllSettings settings;
//				//	private JFrame frame;
//				//	private JPanel outerPanel;
//				//	private JPanel methodSettingsPanel;
//				//	//private CardLayout cards;
//				//	private LinkedHashMap<JComboBox, Method> globalSettingsMap;
//				//	private LinkedHashMap<String,Serializable> methodSettingsMap; //this is beyond janky. We need a generic settings class for all other methodSettings to extend
//				//	
//				//	public static void main(String[] args) {
//				//		new GUI();
//				//	}
//				//	
//				//	public GUI(){
//				//		settings = new AllSettings();
//				//		globalSettingsMap = new LinkedHashMap<JComboBox, Method>(); // screw this. We'll find a better way
//				//		methodSettingsMap = new LinkedHashMap<String, Serializable>(); // <String, SettingsClass>
//				//		//ask Jesse for design advice on this...
//				//		methodSettingsMap.put("MINUTIAEMETHOD", MinutiaSettings.class);//MinutiaeSettings.getInstance()); 
//				//		methodSettingsMap.put("PATHSMETHOD", settings.pathSettings.getInstance());
//				//		methodSettingsMap.put("TRIANGLES", settings.triangleSettings.getInstance());
//				//		methodSettingsMap.put("TRIPLESOFTRIANGLES", settings.triangleSettings.getInstance());
//				//		methodSettingsMap.put("TRIPLESOFTRIANGLESALLROTATIONS", settings.triangleSettings.getInstance());
//				//		methodSettingsMap.put("NGONS", settings.ngonSettings.getInstance());
//				//		methodSettingsMap.put("NGONSALLROTATIONS", settings.ngonSettings.getInstance());
//				//
//				//		frame = new JFrame("Biometrics");
//				//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				//		outerPanel = new JPanel();
//				//		frame.add(outerPanel);
//				//		outerPanel.setLayout(new GridLayout(2,1));
//				//		frame.pack();
//				//
//				//		methodSettingsPanel = new JPanel();
//				//
//				//		JPanel globalSettingsPanel = new JPanel();
//				//		try {
//				//			globalSettingsPanel = this.drawGlobalSettingsPanel();
//				//		} catch (Exception e) {
//				//			e.printStackTrace();
//				//		} 
//				//		
//				//		JButton run = new JButton("Run");
//				//
//				>>>>>>> 1.16
//				//		run.addActionListener(new ActionListener(){
//				//
//				//			@Override
//				//			public void actionPerformed(ActionEvent arg0) {
//				//				System.out.println("Running......");
//				//				for(int i = 0; i < 1000; i++){
//				//					System.out.println("Running......");
//				//				}
//				//				System.out.println("Just Kidding!");
//				//
//				//			}
//				//		});
//				//		methodSettingsPanel.add(run);
//				//		
//				//		outerPanel.add(globalSettingsPanel, "Global Settings Panel");
//				//		outerPanel.add(methodSettingsPanel,"Method Settings Panel");
//				//
//				//		frame.pack();
//				//		frame.setVisible(true);
//				//	}
//				//	
//				//	private JPanel drawGlobalSettingsPanel() throws SecurityException, NoSuchMethodException{
//				//		final JPanel globalSettingsPanel = new JPanel();
//				//		
//				//		int amountOfGlobalSettingsElements = 7;
//				//		globalSettingsPanel.setLayout(new GridLayout(amountOfGlobalSettingsElements+1,2));
//				//		
//				//		FingerPrintEnumerator[] fingerprintMethodEnums = FingerprintMethodFactory.FingerPrintEnumerator.values();
//				//		final JComboBox fingerprintMethodValuesBox = new JComboBox(fingerprintMethodEnums);
//				//		fingerprintMethodValuesBox.addActionListener(new ActionListener(){
//				//
//				//			@Override
//				//			public void actionPerformed(ActionEvent e) {
//				//				settings.globalSettings.setFingerprintMethod(fingerprintMethodValuesBox.getSelectedItem().toString());
//				//				drawMethodSettingsPanel();
//				//			}
//				//			
//				//		});
//				//		
//				//		MatchingCoordinatorEnumerator[] matchingCoordinatorEnums = CoordinatorFactory.MatchingCoordinatorEnumerator.values();
//				//		JComboBox matchingCoordinatorValuesBox = new JComboBox(matchingCoordinatorEnums);
//				//		
//				//		IndexingCoordinatorEnumerator[] indexingCoordinatorEnums = CoordinatorFactory.IndexingCoordinatorEnumerator.values(); 
//				//		JComboBox indexingCoordinatorValuesBox = new JComboBox(indexingCoordinatorEnums);
//				//		
//				//		HistogramCoordinatorEnumerator[] histogramCoordinatorEnums = CoordinatorFactory.HistogramCoordinatorEnumerator.values();
//				//		JComboBox histogramCoordinatorValuesBox = new JComboBox(histogramCoordinatorEnums);
//				//		
//				//		HasherEnumerator[] hasherEnums = HasherFactory.HasherEnumerator.values();
//				//		JComboBox hasherValuesBox = new JComboBox(hasherEnums);
//				//		
//				//		TestGeneratorEnumerator[] testGeneratorEnums = TestGeneratorFactory.TestGeneratorEnumerator.values();
//				//		JComboBox testGeneratorValuesBox = new JComboBox(testGeneratorEnums);
//				//		
//				//		String[] datasetValues = {"FVC2002DB1", "FVC2002DB2", "FVC2002DB3", "FVC2002DB4",
//				//				"FVC2004DB1", "FVC2004DB2", "FVC2004DB3", "FVC2004DB4", 
//				//				"FVC2006DB1", "FVC2006DB2", "FVC2006DB3", "FVC2006DB4", "other"};
//				//		JComboBox datasetValuesBox = new JComboBox(datasetValues);
//				//
//				//		fingerprintMethodValuesBox.setSelectedItem(FingerprintMethodFactory.FingerPrintEnumerator.valueOf("TRIANGLES"));
//				//		matchingCoordinatorValuesBox.setSelectedItem(CoordinatorFactory.MatchingCoordinatorEnumerator.valueOf("NONE"));
//				//		indexingCoordinatorValuesBox.setSelectedItem(CoordinatorFactory.IndexingCoordinatorEnumerator.valueOf("NONE"));
//				//		histogramCoordinatorValuesBox.setSelectedItem(CoordinatorFactory.HistogramCoordinatorEnumerator.valueOf("HISTOGRAM"));
//				//		hasherValuesBox.setSelectedItem(HasherFactory.HasherEnumerator.valueOf("STRAIGHTHASHER"));
//				//		testGeneratorValuesBox.setSelectedItem(TestGeneratorFactory.TestGeneratorEnumerator.valueOf("GENERATEFVCSTYLETESTS"));
//				//		datasetValuesBox.setSelectedItem("FVC2002DB1");
//				//
//				//		globalSettingsPanel.add(new JLabel("Fingerprint Method:"));
//				//		globalSettingsPanel.add(fingerprintMethodValuesBox);
//				//		System.out.println(settings.globalSettings.getClass().getMethod("setFingerprintMethod", String.class));
//				//		globalSettingsMap.put(fingerprintMethodValuesBox, settings.globalSettings.getClass().getMethod("setFingerprintMethod", String.class));
//				//		
//				//		globalSettingsPanel.add(new JLabel("Matching Coordinator:"));
//				//		globalSettingsPanel.add(matchingCoordinatorValuesBox);
//				//		globalSettingsMap.put(matchingCoordinatorValuesBox, settings.globalSettings.getClass().getMethod("setMatchingCoordinator", String.class));
//				//
//				//		
//				//		globalSettingsPanel.add(new JLabel("Indexing Coordinator:"));
//				//		globalSettingsPanel.add(indexingCoordinatorValuesBox);
//				//		globalSettingsMap.put(indexingCoordinatorValuesBox, settings.globalSettings.getClass().getMethod("setIndexingCoordinator", String.class));
//				//
//				//		
//				//		globalSettingsPanel.add(new JLabel("Histogram Coordinator:"));
//				//		globalSettingsPanel.add(histogramCoordinatorValuesBox);
//				//		globalSettingsMap.put(histogramCoordinatorValuesBox, settings.globalSettings.getClass().getMethod("setHistogramCoordinator", String.class));
//				//
//				//		
//				//		globalSettingsPanel.add(new JLabel("Hasher:"));
//				//		globalSettingsPanel.add(hasherValuesBox);		
//				//		globalSettingsMap.put(hasherValuesBox, settings.globalSettings.getClass().getMethod("setHasher", String.class));
//				//
//				//		
//				//		globalSettingsPanel.add(new JLabel("Test Generator:"));
//				//		globalSettingsPanel.add(testGeneratorValuesBox);
//				//		globalSettingsMap.put(testGeneratorValuesBox, settings.globalSettings.getClass().getMethod("setTestGenerator", String.class));
//				//
//				//		
//				//		globalSettingsPanel.add(new JLabel("Dataset:"));
//				//		globalSettingsPanel.add(datasetValuesBox);
//				//		globalSettingsMap.put(datasetValuesBox, settings.globalSettings.getClass().getMethod("setTestingDataset", String.class));
//				//
//				//		JButton load = new JButton("Load");
//				//		load.addActionListener(new ActionListener(){
//				//
//				//			@Override
//				//			public void actionPerformed(ActionEvent arg0) {
//				//				System.out.println("Stop pushing my buttons.");
//				//			}
//				//		});
//				//		
//				//		globalSettingsPanel.add(load);
//				//		return globalSettingsPanel;
//				//	}
//				//	
//				//	private void drawMethodSettingsPanel(){
//				//		methodSettingsPanel = new JPanel();
//				//		//System.out.println(settings.globalSettings.getFingerprintMethodString());
//				//		Serializable fingerprintMethod = methodSettingsMap.get(settings.globalSettings.getFingerprintMethod());
//				//		//System.out.println(fingerprintMethod);
//				//		ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(fingerprintMethod.getClass().getFields()));
//				//		methodSettingsPanel.setLayout(new GridLayout(fields.size()+1,2));
//				//		for(Field field : fields){
//				//			System.out.println(field.getName());
//				//			methodSettingsPanel.add(new JLabel(field.getName()));
//				//			methodSettingsPanel.add(new JTextField());
//				//			//System.out.println(field.getName());
//				//		}
//				//		methodSettingsPanel.revalidate();
//				//		methodSettingsPanel.repaint();
//				//		outerPanel.revalidate();
//				//		outerPanel.repaint();	
//				//	}
//				//
//	
//}