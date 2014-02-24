package system.method.irismethods;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Iris;
import system.method.Method;
import system.method.feature.Feature;

public abstract class IrisMethod extends Method{



	public abstract Template quantizeOne(Iris iris); 


	public abstract ArrayList<Template> quantizeAll(Iris iris);
	
	
	public abstract ArrayList<Feature> irisToFeatures(Iris iris);



}
