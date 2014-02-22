package system.method.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.method.feature.Feature;

public class NgonsAllRotations extends Ngons{

	public NgonsAllRotations(){
		super();
	}
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint){
		return this.ngonsAllRotationsQuantizeOne(fingerprint);
	}
	
	private  Template ngonsAllRotationsQuantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<Ngon> ngons = super.fingerprintToNgons(fingerprint);
		ArrayList<Template> rotatedTemplates = super.ngonsQuantizeAll(fingerprint);
		for(Template temp : rotatedTemplates){
			for(BigInteger bigInt : temp.hashes){
				template.hashes.add(bigInt);
			}
		}
		
		return template;
	}
	
	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint){
		return this.ngonsAllRotationsQuantizeAll(fingerprint);
	}
	
	private ArrayList<Template> ngonsAllRotationsQuantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> template = new ArrayList<Template>();
		template.add(this.ngonsAllRotationsQuantizeOne(fingerprint));
		return template;
	}
	
	

	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint){
		return new ArrayList<Feature>(this.fingerprintToNgons(fingerprint));
	}
	
	
	@Override
	public Feature getBlankFeatureForTraining(){
		return new Ngon();
	}
	
	@Override
	public Feature getBlankFeatureForTotalBits(){
		return new Ngon();
	}

}
