package settings;

import interfaces.gui.ResultsGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;

import settings.coordinatorsettings.AllHistogramCoordinatorSettings;
import settings.coordinatorsettings.AllIndexingCoordinatorSettings;
import settings.coordinatorsettings.AllMatchingCoordinatorSettings;
import settings.hashersettings.AllHasherSettings;
import settings.modalitysettings.AllModalitySettings;
import system.allcommonclasses.commonstructures.Results;
import system.biometricsystem.BiometricSystem;

public class AllSettings extends Settings{

	public static final long serialVersionUID = 1L;
	
//	private JPanel topPanel;
//	
//	public JPanel getTopJPanel(){
//		return topPanel;
//	}
	
	
	//Singleton. This block of code must be in all settings files (except settings variables) to enable serialization.
	private static AllSettings instance;
	private AllSettings(){}
	public static AllSettings getInstance(){
		if(instance == null){
			instance = new AllSettings();
		}
		return instance;
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{AllSettings.instance = (AllSettings) in.readObject();} // TODO does this really work??
	
	
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
		this.settingsVariables.put("Matching", AllMatchingCoordinatorSettings.getInstance());
		this.settingsVariables.put("Indexing", AllIndexingCoordinatorSettings.getInstance());
		this.settingsVariables.put("Histogram", AllHistogramCoordinatorSettings.getInstance());
		this.settingsVariables.put("Hasher", AllHasherSettings.getInstance());
		this.settingsVariables.put("Modality", AllModalitySettings.getInstance());
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

	public AllModalitySettings modality(){
		return (AllModalitySettings) this.settingsVariables.get("Modality");
	}
	public AllHasherSettings hasher(){
		return (AllHasherSettings) this.settingsVariables.get("Hasher");
	}
	public AllMatchingCoordinatorSettings matchingCoordinator(){
		return (AllMatchingCoordinatorSettings) this.settingsVariables.get("Matching");
	}
	public AllIndexingCoordinatorSettings indexingCoordinator(){
		return (AllIndexingCoordinatorSettings) this.settingsVariables.get("Indexing");
	}
	public AllHistogramCoordinatorSettings histogramCoordinator(){
		return (AllHistogramCoordinatorSettings) this.settingsVariables.get("Histogram");
	}


}
