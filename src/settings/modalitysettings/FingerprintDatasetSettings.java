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
	

	@Override
	protected void addALLOptions() {
		File directory = new File("datasets/fingerprint");
		File[] files = directory.listFiles();
		
		for(File f : files){
			if(f.isFile()){ //LATER make this look pretty by removing the .ser
				this.addToOptions(new SettingsDropDownItem(f.getName()));
			}
		}
		
	}


}
