package system.allcommonclasses.modalities;

import java.util.ArrayList;

import settings.modalitysettings.FingerprintSettings;
import system.allcommonclasses.commonstructures.Template;
import system.method.feature.Feature;
import system.method.fingerprintmethods.FingerprintMethod;
import system.method.irismethods.IrisMethod;

public class Iris extends Biometric{

	private static IrisMethod irisMethod;

	@Override
	public Template quantizeOne() {
		return Iris.irisMethod.quantizeOne(this);
	}

	@Override
	public ArrayList<Template> quantizeAll() {
		return Iris.irisMethod.quantizeAll(this);
	}

	@Override
	public ArrayList<Feature> toFeatures() {
		return Iris.irisMethod.irisToFeatures(this);
	} 

	@Override
	public ArrayList<Feature> toQuantizedFeatures() {
		return Iris.irisMethod.irisToQuantizedFeatures(this);
	} 
	
	@Override
	public boolean equals(Object other){
	    if (other == null){
	    	return false;
	    }
	    if (other == this){
	    	return true;
	    }
	    if (!(other instanceof Iris)){
	    	return false;
	    }
	    Iris otherIris = (Iris)other;

	    // code for equals
	    
	    return true;
	}
	
	@Override
	public String toString(){
		return "Iris.toString()";
	}

	
	public static IrisMethod getIrisMethod() {
		return Iris.irisMethod;
	}

	public static void setIrisMethod(IrisMethod irisMethod) {
		Iris.irisMethod = irisMethod;
		Biometric.method = irisMethod;
	}

	
	@Override
	public boolean isFailure() {
		// used for failure to capture
		return false;
	}

	
	
}
