package system.allcommonclasses.modalities;

import settings.modalitysettings.FingerprintSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.utilities.RidgeDistance;
import system.method.feature.Feature;
import system.method.fingerprintmethods.FingerprintMethod;

import java.util.*;

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
	 */
	public Fingerprint(){
		this.minutiae = new ArrayList<Minutia>();
        this.ridgeDistances = new HashSet<RidgeDistance>();
	}

    private Set<Minutia> minutiaePoints;
    private Template template = null;
    private int userNumber;
    private int readingNumber;

    private Set<RidgeDistance> ridgeDistances;

    public Set<RidgeDistance> getRidgeDistances() {
        return ridgeDistances;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public int getReadingNumber() {
        return readingNumber;
    }

    public void setReadingNumber(int readingNumber) {
        this.readingNumber = readingNumber;
    }

    public Set<Minutia> getMinutiaePoints() {
        return minutiaePoints;
    }

    public void setMinutiaePoints(Set<Minutia> minutiaePoints) {
        this.minutiaePoints = minutiaePoints;
    }


    public void trimLowQualityPoints(int minQuality, int maxPoints) {

        Set<Minutia> toRemove = new HashSet<Minutia>();
        List<Minutia> sortMinutia = new ArrayList<Minutia>();

        for(Minutia minutia : this.getMinutiaePoints()){
            sortMinutia.add(minutia);
        }

        Collections.sort(sortMinutia, new MinutiaReverseQualityComparator());

        int n = sortMinutia.size();
        int minutiaCollected = 0;

        for(int i=0; i<n; i++){
            Minutia currentMinutia = sortMinutia.get(i);
            if(currentMinutia.getQuality() < minQuality || minutiaCollected >= maxPoints){
                toRemove.add(currentMinutia);
            }else{
                minutiaCollected++;
            }
        }

        this.getMinutiaePoints().removeAll(toRemove);
//        System.out.println(this.getMinutiaePoints().size() + " points remaining");
    }

    public void setRidgeDistances(Set<RidgeDistance> ridgeDistances) {
        this.ridgeDistances = ridgeDistances;
    }



    public class MinutiaReverseQualityComparator implements Comparator<Minutia> {

        @Override
        public int compare(Minutia m1, Minutia m2) {
            Long q1 = m1.getQuality();
            Long q2 = m2.getQuality();
            return q2.compareTo(q1);
        }
    }

	@Override
	public Template quantizeOne(){
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

	@Override
	public ArrayList<Feature> toQuantizedFeatures() {
		return Fingerprint.fingerprintMethod.fingerprintToQuantizedFeatures(this);
	}
	
	/**
	 * Rotate the fingerprint around the point (0,0)
	 *
	 * @param degrees the amount of rotation in degrees
	 */
	public Fingerprint rotate(double degrees){
		return rotate(degrees, 0L, 0L);
	}
	
	/**
	 * Rotate the fingerprint around the point (centerX,centerY)
	 *
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
//		return this.minutiae.toString();
		Integer f = this.minutiae.size();
		return f.toString();
	}

	
	public static FingerprintMethod getFingerprintMethod() {
		return fingerprintMethod;
	}

	public static void setFingerprintMethod(FingerprintMethod fingerprintMethod) {
		Fingerprint.fingerprintMethod = fingerprintMethod;
		Biometric.method = fingerprintMethod;
	}

	@Override
	public boolean isFailure() {
		if(this.minutiae.size() < FingerprintSettings.getInstance().minimumMinutia().getValue().intValue()){
			return true;
		}
		return false;
	}
	
}
