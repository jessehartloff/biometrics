package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.*;
import system.allcommonclasses.settings.TriangleSettings;


/**
 * 
 * Determines how triangles are formed and quantized. Triangles are triplets of minutiae points and...
 * 
 *
 */
public class Triangles extends FingerprintMethod {
	
	protected TriangleSettings settings;
	
	protected class Triangle implements Comparable<Triangle>{
		
		Long theta0;
		
		Long x1;
		Long y1;
		Long theta1;
		
		Long x2;
		Long y2;
		Long theta2;
		
		Double centerX;
		Double centerY;
		
		protected BigInteger toBigInt(){
			BigInteger toReturn = BigInteger.ZERO;
			
			toReturn.add(BigInteger.valueOf(theta0));
			toReturn.shiftLeft(settings.getBitsForTheta0());
			
			toReturn.add(BigInteger.valueOf(x1));
			toReturn.shiftLeft(settings.getBitsForX1());
			
			toReturn.add(BigInteger.valueOf(y1));
			toReturn.shiftLeft(settings.getBitsForY1());
			
			toReturn.add(BigInteger.valueOf(theta1));
			toReturn.shiftLeft(settings.getBitsForTheta1());
			
			toReturn.add(BigInteger.valueOf(x2));
			toReturn.shiftLeft(settings.getBitsForX2());
			
			toReturn.add(BigInteger.valueOf(y2));
			toReturn.shiftLeft(settings.getBitsForY2());
			
			toReturn.add(BigInteger.valueOf(theta2));
			toReturn.shiftLeft(settings.getBitsForTheta2());
			
			return toReturn;
		}
		
		//TriangleSettings ts = TriangleSettings.getInstance();
		
		//ts.getBitsForTheta0();
		
		protected void fromBigInt(BigInteger bigInt){
			{}// TODO the following needs to be tested or changed to not suck
			BigInteger bigTwo = BigInteger.valueOf(2);
			{}// TODO this is broke. need to shift right
			this.theta0 = bigInt.and(bigTwo.pow(settings.getBitsForTheta0()).add(BigInteger.valueOf(-1))).longValue();
			this.x1 = bigInt.and(bigTwo.pow(settings.getBitsForX1()).add(BigInteger.valueOf(-1))).longValue();
			this.y1 = bigInt.and(bigTwo.pow(settings.getBitsForY1()).add(BigInteger.valueOf(-1))).longValue();
			this.theta1 = bigInt.and(bigTwo.pow(settings.getBitsForTheta1()).add(BigInteger.valueOf(-1))).longValue();
			this.x2 = bigInt.and(bigTwo.pow(settings.getBitsForX2()).add(BigInteger.valueOf(-1))).longValue();
			this.y2 = bigInt.and(bigTwo.pow(settings.getBitsForY2()).add(BigInteger.valueOf(-1))).longValue();
			this.theta2 = bigInt.and(bigTwo.pow(settings.getBitsForTheta2()).add(BigInteger.valueOf(-1))).longValue();
		}

		@Override
		public int compareTo(Triangle that) {
			int compareX = this.centerX.compareTo(that.centerX);
			return compareX == 0 ? this.centerY.compareTo(that.centerY) : compareX;
		}

	}
	
	
	
	protected Triangles() {
		
		{}//TODO get settings instance
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
	public static FingerprintMethod getInstance(){
		if(singleFingerprintMethod == null){
			singleFingerprintMethod = new Triangles();
		}
		else{
			FingerprintMethod.checkClass("Triangles");
		}
		return singleFingerprintMethod;
	}
	
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<Triangle> triangles = this.fingerprintToTriangles(fingerprint);
		for(Triangle triangle : triangles){
			template.hashes.add(triangle.toBigInt());
		}
		return template;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 
		for(double rotation=settings.getRotationStart(); 
				rotation<settings.getRotationStop(); 
				rotation+=settings.getRotationStep())
		{
			Fingerprint rotatedPrint = fingerprint.rotate(rotation);
			templates.add(this.quantizeOne(rotatedPrint));
		}
		return templates;
	}

	@Override
	public Double distance(BigInteger point1, BigInteger point2) {
		// TODO triangle distance
		return null;
	}
	
	protected ArrayList<Triangle> fingerprintToTriangles(Fingerprint fingerprint){
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		
		ArrayList<Minutia> minutiaeCopy = new ArrayList<Minutia>();
		
		for(Minutia minutia : fingerprint.minutiae){
			minutiaeCopy.add(minutia);
		}
		
		for(Minutia minutia : fingerprint.minutiae){
			Collections.sort(minutiaeCopy, minutia.getComparator()); // sorts by distance to minutia
			
			int startingIndex;
			for(startingIndex=0; minutiaeCopy.get(startingIndex).distanceTo(minutia) < 0.0001; startingIndex++);
			// could do remove spurious by changing the min distance
			Triangle triangle = makeTriangle(minutia, 
					minutiaeCopy.get(startingIndex), 
					minutiaeCopy.get(startingIndex+1));
			
			triangles.add(triangle);	
		}
	
		return triangles;
	}
	
	protected Triangle makeTriangle(Minutia m0, Minutia m1, Minutia m2){
		
		// order the minutiae
		ArrayList<Minutia> minutiae = new ArrayList<Minutia>();
		minutiae.add(m0); // this code is ugly
		minutiae.add(m1);
		minutiae.add(m2);
		Collections.sort(minutiae);
		m0 = minutiae.get(0);
		m1 = minutiae.get(1);
		m2 = minutiae.get(2);
		
		
		Triangle triangleToReturn = new Triangle();
		
		triangleToReturn.centerX = (m0.x + m1.x + m2.x)/3.0;
		triangleToReturn.centerY = (m0.y + m1.y + m2.y)/3.0;

		triangleToReturn.theta0 = m0.theta;
		
		triangleToReturn.x1 = m1.x - m0.x;
		triangleToReturn.y1 = m1.y - m0.y;
		triangleToReturn.theta1 = m1.theta;
		
		triangleToReturn.x2 = m2.x - m0.x;
		triangleToReturn.y2 = m2.y - m0.y;
		triangleToReturn.theta2 = m2.theta;

		{}// TODO triangle binning
		
		return triangleToReturn;
	}
	

	
}// end class
