package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Fingerprint;

/**
 * 
 * Same as triangles, but times three.
 *
 */
public class TriplesOfTriangles extends Triangles {

	protected class TriangleTriplet{
		Triangle t0;
		Triangle t1;
		Triangle t2;
		
		ArrayList<Triangle> ts;
		
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
		
		protected BigInteger toBigInt(){
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
		
		protected void fromBigInt(BigInteger bigInt){
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
	
	/**
	 * The getInstance methods are the only way to create FingerprintMethod objects. This ensures that
	 * each run of the system will only have only one FingerprintMethod that is shared by 
	 * every object that needs one. This prevents cases where two readings are being
	 * compared, but were quantized using different methods.
	 * 
	 * If an instance already exists, a check is made to ensure that the existing method matches
	 * the method asked for.
	 *  
	 * @return An instance of a FingerprintMethod
	 */
//	public static FingerprintMethod getInstance(){
//		if(singleFingerprintMethod == null){
//			singleFingerprintMethod = new TriplesOfTriangles();
//		}
//		else{
//			FingerprintMethod.checkClass("TriplesOfTriangles");
//		}
//		return singleFingerprintMethod;
//	}
	
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return triplesOfTrianglesQuantizeOne(fingerprint);
	}
	
	public Template triplesOfTrianglesQuantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<Triangle> triangles = super.fingerprintToTriangles(fingerprint);
		
		int n = triangles.size();
		for(int i=0; i<n; i++){
			for(int j=i+1; j<n; j++){
				for(int k=j+1; k<n; k++){
					Triangle triangle0 = triangles.get(i);
					Triangle triangle1 = triangles.get(j);
					Triangle triangle2 = triangles.get(k);
					Double distance0 = triangle0.distanceBetweenCenters(triangle1);
					Double distance1 = triangle0.distanceBetweenCenters(triangle2);
					Double distance2 = triangle1.distanceBetweenCenters(triangle2);
					Double threshold = settings.getThresholdForTriplets();
					if(distance0 < threshold && distance1 < threshold && distance2 < threshold){// TODO better condition?
						TriangleTriplet triplet = new TriangleTriplet();
						triplet.t0 = triangles.get(i);
						triplet.t1 = triangles.get(j);
						triplet.t2 = triangles.get(k);
						triplet.order();
						template.hashes.add(triplet.toBigInt());
					}
				}
			}
		}
		return template;
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
	public Double distance(BigInteger point1, BigInteger point2) {
		// TODO triple of triangles distance
		return null;
	}
	
	
	
}
