package system.method.fingerprintmethods;

import java.util.ArrayList;

import settings.fingerprintmethodsettings.PRINTSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.method.feature.Feature;

public class PRINTS extends FingerprintMethod{

	PRINTSettings settings;
	
	public PRINTS() {
		settings = PRINTSettings.getInstance();
		this.settings.setAllNumberOfBins(); // initializes the method variable settings (bins and bits)
	}

	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Feature getBlankFeatureForBinning() {
		// TODO Auto-generated method stub
		return null;
	}

}
