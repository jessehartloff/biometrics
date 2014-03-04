package system.method.fingerprintmethods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;

import settings.fingerprintmethodsettings.NgonSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Minutia;
import system.method.feature.Feature;
import system.method.feature.ThetaVariable;
import system.method.feature.Variable;
import system.method.feature.XYVariable;
import system.method.fingerprintmethods.Triangles.Triangle;

public class Ngons extends FingerprintMethod{

	protected NgonSettings settings;
	public Long N;

	
	public class Ngon extends Feature implements Comparable<Ngon>{
//		private NgonSettings ngonSettings;
		public HashSet<Long> minutiaIndices;

		private Double centerX;
		private Double centerY;
		private Long innerN;

		public class NgonComparator implements Comparator<Ngon>{
			Ngon referencePoint;
			
			public NgonComparator(Ngon referencePoint){
				this.referencePoint = referencePoint;
			}
			
			@Override
			public int compare(Ngon arg0, Ngon arg1) {
				Double d0 = referencePoint.distanceBetweenCenters(arg0);
				Double d1 = referencePoint.distanceBetweenCenters(arg1);
				return d0.compareTo(d1);
			}
			
		}
		
		public NgonComparator getComparator(){
			return new NgonComparator(this);
		}

		public Ngon(){
			this.setCenterX(0.0);
			this.setCenterY(0.0);
			innerN = settings.n().getValue();
//			this.ngonSettings = settings;
			this.minutiaIndices = new HashSet<Long>();
			variables.put("theta0", new ThetaVariable(settings.getMinutiaComponentVariable("theta", 0L)));
			for(Long i = 1L; i < innerN; i++){
				variables.put(makeKey("x", i), new XYVariable(settings.getMinutiaComponentVariable("x", i)));
				variables.put(makeKey("y", i), new XYVariable(settings.getMinutiaComponentVariable("y", i)));
				variables.put(makeKey("theta", i), new ThetaVariable(settings.getMinutiaComponentVariable("theta", i)));
			}
		}
		
		public String makeKey(String component, Long i){
			return component+i.toString();
		}
		
		public Double distanceBetweenCenters(Ngon that) {
			Double distance;
			Double dx = this.getCenterX() - that.getCenterX();
			Double dy = this.getCenterY() - that.getCenterY();
			distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			return distance;
		}

		@Override
		public int compareTo(Ngon that) {
			int compareX = this.centerX.compareTo(that.centerX);
			return compareX == 0 ? this.centerY.compareTo(that.centerY) : compareX;
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
		
	}
	public Ngons(){
		this.settings = NgonSettings.getInstance();
		this.settings.setAllNumberOfBins(); // initializes the method variable settings (bins and bits)
		this.N = this.settings.n().getValue(); 
	}
	

	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return this.ngonsQuantizeOne(fingerprint);
	}


	public Template ngonsQuantizeOne(Fingerprint fingerprint){
		Template template = new Template();
		ArrayList<Ngon> ngons = this.fingerprintToNgons(fingerprint);
		for(Ngon ngon : ngons){
			template.hashes.add(ngon.toBigInt());
		}
		return template;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		return this.ngonsQuantizeAll(fingerprint);
	}
	
	public ArrayList<Template> ngonsQuantizeAll(Fingerprint fingerprint){
		ArrayList<Template> templates = new ArrayList<Template>();
		for(double rotation=settings.rotationStart().getValue(); 
				rotation < settings.rotationStop().getValue(); 
				rotation += settings.rotationStep().getValue()){
			Fingerprint rotatedPrint = fingerprint.rotate(rotation);
			templates.add(this.ngonsQuantizeOne(rotatedPrint));
		}
		return templates;
	}

	protected ArrayList<Ngon> fingerprintToNgons(Fingerprint fingerprint) {
		ArrayList<Ngon> ngons = new ArrayList<Ngon>();
		ArrayList<Minutia> minutiaeCopy = new ArrayList<Minutia>();
		
		for(Minutia minutia : fingerprint.minutiae){
			minutiaeCopy.add(minutia);
		}
		for(Minutia minutia : fingerprint.minutiae){
			Collections.sort(minutiaeCopy, minutia.getComparator());
			int startingIndex;
			for(startingIndex=0; minutiaeCopy.get(startingIndex).distanceTo(minutia) < 0.0001; startingIndex++);
			ArrayList<Minutia> minutiae = new ArrayList<Minutia>();
			minutiae.add(minutia);
			for(int i=0; i < settings.kClosestMinutia().getValue(); i++){
				minutiae.add(minutiaeCopy.get(startingIndex+i));
			}
			ngons.addAll(this.makeAllPossibleNgons(minutiae));
			
		}
		return ngons;
	}	

	
	public Ngon makeNgon(ArrayList<Minutia> minutiaList){
		Ngon ngonToReturn = new Ngon();
		Collections.sort(minutiaList);
		Minutia m0 = minutiaList.get(0);
		
		for(Minutia minutia : minutiaList){
			ngonToReturn.setCenterX(ngonToReturn.getCenterX() + minutia.getX());
			ngonToReturn.setCenterY(ngonToReturn.getCenterY() + minutia.getY());
		}
		
		ngonToReturn.setCenterX(ngonToReturn.getCenterX() / settings.n().getValue());
		ngonToReturn.setCenterY(ngonToReturn.getCenterY() / settings.n().getValue());
		
		ngonToReturn.variables.get("theta0").setPrequantizedValue(minutiaList.get(0).getTheta().doubleValue());
		for(Long i = 1L; i < settings.n().getValue(); i++){
			ngonToReturn.variables.get(ngonToReturn.makeKey("x", i)).setPrequantizedValue(minutiaList.get(i.intValue()).getX().doubleValue() - m0.getX().doubleValue());
			ngonToReturn.variables.get(ngonToReturn.makeKey("y", i)).setPrequantizedValue(minutiaList.get(i.intValue()).getY().doubleValue() - m0.getY().doubleValue());
			ngonToReturn.variables.get(ngonToReturn.makeKey("theta", i)).setPrequantizedValue(minutiaList.get(i.intValue()).getTheta().doubleValue());		
		}
		
		return ngonToReturn;
	}
	
	
	private ArrayList<Ngon> makeAllPossibleNgons(ArrayList<Minutia> minutiae) {
		return recursiveNgonBuilder(minutiae, new ArrayList<Minutia>());
	}


	public ArrayList<Ngon> recursiveNgonBuilder(ArrayList<Minutia> minutiae, ArrayList<Minutia> currentNgon){
		if(currentNgon.size() == settings.n().getValue()){
			ArrayList<Ngon> baseCaseList = new ArrayList<Ngon>();
			baseCaseList.add(this.makeNgon(currentNgon));
			return baseCaseList;
		} else {
			ArrayList<Ngon> intermediaryNgons = new ArrayList<Ngon>();
			for(Minutia minutia : minutiae){
				if(currentNgon.contains(minutia)){
					return intermediaryNgons;
				} else {
					currentNgon.add(minutia);
					intermediaryNgons.addAll(recursiveNgonBuilder(minutiae, currentNgon));
					currentNgon.remove(currentNgon.size()-1);
				}
			}
			return intermediaryNgons;
		}
		
	}

	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint) {
		return new ArrayList<Feature>(this.fingerprintToNgons(fingerprint));
	}

	@Override
	public Feature getBlankFeatureForTraining() {
		return new Ngon();
	}
	

}
