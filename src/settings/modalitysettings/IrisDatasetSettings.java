package settings.modalitysettings;

import java.io.File;

import settings.settingsvariables.SettingsDropDownItem;

public class IrisDatasetSettings extends DatasetSettings{
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void addALLOptions() {
		File directory = new File("datasets/iris");
		File[] files = directory.listFiles();
		
		for(File f : files){
			if(f.isFile()){
				this.addToOptions(new SettingsDropDownItem(f.getName()));
			}
		}
		
	}

}
