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
	
	public class Triangle implements Comparable<Triangle>{

		public Long prequantizedTheta0;
		public Long prequantizedX1;
		public Long prequantizedY1;
		public Long prequantizedTheta1;
		public Long prequantizedX2;
		public Long prequantizedY2;
		public Long prequantizedTheta2;
		
		public Long theta0;
		public Long x1;
		public Long y1;
		public Long theta1;
		public Long x2;
		public Long y2;
		public Long theta2;
		
		public Double centerX;
		public Double centerY;
		
		protected TriangleSettings innerSettings;
		public Triangle(TriangleSettings settings){
			this.innerSettings = settings;
		}
		public Triangle(){
			this.innerSettings = settings;
		}
		
		public BigInteger toBigInt(){

//			System.out.print(settings.theta0.getBinBoundaries() + " ");
//			System.out.print(x1 + " ");
//			System.out.print(y1 + " ");
//			System.out.print(theta1 + " ");
//			System.out.print(x2 + " ");
//			System.out.print(y2 + " ");
//			System.out.print(theta2 + " \n");
			
			BigInteger toReturn = BigInteger.valueOf(theta0);
			
			toReturn = toReturn.shiftLeft(settings.x1.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(x1));
			
			toReturn = toReturn.shiftLeft(settings.y1.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(y1));
			
			toReturn = toReturn.shiftLeft(settings.theta1.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(theta1));
			
			toReturn = toReturn.shiftLeft(settings.x2.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(x2));
			
			toReturn = toReturn.shiftLeft(settings.y2.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(y2));
			
			toReturn = toReturn.shiftLeft(settings.theta2.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(theta2));
			
//			System.out.print(toReturn + " ");
			return toReturn;
		}
		
		public void fromBigInt(BigInteger bigInt){
			
			{}// TODO the following needs to be tested and/or changed to not suck
			BigInteger bigTwo = BigInteger.valueOf(2);
			
			this.theta2 = bigInt.and(bigTwo.pow(settings.theta2.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.theta2.getBits());
			
			this.y2 = bigInt.and(bigTwo.pow(settings.y2.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.y2.getBits());
			
			this.x2 = bigInt.and(bigTwo.pow(settings.x2.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.x2.getBits());
			
			this.theta1 = bigInt.and(bigTwo.pow(settings.theta1.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.theta1.getBits());
			
			this.y1 = bigInt.and(bigTwo.pow(settings.y1.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.y1.getBits());
			
			this.x1 = bigInt.and(bigTwo.pow(settings.x1.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.x1.getBits());
			
			this.theta0 = bigInt.and(bigTwo.pow(settings.theta0.getBits()).add(BigInteger.valueOf(-1))).longValue();
//			bigInt = bigInt.shiftRight(settings.theta0.getBits());
		}

		@Override
		public int compareTo(Triangle that) {
			int compareX = this.centerX.compareTo(that.centerX);
			return compareX == 0 ? this.centerY.compareTo(that.centerY) : compareX;
		}
		
		@Override
	    public boolean equals(Object obj) {
	        if (obj == null)
	            return false;
	        if (obj == this)
	            return true;
	        if (!(obj instanceof Triangle))
	            return false;

	        Triangle otherTriangle = (Triangle) obj;
	        return this.theta0.equals(otherTriangle.theta0) && 
	        		this.x1.equals(otherTriangle.x1) && 
	        		this.y1.equals(otherTriangle.y1) && 
	        		this.theta1.equals(otherTriangle.theta1) && 
	        		this.x2.equals(otherTriangle.x2) && 
	        		this.y2.equals(otherTriangle.y2) && 
	        		this.theta2.equals(otherTriangle.theta2); 
	    }
		
		@Override
		public String toString(){
			return "" + theta0 + " " + x1 + " " + y1 + " " + theta1 + " " + x2 + " " + y2 + " " + theta2;
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
		System.out.println(template.hashes);
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
			// could do remove spurious by changing the min distance
			Triangle triangle = makeTriangle(minutia, 
					minutiaeCopy.get(startingIndex), 
					minutiaeCopy.get(startingIndex+1));
			
//			System.out.println(triangle);

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
		
		
		triangleToReturn.prequantizedTheta0 = m0.theta;
		
		triangleToReturn.prequantizedX1     = m1.x - m0.x;
		triangleToReturn.prequantizedY1     = m1.y - m0.y;
		triangleToReturn.prequantizedTheta1 = m1.theta;
		
		triangleToReturn.prequantizedX2     = m2.x - m0.x;
		triangleToReturn.prequantizedY2     = m2.y - m0.y;
		triangleToReturn.prequantizedTheta2 = m2.theta;

		triangleToReturn.theta0 = settings.theta0.findBin(triangleToReturn.prequantizedTheta0);
		
		triangleToReturn.x1 = settings.x1.findBin(triangleToReturn.prequantizedX1);
		triangleToReturn.y1 = settings.y1.findBin(triangleToReturn.prequantizedY1);
		triangleToReturn.theta1 = settings.theta1.findBin(triangleToReturn.prequantizedTheta1);
		
		triangleToReturn.x2 = settings.x2.findBin(triangleToReturn.prequantizedX2);
		triangleToReturn.y2 = settings.y2.findBin(triangleToReturn.prequantizedY2);
		triangleToReturn.theta2 = settings.theta2.findBin(triangleToReturn.prequantizedTheta2);
		
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
		
		for(Template template : templates){
			for(BigInteger bigInt : template.hashes){
				Triangle triangle = new Triangle();
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
		
		settings.theta0.computeBinBoundaries(allTheta0);
		System.out.println("thetas: " + allTheta0);
		settings.x1.computeBinBoundaries(allX1);
		settings.y1.computeBinBoundaries(allY1);
		settings.theta1.computeBinBoundaries(allTheta1);
		settings.x2.computeBinBoundaries(allX2);
		settings.y2.computeBinBoundaries(allY2);
		settings.theta2.computeBinBoundaries(allTheta2);
	}
	

	
}// end class
