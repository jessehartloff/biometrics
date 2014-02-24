package system.method.fingerprintmethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import settings.fingerprintmethodsettings.TriangleSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.*;
import system.method.feature.*;


/**
 * 
 * Determines how triangles are formed and quantized. Triangles are triplets of minutiae points and...
 * 
 *
 */
public class Triangles extends FingerprintMethod {
	
	private TriangleSettings settings;
	
	public class Triangle extends Feature implements Comparable<Triangle>{

		public class TriangleComparator implements Comparator<Triangle>{
			Triangle referencePoint;
			public TriangleComparator(Triangle referencePoint){
				this.referencePoint = referencePoint;			
			}
			@Override
			public int compare(Triangle t0, Triangle t1) {
				Double d0 = referencePoint.distanceBetweenCenters(t0);
				Double d1 = referencePoint.distanceBetweenCenters(t1);
				return d0.compareTo(d1);
			}
		}
		
		public TriangleComparator getComparator(){
			return new TriangleComparator(this);
		}
		
		public HashSet<Long> minutiaIndecies;
		
		private Double centerX;
		private Double centerY;
		
		public TriangleSettings innerSettings;

		public Triangle(){
			minutiaIndecies = new HashSet<Long>();
			this.innerSettings = settings;
			variables.put("theta0", new ThetaVariable(settings.theta0()));
			variables.put("x1", new XYVariable(settings.x1()));
			variables.put("y1", new XYVariable(settings.y1()));
			variables.put("theta1", new ThetaVariable(settings.theta1()));
			variables.put("x2", new XYVariable(settings.x2()));
			variables.put("y2", new XYVariable(settings.y2()));
			variables.put("theta2", new ThetaVariable(settings.theta2()));
		}
		
		public Double distanceBetweenCenters(Triangle that){
			Double distance;
			Double dx = this.getCenterX() - that.getCenterX();
			Double dy = this.getCenterY() - that.getCenterY();
			distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			return distance;
		}

		@Override
		public int compareTo(Triangle that) {
			int compareX = this.centerX.compareTo(that.centerX);
			return compareX == 0 ? this.centerY.compareTo(that.centerY) : compareX;
		}

		//getters and setters
		public Double getCenterX() {return centerX;}
		public void setCenterX(Double centerX) {this.centerX = centerX;}
		public Double getCenterY() {return centerY;}
		public void setCenterY(Double centerY) {this.centerY = centerY;}
	
	}
	
	
	public Triangles() {
		settings = TriangleSettings.getInstance();
	}
	
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return this.trianglesQuantizeOne(fingerprint);
	}
	
	public Template trianglesQuantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<Triangle> triangles = this.fingerprintToTriangles(fingerprint);
		for(Triangle triangle : triangles){
			template.hashes.add(triangle.toBigInt());
		}
		return template;
	}


	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		return this.triangleQuantizeAll(fingerprint);
	}
	
	public ArrayList<Template> triangleQuantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 
		for(double rotation=settings.rotationStart().getValue(); 
				rotation<settings.rotationStop().getValue(); 
				rotation+=settings.rotationStep().getValue())
		{
			Fingerprint rotatedPrint = fingerprint.rotate(rotation);
			templates.add(this.trianglesQuantizeOne(rotatedPrint));
		}
		return templates;
	}
	
	
	public ArrayList<Triangle> fingerprintToTriangles(Fingerprint fingerprint){
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		ArrayList<Minutia> minutiaeCopy = new ArrayList<Minutia>();
		
		for(Minutia minutia : fingerprint.minutiae){
			minutiaeCopy.add(minutia);
		}
		
		for(Minutia minutia : fingerprint.minutiae){
			Collections.sort(minutiaeCopy, minutia.getComparator()); // sorts by distance to minutia
			
			int startingIndex;
			for(startingIndex=0; minutiaeCopy.get(startingIndex).distanceTo(minutia) < 0.0001; startingIndex++);
			// could do remove spurious here
			
			ArrayList<Minutia> minutiae = new ArrayList<Minutia>();
			minutiae.add(minutia); // <---- TODO Matt, do this in PRINTS
			for(int i=0; i<settings.kClosestMinutia().getValue(); i++){
				minutiae.add(minutiaeCopy.get(startingIndex+i));
			}
			
			triangles.addAll(this.makeAllPossibleTriangles(minutiae));
			
		}
		
		return triangles;
	}
	
	
	private Triangle makeTriangle(Minutia m0, Minutia m1, Minutia m2){
		
		// order the minutiae
		ArrayList<Minutia> minutiae = new ArrayList<Minutia>();
		minutiae.add(m0);
		minutiae.add(m1);
		minutiae.add(m2);
		Collections.sort(minutiae);
		m0 = minutiae.get(0);
		m1 = minutiae.get(1);
		m2 = minutiae.get(2);

		Triangle triangleToReturn = new Triangle();

		triangleToReturn.minutiaIndecies.add(m0.getIndex());
		triangleToReturn.minutiaIndecies.add(m1.getIndex());
		triangleToReturn.minutiaIndecies.add(m2.getIndex());
		
		triangleToReturn.centerX = (m0.getX() + m1.getX() + m2.getX())/3.0;
		triangleToReturn.centerY = (m0.getY() + m1.getY() + m2.getY())/3.0;
		
		triangleToReturn.variables.get("theta0").setPrequantizedValue(m0.getTheta());
		
		triangleToReturn.variables.get("x1").setPrequantizedValue(m1.getX() - m0.getX());
		triangleToReturn.variables.get("y1").setPrequantizedValue(m1.getY() - m0.getY());
		triangleToReturn.variables.get("theta1").setPrequantizedValue(m1.getTheta());
		
		triangleToReturn.variables.get("x2").setPrequantizedValue(m2.getX() - m0.getX());
		triangleToReturn.variables.get("y2").setPrequantizedValue(m2.getY() - m0.getY());
		triangleToReturn.variables.get("theta2").setPrequantizedValue(m2.getTheta());
		
		return triangleToReturn;
	}

	private ArrayList<Triangle> makeAllPossibleTriangles(ArrayList<Minutia> minutiae){
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		int n = minutiae.size();
		for(int i=0; i<n; i++){
			for(int j=i+1; j<n; j++){
				for(int k=j+1; k<n; k++){
					triangles.add(this.makeTriangle(minutiae.get(i), minutiae.get(j), minutiae.get(k)));
				}
			}
		}
		return triangles;
	}
	
	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint){
		return new ArrayList<Feature>(this.fingerprintToTriangles(fingerprint));
	}

	
	@Override
	public Feature getBlankFeatureForTraining(){
		return new Triangle();
	}
	
}
