package system.makefeaturevector.fingerprintmethods;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.settings.NgonSettings;
import system.makefeaturevector.feature.Feature;

public class Ngon extends FingerprintMethod{

	protected NgonSettings settings;
	private Long N;
	
	public Ngon(){
		this.settings = NgonSettings.getInstance();
		this.N = settings.getN();
		
	}
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return null;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		return null;
	}

	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint) {
		return null;
	}

	@Override
	public Feature getBlankFeatureForBinning() {
		return null;
	}

}
