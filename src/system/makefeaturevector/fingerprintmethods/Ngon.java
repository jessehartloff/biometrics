package system.makefeaturevector.fingerprintmethods;

import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.settings.TriangleSettings;
import system.makefeaturevector.feature.Feature;

public class Ngon extends FingerprintMethod{

	{}// TODO -n-gons

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
		TriangleSettings.getInstance().getkClosestMinutia();
		return null;
	}

	@Override
	public Feature getBlankFeatureForBinning() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
