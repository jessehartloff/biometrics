package system.method.fingerprintmethods;

import java.util.ArrayList;

import settings.fingerprintmethodsettings.NgonsSingleEnrollAllRotationsSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;

public class NgonsSingleEnrollAllRotations extends NgonsAllRotations{

	private NgonsSingleEnrollAllRotationsSettings settings;
	
	public NgonsSingleEnrollAllRotations(){
		super(NgonsSingleEnrollAllRotationsSettings.getInstance());
	}
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint){
		return this.ngonsSingleEnrollAllRotationsQuantizeOne(fingerprint);
	}
	
	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint){
		return this.ngonsSingleEnrollAllRotationsQuantizeAll(fingerprint);
	}

	private ArrayList<Template> ngonsSingleEnrollAllRotationsQuantizeAll(Fingerprint fingerprint) {
		return this.ngonsAllRotationsQuantizeAll(fingerprint);
	}

	private Template ngonsSingleEnrollAllRotationsQuantizeOne(Fingerprint fingerprint) {
		return this.ngonsQuantizeOne(fingerprint);
	}
	
}
