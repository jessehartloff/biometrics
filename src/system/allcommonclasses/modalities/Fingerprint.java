package system.allcommonclasses.modalities;

import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.allcommonclasses.settings.GlobalSettings;

/**
 * A fingerprint.
 *
 */
public class Fingerprint extends Biometric{

	public ArrayList<Minutia> minutiae;
	
	/**
	 * When creating a fingerprint, it's quantization method must be specified.
	 * 
	 * @param method the method determining how the print will be quantized for template generation.
	 */
	public Fingerprint(){
		this.minutiae = new ArrayList<Minutia>();
	}
	

	public Template quantizeOne(){
		return GlobalSettings.fingerprintMethod.quantizeOne(this);
	}

	

	public ArrayList<Template> quantizeAll(){
		return GlobalSettings.fingerprintMethod.quantizeAll(this);
	}
	
	/**
	 * Rotate the fingerprint around the point (0,0)
	 * 
	 * @param toRotate reference to the fingerprint that will store the rotated fingerprint
	 * @param degrees the amount of rotation in degrees
	 */
	public Fingerprint rotate(double degrees){
		return rotate(degrees, 0, 0);
	}
	
	/**
	 * Rotate the fingerprint around the point (centerX,centerY)
	 * 
	 * @param toRotate reference to the fingerprint variable that will store the rotated fingerprint
	 * @param degrees the amount of rotation in degrees
	 * @param centerX
	 * @param centerY
	 */
	public Fingerprint rotate(double degrees, int centerX, int centerY){
		
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
	public Fingerprint translate(int dx, int dy){

		Fingerprint toTranslate = new Fingerprint();
		
		for(Minutia minutia : this.minutiae){
			
			Minutia translatedMinutia = new Minutia();
			
			translatedMinutia.setX(minutia.getX() + dx);
			translatedMinutia.setY(minutia.getY() + dy);
			translatedMinutia.setTheta(minutia.getTheta());
			translatedMinutia.setConfidence(minutia.getConfidence());
			
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
	
}
