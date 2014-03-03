package settings.modalitysettings;

import java.io.File;

import settings.ComboBoxSettings;
import settings.coordinatorsettings.CoordinatorSettings;
import settings.coordinatorsettings.AllHistogramCoordinatorSettings;
import settings.coordinatorsettings.HistogramSettings;
import settings.coordinatorsettings.NoCoordinator;
import settings.settingsvariables.SettingsDropDownItem;

public class FingerprintDatasetSettings extends DatasetSettings{
	private static final long serialVersionUID = 1L;
	private String initialSet;
	
	public FingerprintDatasetSettings(String initialSet){
		super();
		this.initialSet = initialSet;
	}

	@Override
	protected void addALLOptions() {
		File directory = new File("datasets/fingerprint");
		File[] files = directory.listFiles();

		for(File f : files){
			if(f.isFile() && (f.getName().equalsIgnoreCase(this.initialSet))){
				this.addToOptions(new SettingsDropDownItem(f.getName()));
			}
		}

		for(File f : files){
			if(f.isFile() && !(f.getName().equalsIgnoreCase(this.initialSet))){
				this.addToOptions(new SettingsDropDownItem(f.getName()));
			}
		}
		
	}


}
