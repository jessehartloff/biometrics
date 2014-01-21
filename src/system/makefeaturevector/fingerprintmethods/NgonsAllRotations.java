package system.makefeaturevector.fingerprintmethods;

import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.makefeaturevector.feature.Feature;

public class NgonsAllRotations extends Ngons{
	
	public NgonsAllRotations(){
	}
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint){
		return this.ngonsAllRotationsQuantizeOne(fingerprint);
	}
	
	private Template ngonsAllRotationsQuantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<Ngon> ngons = super.fingerprintToNgons(fingerprint);
		ArrayList<Ngon> ngonCopy = new ArrayList<Ngon>();
		
		ngonCopy.addAll(ngons);
		
		for(Ngon ngon : ngons){
			Collections.sort(ngonCopy, ngon.getComparator());
			
			int startingIndex;
			for(startingIndex = 0;
					ngonCopy.get(startingIndex).distanceBetweenCenters(ngon) < 0.0001;
					startingIndex++);
			ArrayList<Ngon> ngonsToTry = new ArrayList<Ngon>();
			ngonsToTry.add(ngon);
			
			for(int i = 0; i < settings.getkClosestMinutia(); i++){
				ngonsToTry.add(ngonCopy.get(startingIndex+i));
			}
			//ngonsToTry.addAll(arg0);
			
		}
		
		
		return null; //<------
	}
	
	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint){
		return this.ngonsAllRotationsQuantizeAll(fingerprint);
	}
	
	private ArrayList<Template> ngonsAllRotationsQuantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>();
		for(double rotation = settings.getRotationStart();
				rotation < settings.getRotationStop(); 
				rotation += settings.getRotationStep()){
			Fingerprint rotatedPrint = fingerprint.rotate(rotation);
			templates.add(this.ngonsQuantizeOne(fingerprint));
		}
		return templates;
	}
	
	

	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint){
		return new ArrayList<Feature>(this.fingerprintToNgons(fingerprint));
	}
	
	
	@Override
	public Feature getBlankFeatureForBinning(){
		return new Ngon();
	}
	
	@Override
	public Feature getBlankFeatureForTotalBits(){
		return new Ngon();
	}

}
