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
			toReturn.shiftLeft(settings.theta0.getBits());
			
			toReturn.add(BigInteger.valueOf(x1));
			toReturn.shiftLeft(settings.theta0.getBits());
			
			toReturn.add(BigInteger.valueOf(y1));
			toReturn.shiftLeft(settings.y1.getBits());
			
			toReturn.add(BigInteger.valueOf(theta1));
			toReturn.shiftLeft(settings.theta1.getBits());
			
			toReturn.add(BigInteger.valueOf(x2));
			toReturn.shiftLeft(settings.x2.getBits());
			
			toReturn.add(BigInteger.valueOf(y2));
			toReturn.shiftLeft(settings.y2.getBits());
			
			toReturn.add(BigInteger.valueOf(theta2));
			toReturn.shiftLeft(settings.theta2.getBits());
			
			return toReturn;
		}
		
		
		protected void fromBigInt(BigInteger bigInt){
			
			{}// TODO the following needs to be tested and/or changed to not suck
			BigInteger bigTwo = BigInteger.valueOf(2);
			this.theta0 = bigInt.and(bigTwo.pow(settings.theta0.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt.shiftRight(settings.theta0.getBits());
			
			this.x1 = bigInt.and(bigTwo.pow(settings.x1.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt.shiftRight(settings.x1.getBits());
			
			this.y1 = bigInt.and(bigTwo.pow(settings.y1.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt.shiftRight(settings.y1.getBits());
			
			this.theta1 = bigInt.and(bigTwo.pow(settings.theta1.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt.shiftRight(settings.theta1.getBits());
			
			this.x2 = bigInt.and(bigTwo.pow(settings.x2.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt.shiftRight(settings.x2.getBits());
			
			this.y2 = bigInt.and(bigTwo.pow(settings.y2.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt.shiftRight(settings.y2.getBits());
			
			this.theta2 = bigInt.and(bigTwo.pow(settings.theta2.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt.shiftRight(settings.theta2.getBits());
		}

		@Override
		public int compareTo(Triangle that) {
			int compareX = this.centerX.compareTo(that.centerX);
			return compareX == 0 ? this.centerY.compareTo(that.centerY) : compareX;
		}

	}
	
	
	
	public Triangles() {
		settings = TriangleSettings.getInstance();
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
//			singleFingerprintMethod = new Triangles();
//		}
//		else{
//			FingerprintMethod.checkClass("Triangles");
//		}
//		return singleFingerprintMethod;
//	}
	
	
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
		
		
		Long prequantizedTheta0 = m0.theta;
		
		Long prequantizedX1     = m1.x - m0.x;
		Long prequantizedY1     = m1.y - m0.y;
		Long prequantizedTheta1 = m1.theta;
		
		Long prequantizedX2     = m2.x - m0.x;
		Long prequantizedY2     = m2.y - m0.y;
		Long prequantizedTheta2 = m2.theta;
		

		triangleToReturn.theta0 = settings.theta0.findBin(prequantizedTheta0);
		
		triangleToReturn.x1 = settings.x1.findBin(prequantizedX1);
		triangleToReturn.y1 = settings.y1.findBin(prequantizedY1);
		triangleToReturn.theta1 = settings.theta1.findBin(prequantizedTheta1);
		
		triangleToReturn.x2 = settings.x2.findBin(prequantizedX2);
		triangleToReturn.y2 = settings.y2.findBin(prequantizedY2);
		triangleToReturn.theta2 = settings.theta2.findBin(prequantizedTheta2);

		{}// TODO triangle binning
		
		return triangleToReturn;
	}

	@Override
	public void doAllTheBinning(ArrayList<Template> templates) {
		
		ArrayList<Long> allTheta0 = new ArrayList<Long>();
		ArrayList<Long> allX1 = new ArrayList<Long>();
		ArrayList<Long> allY1 = new ArrayList<Long>();
		ArrayList<Long> allTheta1 = new ArrayList<Long>();
		ArrayList<Long> allX2 = new ArrayList<Long>();
		ArrayList<Long> allY2 = new ArrayList<Long>();
		ArrayList<Long> allTheta2 = new ArrayList<Long>();
		
		Triangle triangle = new Triangle();
		for(Template template : templates){
			for(BigInteger bigInt : template.hashes){
				triangle.fromBigInt(bigInt);
				allTheta0.add(triangle.theta0);
				allX1.add(triangle.x1);
				allY1.add(triangle.y1);
				allTheta1.add(triangle.theta1);
				allX2.add(triangle.x2);
				allY2.add(triangle.y2);
				allTheta2.add(triangle.theta2);
			}
		}
		
		settings.theta0.computeBinBoundaries(allTheta2);
		settings.x1.computeBinBoundaries(allX1);
		settings.y1.computeBinBoundaries(allY1);
		settings.theta1.computeBinBoundaries(allTheta1);
		settings.x2.computeBinBoundaries(allX2);
		settings.y2.computeBinBoundaries(allY2);
		settings.theta2.computeBinBoundaries(allTheta2);
	}
	

	
}// end class
