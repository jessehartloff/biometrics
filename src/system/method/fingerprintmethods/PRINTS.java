package system.method.fingerprintmethods;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;

import settings.fingerprintmethodsettings.PRINTSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Minutia;
import system.method.feature.DistanceVariable;
import system.method.feature.Feature;
import system.method.feature.PhiVariable;
import system.method.feature.SigmaVariable;

public class PRINTS extends FingerprintMethod{

	private PRINTSettings settings;
	protected Long N;
	
	public class PRINT extends Feature implements Comparable<PRINT>{
		public HashSet<Long> minutiaIndices;


		private Double centerX;
		private Double centerY;
		private Long N;
		private Double angle;
		
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
			for(Long i = 0L; i < N; i ++){
				variables.put(makeKey("distance", i), new DistanceVariable(settings.getMinutiaComponentVariable("distance", i)));
				variables.put(makeKey("sigma",i), new SigmaVariable(settings.getMinutiaComponentVariable("sigma", i)));
				variables.put(makeKey("phi",i), new PhiVariable(settings.getMinutiaComponentVariable("phi", i)));
			}
		}
		@Override
		public BigInteger toBigInt(){
			BigInteger featureBigInt = super.toBigInt();
			BigInteger toReturn = featureBigInt.shiftLeft(settings.rotationRegions().getValue().intValue());
			BigInteger regionNumber = BigInteger.valueOf( new Double(Math.floor(this.angle/360.0*settings.rotationRegions().getValue().doubleValue())).intValue());
			toReturn.add(regionNumber);
			return toReturn;
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

		public Double getAngle(){
			return this.angle;
		}
		
		public void setAngle(Double angle){
			this.angle = angle;
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
		Template template = new Template();
		ArrayList<PRINT> prints = this.fingerprintToPRINTS(fingerprint);
		for(PRINT print : prints){
			BigInteger quantizedPRINT = print.toBigInt();
			BigInteger regionNumber = this.getRegionBigInteger(quantizedPRINT);
			template.hashes.add(quantizedPRINT);
			template.hashes.add(quantizedPRINT.add( BigInteger.valueOf(1)));
			template.hashes.add(quantizedPRINT.add( BigInteger.valueOf(-1)));
		}
		
		return templates;
	}
	
	public  BigInteger getRegionBigInteger(BigInteger integer){
		String regionNumberDigits = this.settings.rotationRegions().getValue().toString();
		BigInteger regionNumber = integer.remainder(new BigDecimal(Math.pow(10,regionNumberDigits.length())).toBigInteger());
		return regionNumber;
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
			minutiae.add(minutia);
			for(int i = 0; i < settings.kClosestMinutia().getValue(); i++){
				minutiae.add(minutiaeCopy.get(startingIndex+i));
			}
			prints.add(this.makePRINT(minutiae));
		}
		return prints;
	}
	
	
	public static boolean isLeft(Minutia center, Minutia linePoint, Minutia pointToCheck){
		return PRINTS.isLeft(center.getX().doubleValue(), center.getY().doubleValue(), linePoint, pointToCheck);
				//returns true if pointToCheck is left of the line made by linePointA and linePointB
				//note: returns true if pointTCheck is above the horizontal line made by A and B
	}
	
	public static boolean isLeft(Double centerX, Double centerY, Minutia linePoint, Minutia pointToCheck){
		return ((linePoint.getX() - centerX)*(pointToCheck.getY() - centerY)
				- (linePoint.getY() - centerY)*(pointToCheck.getX() - centerX)) > 0;
				//returns true if pointToCheck is left of the line made by linePointA and linePointB
				//note: returns true if pointTCheck is above the horizontal line made by A and B
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

		
		ArrayList<Minutia> minutiaToSortDistance = new ArrayList<Minutia>();
		LinkedHashMap<Double, Integer> indexDistanceMinutia = new LinkedHashMap<Double, Integer>();
		
		Double cx = returnPRINT.getCenterX().doubleValue();
		Double cy = returnPRINT.getCenterY().doubleValue();
		
		for(int i = 0;i < minutiaList.size(); i ++){
			Minutia minutia = minutiaList.get(i);
			Double distFromCenter = Minutia.distance(cx, cy, minutia.getX().doubleValue(), minutia.getY().doubleValue());

			distances.add(distFromCenter);
			indexDistanceMinutia.put(distFromCenter, i);
			minutiaToSortDistance.add(minutia);
		}
		
		Collections.sort(distances);
		Minutia m0 = minutiaToSortDistance.get(indexDistanceMinutia.get(distances.get(0)));
		
		ArrayList<Double> absoluteInteriorAngles = new ArrayList<Double>();
		LinkedHashMap<Double, Integer> indexInteriorAngleMinutia = new LinkedHashMap<Double, Integer>();

		ArrayList<Minutia> minutiaToSortAngles = new ArrayList<Minutia>();

		for(int i = 0;i < minutiaList.size(); i ++){
			Minutia minutia = minutiaList.get(i);
			Double intAngle = Minutia.computeInsideAngle(m0, cx, cy, minutia);
			if(isLeft(cx,cy, m0, minutia)){
				intAngle  = (intAngle + 180) % 360;
			}
			
			absoluteInteriorAngles.add(intAngle);
			indexInteriorAngleMinutia.put(intAngle, i);
			minutiaToSortAngles.add(minutia);
		}
		
		Collections.sort(absoluteInteriorAngles);
		
		ArrayList<Minutia> sortedMinutia = new ArrayList<Minutia>();
		for(Double interiorAngle : absoluteInteriorAngles){
			Minutia m = minutiaToSortAngles.get(indexInteriorAngleMinutia.get(interiorAngle));
			sortedMinutia.add(m);
		}
		
		distances = new ArrayList<Double>();
		ArrayList<Double> sigmas = new ArrayList<Double>();
		ArrayList<Double> phis = new ArrayList<Double>();
		
		for(int i = 0;i < sortedMinutia.size(); i ++){
			Minutia m1 = sortedMinutia.get(i), m2 = sortedMinutia.get((i+1) % sortedMinutia.size()); //going around counterclockwise...
			Double distance = Minutia.distance(cx, cy, m1.getX().doubleValue(), m1.getY().doubleValue());
			Double phi = Minutia.computeInsideAngle(m1, cx, cy, m2);
			Double sigma = m2.getTheta().doubleValue() - m1.getTheta().doubleValue();

			//ith position in each list corresponds to the ith minutia point
			distances.add(distance);
			phis.add(phi);
			sigmas.add(sigma);
		}
		
		//Double angle = Math.tan(m0.getY().doubleValue()/m0.getX().doubleValue());//verify the angle is from the positive x axis... shouldn't make a difference
		Double angle = Minutia.computeInsideAngle(m0, cx, cy, new Minutia(0,0,0));//verify the angle is from the positive x axis... shouldn't make a difference

		
		returnPRINT.setAngle(angle);
		
		for(Long i = 0L; i < sortedMinutia.size(); i++){
			//worth thinking about overloading setPrequantizedValue if Double has more bits than Long?
			returnPRINT.variables.get(makeKey("distance", i)).setPrequantizedValue(distances.get(i.intValue()).longValue());
			returnPRINT.variables.get(makeKey("sigma", i)).setPrequantizedValue(sigmas.get(i.intValue()).longValue());
			returnPRINT.variables.get(makeKey("phi", i)).setPrequantizedValue(phis.get(i.intValue()).longValue());
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
