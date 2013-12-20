package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.makefeaturevector.feature.Feature;

/**
 * 
 * Same as triangles, but times three.
 *
 */
public class TriplesOfTriangles extends Triangles {

	protected class TriangleTriplet extends Feature{
		Triangle t0;
		Triangle t1;
		Triangle t2;
		
		ArrayList<Triangle> ts;
		
		public TriangleTriplet(){
			
		}
		
		@Override
		public Long getTotalBits(){
			return t0.getTotalBits() + t1.getTotalBits() + t2.getTotalBits();
		}
		
		protected void order(){
			ArrayList<Triangle> threeTriangles = new ArrayList<Triangle>();
			threeTriangles.add(t0);
			threeTriangles.add(t1);
			threeTriangles.add(t2);
			Collections.sort(threeTriangles);
			t0 = threeTriangles.get(0);
			t1 = threeTriangles.get(1);
			t2 = threeTriangles.get(2);
		}
		
		@Override
		public BigInteger toBigInt(){
			Integer triangleBits = settings.theta0.getBits() + 
					settings.x1.getBits() +
					settings.y1.getBits() +
					settings.theta1.getBits() +
					settings.x2.getBits() +
					settings.y2.getBits() +
					settings.theta2.getBits();
			
			BigInteger toReturn = t0.toBigInt();

			toReturn = toReturn.shiftLeft(triangleBits);
			toReturn = toReturn.add(t1.toBigInt());
			
			toReturn = toReturn.shiftLeft(triangleBits);
			toReturn = toReturn.add(t2.toBigInt());
			
			return toReturn;
		}
		
		@Override
		public void fromBigInt(BigInteger bigInt){
			Integer triangleBits = settings.theta0.getBits() + 
					settings.x1.getBits() +
					settings.y1.getBits() +
					settings.theta1.getBits() +
					settings.x2.getBits() +
					settings.y2.getBits() +
					settings.theta2.getBits();

			BigInteger bigTwo = BigInteger.valueOf(2);
			
			Triangle triangle2 = new Triangle();
			triangle2.fromBigInt(bigInt.and(bigTwo.pow(triangleBits).add(BigInteger.valueOf(-1))));
			t2 = triangle2;
			bigInt = bigInt.shiftRight(triangleBits);

			Triangle triangle1 = new Triangle();
			triangle1.fromBigInt(bigInt.and(bigTwo.pow(triangleBits).add(BigInteger.valueOf(-1))));
			t1 = triangle1;
			bigInt = bigInt.shiftRight(triangleBits);

			Triangle triangle0 = new Triangle();
			triangle0.fromBigInt(bigInt.and(bigTwo.pow(triangleBits).add(BigInteger.valueOf(-1))));
			t0 = triangle0;
		}
	}
	
	
	
	public TriplesOfTriangles() {
	}
	
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return triplesOfTrianglesQuantizeOne(fingerprint);
	}

	
	public Template triplesOfTrianglesQuantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<Triangle> triangles = super.fingerprintToTriangles(fingerprint);

//		System.out.println("gre: " + triangles.get(0).minutiaIndecies);
		ArrayList<Triangle> triangleCopy = new ArrayList<Triangle>();
			
		for(Triangle triangle : triangles){
			triangleCopy.add(triangle);
		}
			
		for(Triangle triangle : triangles){
			Collections.sort(triangleCopy, triangle.getComparator());
				
			int startingIndex;
			for(startingIndex=0; triangleCopy.get(startingIndex).distanceBetweenCenters(triangle) < 0.0001; startingIndex++);

//			System.out.println("tri: " + triangle.minutiaIndecies);
			
			ArrayList<Triangle> trianglesToTry = new ArrayList<Triangle>();
			trianglesToTry.add(triangle);
			for(int i=0; i<settings.getkClosestTriangles(); i++){
				trianglesToTry.add(triangleCopy.get(startingIndex+i));
			}
			
			this.tryToAddAllPossibleTriplets(template, trianglesToTry);
	
			// fours:    0.061
			// ten-four: 0.055
			// tens:     0.046  DB2: 0.032
			
			}
			
		return template;
	}

	
	private void tryToAddTriplet(Template template, Triangle t0, Triangle t1, Triangle t2){
		HashSet<Long> indecies = new HashSet<Long>();
		indecies.addAll(t0.minutiaIndecies);
		indecies.addAll(t1.minutiaIndecies);
		indecies.addAll(t2.minutiaIndecies);
//		System.out.println("number of points in triplet: " + indecies.size());
//		System.out.println("all: " + indecies);
//		System.out.println("t0: " + t0.minutiaIndecies);
//		System.out.println("t1: " + t1.minutiaIndecies);
		if(indecies.size() >= settings.getMinimumPointsForTripletOfTriangles()){
			TriangleTriplet triplet = new TriangleTriplet();
			triplet.t0 = t0;
			triplet.t1 = t1;
			triplet.t2 = t2;
			triplet.order();
			template.hashes.add(triplet.toBigInt());
		}
	}
	
	private void tryToAddAllPossibleTriplets(Template template, ArrayList<Triangle> triangles){
		int n = triangles.size();
		for(int i=0; i<n; i++){
			for(int j=i+1; j<n; j++){
				for(int k=j+1; k<n; k++){
					this.tryToAddTriplet(template, triangles.get(i), triangles.get(j), triangles.get(k));
				}
			}
		}
	}
	
	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		return triplesOfTrianglesQuantizeAll(fingerprint);
	}

	public ArrayList<Template> triplesOfTrianglesQuantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 

		for(double rotation=settings.getRotationStart(); 
				rotation<settings.getRotationStop(); 
				rotation+=settings.getRotationStep())
		{
			Fingerprint rotatedPrint = fingerprint.rotate(rotation);
			templates.add(this.triplesOfTrianglesQuantizeOne(rotatedPrint));
		}
		return templates;
	}
	
	
	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint){
		return new ArrayList<Feature>(this.fingerprintToTriangles(fingerprint));
	}
	
	
	@Override
	public Feature getBlankFeatureForBinning(){
		return new Triangle();
	}
	
	@Override
	public Feature getBlankFeatureForTotalBits(){
		return new TriangleTriplet();
	}
}
