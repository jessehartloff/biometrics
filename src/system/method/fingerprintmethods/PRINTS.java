package system.method.fingerprintmethods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import settings.fingerprintmethodsettings.PRINTSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Minutia;
import system.method.feature.DistanceVariable;
import system.method.feature.Feature;
import system.method.feature.PhiVariable;
import system.method.feature.RegionVariable;
import system.method.feature.SigmaVariable;

public class PRINTS extends FingerprintMethod{

	private PRINTSettings settings;
	protected Long N;
	
	public class PRINT extends Feature implements Comparable<PRINT>{
		public HashSet<Long> minutiaIndices;


		private Double centerX;
		private Double centerY;
		private Long N;
		
		public class PRINTComparator implements Comparator<PRINT>{
			PRINT reference;
			
			public PRINTComparator(PRINT referencePoint){
				this.reference = referencePoint;
			}
	
			@Override
			public int compare(PRINT arg0, PRINT arg1) {
				Double d0 = this.reference.distanceBetweenCenters(arg0);
				Double d1 = this.reference.distanceBetweenCenters(arg1);
				return d0.compareTo(d1);
			}
		}
		
		public PRINTComparator getComparator(){
			return new PRINTComparator(this);
		}
		
		public PRINT(){
			this.N = settings.n().getValue();
			this.minutiaIndices = new HashSet<Long>();
			this.setCenterX(0.0);
			this.setCenterY(0.0);			
			variables.put("angle", new RegionVariable(settings.getAngle()));
			for(Long i = 0L; i < N; i ++){
				variables.put(makeKey("distance", i), new DistanceVariable(settings.getMinutiaComponentVariable("distance", i)));
				variables.put(makeKey("sigma",i), new SigmaVariable(settings.getMinutiaComponentVariable("sigma", i)));
				variables.put(makeKey("phi",i), new PhiVariable(settings.getMinutiaComponentVariable("phi", i)));
			}
		}

		public Double getCenterX() {
			return centerX;
		}

		public void setCenterX(Double centerX) {
			this.centerX = centerX;
		}

		public Double getCenterY() {
			return centerY;
		}

		public void setCenterY(Double centerY) {
			this.centerY = centerY;
		}

		@Override
		public int compareTo(PRINT that) {
			int compareX = this.centerX.compareTo(that.centerX);
			return compareX == 0 ? this.centerY.compareTo(that.centerY) : compareX;
		}
		public Double distanceBetweenCenters(PRINT that) {
			Double distance;
			Double dx = this.getCenterX() - that.getCenterX();
			Double dy = this.getCenterY() - that.getCenterY();
			distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			return distance;
		}
		
	}
	
	public PRINTS() {
		settings = PRINTSettings.getInstance();

		this.settings.setAllNumberOfBins(); // initializes the method variable settings (bins and bits)
		this.N = this.settings.n().getValue();

	}

	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return this.PRINTSQuantizeOne(fingerprint);
	}
	
	//used for enrollment
	private Template PRINTSQuantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<PRINT> prints = this.fingerprintToPRINTS(fingerprint);
		for(PRINT print : prints){
			template.hashes.add(print.toBigInt());
		}
		return template;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		return this.PRINTSQuantizeAll(fingerprint);
	}
	//used for matching
	private ArrayList<Template> PRINTSQuantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>();
		return templates;
	}
	

	private ArrayList<PRINT> fingerprintToPRINTS(Fingerprint fingerprint) {
		ArrayList<PRINT> prints = new ArrayList<PRINT>();
		ArrayList<Minutia> minutiaeCopy = new ArrayList<Minutia>();
		
		for(Minutia minutia : fingerprint.minutiae){
			minutiaeCopy.add(minutia);
		}
		
		for(Minutia minutia : fingerprint.minutiae){
			Collections.sort(minutiaeCopy, minutia.getComparator());
			int startingIndex;
			for(startingIndex = 0; minutiaeCopy.get(startingIndex).distanceTo(minutia) < 0.0001; startingIndex++);
			ArrayList<Minutia> minutiae = new ArrayList<Minutia>();
			for(int i = 0; i < settings.kClosestMinutia().getValue(); i++){
				minutiae.add(minutiaeCopy.get(startingIndex+i));
			}
			prints.add(this.makePRINT(minutiae));
		}
		return prints;
	}

	private PRINT makePRINT(ArrayList<Minutia> minutiaList) {
		PRINT returnPRINT = new PRINT();
		
		for(Minutia minutia : minutiaList){
			returnPRINT.setCenterX(returnPRINT.getCenterX() + minutia.getX());
			returnPRINT.setCenterY(returnPRINT.getCenterY() + minutia.getY());
		}
		
		returnPRINT.setCenterX(returnPRINT.getCenterX() /(float) settings.n().getValue());
		returnPRINT.setCenterY(returnPRINT.getCenterY() /(float) settings.n().getValue());
		
		ArrayList<Double> distances = new ArrayList<Double>();
		ArrayList<Double> sigmas = new ArrayList<Double>();
		ArrayList<Double> phis = new ArrayList<Double>();
		
		
		Double cx = returnPRINT.getCenterX().doubleValue();
		Double cy = returnPRINT.getCenterY().doubleValue();
		for(Minutia minutia : minutiaList){
		}
		
		for(int i = 0;i < minutiaList.size(); i ++){
			Minutia m1 = minutiaList.get(i), m2 = minutiaList.get( (i+1) % minutiaList.size() ); //going around counterclockwise...
			
			Double distFromCenter = Minutia.distance(cx, cy, m1.getX().doubleValue(), m1.getY().doubleValue());
			Double phi = Minutia.computeInsideAngle(m1, cx, cy, m2);
			Double sigma = m2.getTheta().doubleValue() - m1.getTheta().doubleValue();

			//ith position in each list corresponds to the ith minutia point
			distances.add(distFromCenter);
			phis.add(phi);
			sigmas.add(sigma);
		}
		
		
		int startingIndex = 0;
		double maxDist = 0;
		for(int i = 0; i < distances.size(); i++){
			double currentDistance = distances.get(i);
			if( currentDistance > maxDist){
				maxDist = currentDistance;
				startingIndex = i;
			}
		}
		Minutia m0 = minutiaList.get(0);
		
		Double angle = Math.tan(m0.getY().doubleValue()/m0.getX().doubleValue());//verify the angle is from the positive x axis... shouldn't make a difference
		returnPRINT.variables.get("angle").setPrequantizedValue(angle.longValue());
		
		for(Long i = 0L; i < N; i++){
			int index = (startingIndex+i.intValue()) % N.intValue();
			//worth thinking about overloading setPrequantizedValue if Double has more bits than Long?
			returnPRINT.variables.get(makeKey("distance", i)).setPrequantizedValue(distances.get(index).longValue());
			returnPRINT.variables.get(makeKey("sigma", i)).setPrequantizedValue(sigmas.get(index).longValue());
			returnPRINT.variables.get(makeKey("phi", i)).setPrequantizedValue(phis.get(index).longValue());
		}
		
		return returnPRINT;
	}

	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint) {
		return new ArrayList<Feature>(this.fingerprintToPRINTS(fingerprint));
	}

	public static String makeKey(String component, Long i){
		return component+i.toString();
	}
	
	@Override
	public Feature getBlankFeatureForTraining() {
		return new PRINT();
	}

}
