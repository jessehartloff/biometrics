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

		private Long prequantizedTheta0;
		private Long prequantizedX1;
		private Long prequantizedY1;
		private Long prequantizedTheta1;
		private Long prequantizedX2;
		private Long prequantizedY2;
		private Long prequantizedTheta2;
		
		private Long theta0;
		private Long x1;
		private Long y1;
		private Long theta1;
		private Long x2;
		private Long y2;
		private Long theta2;
		
		private Double centerX;
		private Double centerY;
		
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
		
	    public boolean prequantizedEquals(Triangle otherTriangle) {
	        return this.getPrequantizedTheta0().equals(otherTriangle.getPrequantizedTheta0()) && 
	        		this.getPrequantizedX1().equals(otherTriangle.getPrequantizedX1()) && 
	        		this.getPrequantizedY1().equals(otherTriangle.getPrequantizedY1()) && 
	        		this.getPrequantizedTheta1().equals(otherTriangle.getPrequantizedTheta1()) && 
	        		this.getPrequantizedX2().equals(otherTriangle.getPrequantizedX2()) && 
	        		this.getPrequantizedY2().equals(otherTriangle.getPrequantizedY2()) && 
	        		this.getPrequantizedTheta2().equals(otherTriangle.getPrequantizedTheta2()); 
	    }
		
		public String prequantizedToString(){
			return "" + prequantizedTheta0 + " " + 
					prequantizedX1 + " " + prequantizedY1 + " " + prequantizedTheta1 + " " + 
					prequantizedX2 + " " + prequantizedY2 + " " + prequantizedTheta2;
		}
		
		public Double distanceBetweenCenters(Triangle that){
			Double distance;
			Double dx = this.centerX - that.centerX;
			Double dy = this.centerY - that.centerY;
			distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
			return distance;
		}
		
		public Long getPrequantizedTheta0() {return prequantizedTheta0;}
		public void setPrequantizedTheta0(Long prequantizedTheta0) {this.prequantizedTheta0 = prequantizedTheta0;}
		public Long getPrequantizedX1() {return prequantizedX1;}
		public void setPrequantizedX1(Long prequantizedX1) {this.prequantizedX1 = prequantizedX1;}
		public Long getPrequantizedY1() {return prequantizedY1;}
		public void setPrequantizedY1(Long prequantizedY1) {this.prequantizedY1 = prequantizedY1;}
		public Long getPrequantizedTheta1() {return prequantizedTheta1;}
		public void setPrequantizedTheta1(Long prequantizedTheta1) {this.prequantizedTheta1 = prequantizedTheta1;}
		public Long getPrequantizedX2() {return prequantizedX2;}
		public void setPrequantizedX2(Long prequantizedX2) {this.prequantizedX2 = prequantizedX2;}
		public Long getPrequantizedY2() {return prequantizedY2;}
		public void setPrequantizedY2(Long prequantizedY2) {this.prequantizedY2 = prequantizedY2;}
		public Long getPrequantizedTheta2() {return prequantizedTheta2;}
		public void setPrequantizedTheta2(Long prequantizedTheta2) {this.prequantizedTheta2 = prequantizedTheta2;}
		public Long getTheta0() {return theta0;}
		public void setTheta0(Long theta0) {this.theta0 = theta0;}
		public Long getX1() {return x1;}
		public void setX1(Long x1) {this.x1 = x1;}
		public Long getY1() {return y1;}
		public void setY1(Long y1) {this.y1 = y1;}
		public Long getTheta1() {return theta1;}
		public void setTheta1(Long theta1) {this.theta1 = theta1;}
		public Long getX2() {return x2;}
		public void setX2(Long x2) {this.x2 = x2;}
		public Long getY2() {return y2;}
		public void setY2(Long y2) {this.y2 = y2;}
		public Long getTheta2() {return theta2;}
		public void setTheta2(Long theta2) {this.theta2 = theta2;}
		public Double getCenterX() {return centerX;}
		public void setCenterX(Double centerX) {this.centerX = centerX;}
		public Double getCenterY() {return centerY;}
		public void setCenterY(Double centerY) {this.centerY = centerY;}

		
		
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
		return trianglesQuantizeOne(fingerprint);
	}
	
	public Template trianglesQuantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<Triangle> triangles = this.fingerprintToTriangles(fingerprint);
		for(Triangle triangle : triangles){
			template.hashes.add(triangle.toBigInt());
		}
