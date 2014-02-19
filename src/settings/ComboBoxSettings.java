package settings;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class ComboBoxSettings extends Settings{

	private static final long serialVersionUID = 1L;
	
	protected transient JComboBox settingsBox;
	protected String variableString;
	protected transient JPanel cardPanel;
	protected int currentIndex = 0;
	
	protected void addToOptions(Settings settings){
		this.settingsBox.addItem(settings);
		this.settingsBox.validate();
		this.cardPanel.add(settings.getJPanel(), settings.getLabel());
	}
	
	protected ComboBoxSettings(){
//		this.addALLOptions();
	}

	protected abstract void addALLOptions();

	
	public void manuallySetComboBox(Settings comboBoxSettings){
		this.settingsVariables.put(this.variableString, comboBoxSettings);
	}
	
	
	@Override
	protected JPanel thisJPanel() {
		
		JPanel currentPanel = new JPanel();

		BoxLayout boxLayout = new BoxLayout(currentPanel, BoxLayout.X_AXIS);
		
		currentPanel.setLayout(boxLayout);
//		int ix = 0;
//		if(this.settingsBox != null){
//			ix = this.settingsBox.getSelectedIndex();
//		}
		this.settingsBox = new JComboBox();
		this.settingsBox.addActionListener(new SettingsComboBoxActionListener(this, this.variableString));
		this.settingsBox.setRenderer(new SettingsRenderer());
		
		this.settingsBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		currentPanel.add(settingsBox);
		
		return currentPanel;
	}

	
	@Override
	public JPanel makeChildrenJPanel(){
		
		this.cardPanel = new JPanel(new CardLayout());
//		this.cardPanel.setBorder(null);
		//System.out.println("made it here");
		this.addALLOptions();
		this.settingsBox.setSelectedIndex(this.currentIndex);
		return this.cardPanel;
	}
	
	
	
	public class SettingsComboBoxActionListener implements ActionListener{
		
		ComboBoxSettings sourceSettings;
		String variableName;
		
		public SettingsComboBoxActionListener(ComboBoxSettings sourceSettings, String variableName) {
			this.sourceSettings = sourceSettings;
			this.variableName = variableName;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			JComboBox sourceBox = (JComboBox) e.getSource();
			Settings selectedSettings = (Settings) sourceBox.getSelectedItem();
			
			sourceSettings.settingsVariables.put(variableName, selectedSettings);
			CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
			cardLayout.show(cardPanel, selectedSettings.getLabel());
			    
//			sourceBox.validate();
//			AllSettings.updateGUI();
			sourceBox.revalidate();
			sourceBox.getParent().repaint();
			sourceBox.getParent().validate();
			sourceBox.setAlignmentX(Component.LEFT_ALIGNMENT);

//			sourceSettings.currentIndex = sourceBox.getSelectedIndex();
		}
		
		
	}
		
	
}
