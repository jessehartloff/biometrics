package settings.modalitysettings;

import java.io.File;

import settings.ComboBoxSettings;
import settings.coordinatorsettings.ACoordinatorSettings;
import settings.coordinatorsettings.HistogramCoordinatorSettings;
import settings.coordinatorsettings.HistogramSettings;
import settings.coordinatorsettings.NoCoordinator;
import settings.settingsvariables.SettingsDropDownItem;

public class FingerprintDatasetSettings extends ADatasetSettings{
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void addALLOptions() {
		File directory = new File("datasets");
		File[] files = directory.listFiles();
		
		for(File f : files){
			if(f.isFile()){ //TODO make this look pretty by removing the .ser
				this.addToOptions(new SettingsDropDownItem(f.getName()));
			}
		}
		
	}


}
