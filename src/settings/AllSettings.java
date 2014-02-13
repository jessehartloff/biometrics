package settings;

import interfaces.gui.ResultsGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import settings.coordinatorsettings.HistogramCoordinatorSettings;
import settings.coordinatorsettings.IndexingCoordinatorSettings;
import settings.coordinatorsettings.MatchingCoordinatorSettings;
import settings.hashersettings.HasherSettings;
import settings.modalitysettings.ModalitySettings;
import system.allcommonclasses.commonstructures.Results;
import system.biometricsystem.BiometricSystem;

public class AllSettings extends Settings{

	public static final long serialVersionUID = 1L;
	
//	private JPanel topPanel;
//	
//	public JPanel getTopJPanel(){
//		return topPanel;
//	}
	
	//Singleton
	private static AllSettings instance;
	private AllSettings(){
//		this.topPanel = new JPanel();
//		this.topPanel.add(this.getJPanel());
	}
	public static AllSettings getInstance(){
		if(instance == null){
			instance = new AllSettings();
		}
		return instance;
	}
//	
//	@Override
//	protected JPanel makeJPanel() {
//		JPanel toRet = new JPanel();
//		toRet.setBackground(java.awt.Color.MAGENTA);
//		//toRet.setLayout(new GridLayout(2,1));
//		JPanel p1 = this.thisJPanel();
//
//		toRet.add(p1, BorderLayout.WEST);
//		JPanel p2 = this.makeChildrenJPanel();
//		
//		toRet.add(p2, BorderLayout.EAST);
//		toRet.repaint();
//		return toRet;
//	}

	
	@Override 
	protected void init(){
		this.settingsVariables.put("Matching", MatchingCoordinatorSettings.getInstance());
		this.settingsVariables.put("Indexing", IndexingCoordinatorSettings.getInstance());
		this.settingsVariables.put("Histogram", HistogramCoordinatorSettings.getInstance());
		this.settingsVariables.put("Hasher", HasherSettings.getInstance());
		this.settingsVariables.put("Modality", ModalitySettings.getInstance());
	}
	
	public static void updateGUI(){
//		AllSettings.getInstance();
//		instance.topPanel.removeAll();
//		instance.topPanel.add(instance.getJPanel());
		instance.getJPanel().repaint();
//		instance.topPanel.getParent().repaint();
	}
	
	public BiometricSystem buildSystem(){

		AllSettings.getInstance();
		
//		instance.topPanel.removeAll(); // remove access to settings. change to progress bars and RM later
		
		BiometricSystem system = new BiometricSystem();
		Results results = system.go();
		
		//this GUI probably shouldn't go here... but fuck it
//		ResultsGUI resultsGUI = new ResultsGUI(results);

		System.out.print(results.rawScores);
		System.out.println(results);
		System.out.println(results.zeroFAR());
		
		return system;
		
	}
	
	protected JPanel thisJPanel(){
		JPanel panel = new JPanel();
		//panel.setLayout(new GridLayout(2,2));

		//panel.setBackground(java.awt.Color.RED);
		//panel.setLayout(new FlowLayout());
		JButton goButton = new JButton("GO!");
		
		goButton.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	buildSystem();
            }
        });   
		
		panel.add(goButton);
		return panel;
	}

	public ModalitySettings modality(){
		return (ModalitySettings) this.settingsVariables.get("Modality");
	}
	public HasherSettings hasher(){
		return (HasherSettings) this.settingsVariables.get("Hasher");
	}
	public MatchingCoordinatorSettings matchingCoordinator(){
		return (MatchingCoordinatorSettings) this.settingsVariables.get("Matching");
	}
	public IndexingCoordinatorSettings indexingCoordinator(){
		return (IndexingCoordinatorSettings) this.settingsVariables.get("Indexing");
	}
	public HistogramCoordinatorSettings histogramCoordinator(){
		return (HistogramCoordinatorSettings) this.settingsVariables.get("Histogram");
	}

	
}
