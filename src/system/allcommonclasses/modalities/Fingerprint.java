package system.allcommonclasses.modalities;

import java.util.ArrayList;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.settings.GlobalSettings;
import system.makefeaturevector.feature.Feature;
import system.makefeaturevector.fingerprintmethods.FingerprintMethod;

/**
 * A fingerprint.
 *
 */
public class Fingerprint extends Biometric{

	private static final long serialVersionUID = 1L;
	
	public ArrayList<Minutia> minutiae;

	private static FingerprintMethod fingerprintMethod; 
	
	/**
	 * When creating a fingerprint, it's quantization method must be specified.
	 * 
	 * @param method the method determining how the print will be quantized for template generation.
	 */
	public Fingerprint(){
		this.minutiae = new ArrayList<Minutia>();
	}
	
	@Override
	public Template quantizeOne(){
//		System.out.println("this: " + this.minutiae.get(0).getIndex());
		return Fingerprint.fingerprintMethod.quantizeOne(this);
	}

	
	@Override
	public ArrayList<Template> quantizeAll(){
		return Fingerprint.fingerprintMethod.quantizeAll(this);
	}
	

	@Override
	public ArrayList<Feature> toFeatures() {
		return Fingerprint.fingerprintMethod.fingerprintToFeatures(this);
	}

	
	/**
	 * Rotate the fingerprint around the point (0,0)
	 * 
	 * @param toRotate reference to the fingerprint that will store the rotated fingerprint
	 * @param degrees the amount of rotation in degrees
	 */
	public Fingerprint rotate(double degrees){
		return rotate(degrees, 0L, 0L);
	}
	
	/**
	 * Rotate the fingerprint around the point (centerX,centerY)
	 * 
	 * @param toRotate reference to the fingerprint variable that will store the rotated fingerprint
	 * @param degrees the amount of rotation in degrees
	 * @param centerX
	 * @param centerY
	 */
	public Fingerprint rotate(double degrees, Long centerX, Long centerY){
		
		Fingerprint toRotate = new Fingerprint();
		for(Minutia minutia : this.minutiae){
			Minutia rotatedMinutia = new Minutia();
			
			rotatedMinutia.setX(Math.round(Math.cos(degrees*(Math.PI/180))*(minutia.getX()-centerX)
					- Math.sin(degrees*(Math.PI/180))*(minutia.getY()-centerY)
					+ centerX));
			
			rotatedMinutia.setY(Math.round(Math.sin(degrees*(Math.PI/180))*(minutia.getX()-centerX)
					+ Math.cos(degrees*(Math.PI/180))*(minutia.getY()-centerY)
					+ centerY));

			rotatedMinutia.setTheta(minutia.getTheta() + Math.round(degrees)); 

			rotatedMinutia.setConfidence(minutia.getConfidence());
			rotatedMinutia.setIndex(minutia.getIndex());
			
			toRotate.minutiae.add(rotatedMinutia);
			
		}
		return toRotate;
	}

	/**
	 * Translates a fingerprint by (dx,dy)
	 * 
	 * @param toTranslate reference to the fingerprint variable that will store the translated fingerprint
	 * @param dx
	 * @param dy
	 */
	public Fingerprint translate(Long dx, Long dy){

		Fingerprint toTranslate = new Fingerprint();
		
		for(Minutia minutia : this.minutiae){
			
			Minutia translatedMinutia = new Minutia();
			
			translatedMinutia.setX(minutia.getX() + dx);
			translatedMinutia.setY(minutia.getY() + dy);
			translatedMinutia.setTheta(minutia.getTheta());
			translatedMinutia.setConfidence(minutia.getConfidence());
			translatedMinutia.setIndex(minutia.getIndex());
			
			toTranslate.minutiae.add(translatedMinutia);
		}
		
		return toTranslate;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null){
	    	return false;
	    }
	    if (other == this){
	    	return true;
	    }
	    if (!(other instanceof Fingerprint)){
	    	return false;
	    }
	    
	    Fingerprint otherFingerprint = (Fingerprint)other;

	    if(this.minutiae.size() != otherFingerprint.minutiae.size()){
	    	return false;
	    }
	    for(Minutia m : this.minutiae){
	    	if(!(otherFingerprint.minutiae.contains(m))){
	    		return false;
	    	}
	    }
	    for(Minutia m : otherFingerprint.minutiae){
	    	if(!(this.minutiae.contains(m))){
	    		return false;
	    	}
	    }
	    return true;
	}
	
	@Override
	public String toString(){
		return this.minutiae.toString();
	}

	
	public static FingerprintMethod getFingerprintMethod() {
		return fingerprintMethod;
	}

	public static void setFingerprintMethod(FingerprintMethod fingerprintMethod) {
		Fingerprint.fingerprintMethod = fingerprintMethod;
		Biometric.method = fingerprintMethod;
	}

	
}