//		System.out.println(template.hashes);
		return template;
	}


	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		return this.triangleQuantizeAll(fingerprint);
	}
	
	public ArrayList<Template> triangleQuantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 
		for(double rotation=settings.getRotationStart(); 
				rotation<settings.getRotationStop(); 
				rotation+=settings.getRotationStep())
		{
			Fingerprint rotatedPrint = fingerprint.rotate(rotation);
			templates.add(this.trianglesQuantizeOne(rotatedPrint));
		}
		return templates;
	}

	@Override
	public Double distance(BigInteger point1, BigInteger point2) {
		// TODO +triangle distance
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
			
//			Triangle triangle2 = makeTriangle(minutia, 
//					minutiaeCopy.get(startingIndex), 
//					minutiaeCopy.get(startingIndex+2));
//			
//			Triangle triangle3 = makeTriangle(minutia, 
//					minutiaeCopy.get(startingIndex+1), 
//					minutiaeCopy.get(startingIndex+2));
//
//			Triangle triangle4 = makeTriangle(
//					minutiaeCopy.get(startingIndex), 
//					minutiaeCopy.get(startingIndex+1), 
//					minutiaeCopy.get(startingIndex+2));
			
//			System.out.println(triangle);

			triangles.add(triangle);
//			triangles.add(triangle2);
//			triangles.add(triangle3);
//			triangles.add(triangle4);	
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
		
		triangleToReturn.centerX = (m0.getX() + m1.getX() + m2.getX())/3.0;
		triangleToReturn.centerY = (m0.getY() + m1.getY() + m2.getY())/3.0;
		
		
		triangleToReturn.prequantizedTheta0 = m0.getTheta();
		
		triangleToReturn.prequantizedX1     = m1.getX() - m0.getX();
		triangleToReturn.prequantizedY1     = m1.getY() - m0.getY();
		triangleToReturn.prequantizedTheta1 = m1.getTheta();
		
		triangleToReturn.prequantizedX2     = m2.getX() - m0.getX();
		triangleToReturn.prequantizedY2     = m2.getY() - m0.getY();
		triangleToReturn.prequantizedTheta2 = m2.getTheta();

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
	public void doAllTheBinning(ArrayList<Fingerprint> fingerprints) {
		
		ArrayList<Long> allTheta0 = new ArrayList<Long>();
		ArrayList<Long> allX1 = new ArrayList<Long>();
		ArrayList<Long> allY1 = new ArrayList<Long>();
		ArrayList<Long> allTheta1 = new ArrayList<Long>();
		ArrayList<Long> allX2 = new ArrayList<Long>();
		ArrayList<Long> allY2 = new ArrayList<Long>();
		ArrayList<Long> allTheta2 = new ArrayList<Long>();
		
		for(Fingerprint fingerprint : fingerprints){
			ArrayList<Triangle> triangles = this.fingerprintToTriangles(fingerprint);
			for(Triangle triangle : triangles){
				allTheta0.add(triangle.prequantizedTheta0);
				allX1.add(triangle.prequantizedX1);
				allY1.add(triangle.prequantizedY1);
				allTheta1.add(triangle.prequantizedTheta1);
				allX2.add(triangle.prequantizedX2);
				allY2.add(triangle.prequantizedY2);
				allTheta2.add(triangle.prequantizedTheta2);
			}
		}
		
		settings.theta0.computeBinBoundaries(allTheta0);
		settings.x1.computeBinBoundaries(allX1);
		settings.y1.computeBinBoundaries(allY1);
		settings.theta1.computeBinBoundaries(allTheta1);
		settings.x2.computeBinBoundaries(allX2);
		settings.y2.computeBinBoundaries(allY2);
		settings.theta2.computeBinBoundaries(allTheta2);
	}
	

	
}// end class
