package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.*;
import system.allcommonclasses.modalities.*;
import system.allcommonclasses.settings.*;
import system.vectordistance.MeasurableDistance;

/**
 * 
 * Determines how Paths are quantized and compared. Paths are... (describe paths here)
 *
 */
public class PathsMethod extends FingerprintMethod {

	private PathSettings settings;
	
	public class Path implements MeasurableDistance<Path>{

		private Long prequantizedD0;
		private Long prequantizedD1;
		private Long prequantizedD2;
		private Long prequantizedD3;
		private Long prequantizedPhi1;
		private Long prequantizedPhi2;
		private Long prequantizedPhi3;
		private Long prequantizedSigma0;
		private Long prequantizedSigma1;
		private Long prequantizedSigma2;
		private Long prequantizedSigma3;
		
		private Long d0;
		private Long d1;
		private Long d2;
		private Long d3;
		private Long phi1;
		private Long phi2;
		private Long phi3;
		private Long sigma0;
		private Long sigma1;
		private Long sigma2;
		private Long sigma3;
		
		private PathSettings innerSettings;
		
		public Path(PathSettings settings){
			this.innerSettings = settings;
		}
		public Path(){
			this.innerSettings = settings;
		}
		
		public BigInteger toBigInt(){
			
			BigInteger toReturn = BigInteger.valueOf(d0);
			
			toReturn = toReturn.shiftLeft(settings.d1.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(d1));
			
			toReturn = toReturn.shiftLeft(settings.d2.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(d2));
			
			toReturn = toReturn.shiftLeft(settings.d3.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(d3));
			
			toReturn = toReturn.shiftLeft(settings.phi1.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(phi1));
			
			toReturn = toReturn.shiftLeft(settings.phi2.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(phi2));
			
			toReturn = toReturn.shiftLeft(settings.phi3.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(phi3));
			
			toReturn = toReturn.shiftLeft(settings.sigma0.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(sigma0));
			
			toReturn = toReturn.shiftLeft(settings.sigma1.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(sigma1));
			
			toReturn = toReturn.shiftLeft(settings.sigma2.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(sigma2));
			
			toReturn = toReturn.shiftLeft(settings.sigma3.getBits());
			toReturn = toReturn.add(BigInteger.valueOf(sigma3));
			
//			System.out.print(toReturn + " ");
			return toReturn;
		}
		
		public void fromBigInt(BigInteger bigInt){
			
			BigInteger bigTwo = BigInteger.valueOf(2);
			
			this.sigma3 = bigInt.and(bigTwo.pow(settings.sigma3.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.sigma3.getBits());
			
			this.sigma2 = bigInt.and(bigTwo.pow(settings.sigma2.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.sigma2.getBits());
			
			this.sigma1 = bigInt.and(bigTwo.pow(settings.sigma1.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.sigma1.getBits());
			
			this.sigma0 = bigInt.and(bigTwo.pow(settings.sigma0.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.sigma0.getBits());
			
			this.phi3 = bigInt.and(bigTwo.pow(settings.phi3.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.phi3.getBits());
			
			this.phi2 = bigInt.and(bigTwo.pow(settings.phi2.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.phi2.getBits());
			
			this.phi1 = bigInt.and(bigTwo.pow(settings.phi1.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.phi1.getBits());
			
			this.d3 = bigInt.and(bigTwo.pow(settings.d3.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.d3.getBits());
			
			this.d2 = bigInt.and(bigTwo.pow(settings.d2.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.d2.getBits());
			
			this.d1 = bigInt.and(bigTwo.pow(settings.d1.getBits()).add(BigInteger.valueOf(-1))).longValue();
			bigInt = bigInt.shiftRight(settings.d1.getBits());
			
			this.d0 = bigInt.and(bigTwo.pow(settings.d0.getBits()).add(BigInteger.valueOf(-1))).longValue();
//			bigInt = bigInt.shiftRight(settings.d0.getBits());
		}
		
		@Override
	    public boolean equals(Object obj) {
	        if (obj == null)
	            return false;
	        if (obj == this)
	            return true;
	        if (!(obj instanceof Path))
	            return false;

	        Path otherPath = (Path) obj;
	        return this.d0.equals(otherPath.d0) && 
	        		this.d1.equals(otherPath.d1) && 
	        		this.d2.equals(otherPath.d2) && 
	        		this.d3.equals(otherPath.d3) &&
	        		this.phi1.equals(otherPath.phi1) &&
	        		this.phi2.equals(otherPath.phi2) &&
	        		this.phi3.equals(otherPath.phi3) && 
	        		this.sigma0.equals(otherPath.sigma0) &&
	        		this.sigma1.equals(otherPath.sigma1) &&
	        		this.sigma2.equals(otherPath.sigma2) &&
	        		this.sigma3.equals(otherPath.sigma3);
	    }
		
		@Override
		public Double distanceFrom(Path compareT) {
			// TODO +distance
			return null;
		}

		//getters and setters
		public Long getD0() {
			return d0;
		}

		public void setD0(Long d0) {
			this.d0 = d0;
		}

		public Long getD1() {
			return d1;
		}

		public void setD1(Long d1) {
			this.d1 = d1;
		}

		public Long getD2() {
			return d2;
		}

		public void setD2(Long d2) {
			this.d2 = d2;
		}

		public Long getD3() {
			return d3;
		}

		public void setD3(Long d3) {
			this.d3 = d3;
		}

		public Long getPhi1() {
			return phi1;
		}

		public void setPhi1(Long phi1) {
			this.phi1 = phi1;
		}

		public Long getPhi2() {
			return phi2;
		}

		public void setPhi2(Long phi2) {
			this.phi2 = phi2;
		}

		public Long getPhi3() {
			return phi3;
		}

		public void setPhi3(Long phi3) {
			this.phi3 = phi3;
		}

		public Long getSigma0() {
			return sigma0;
		}
		
		public void setSigma0(Long sigma0) {
			this.sigma0 = sigma0;
		}

		public Long getSigma1() {
			return sigma1;
		}

		public void setSigma1(Long sigma1) {
			this.sigma1 = sigma1;
		}

		public Long getSigma2() {
			return sigma2;
		}

		public void setSigma2(Long sigma2) {
			this.sigma2 = sigma2;
		}

		public Long getSigma3() {
			return sigma3;
		}

		public void setSigma3(Long sigma3) {
			this.sigma3 = sigma3;
		}
		public Long getPrequantizedD0() {
			return prequantizedD0;
		}
		public void setPrequantizedD0(Long prequantizedD0) {
			this.prequantizedD0 = prequantizedD0;
		}
		public Long getPrequantizedD1() {
			return prequantizedD1;
		}
		public void setPrequantizedD1(Long prequantizedD1) {
			this.prequantizedD1 = prequantizedD1;
		}
		public Long getPrequantizedD2() {
			return prequantizedD2;
		}
		public void setPrequantizedD2(Long prequantizedD2) {
			this.prequantizedD2 = prequantizedD2;
		}
		public Long getPrequantizedD3() {
			return prequantizedD3;
		}
		public void setPrequantizedD3(Long prequantizedD3) {
			this.prequantizedD3 = prequantizedD3;
		}
		public Long getPrequantizedPhi1() {
			return prequantizedPhi1;
		}
		public void setPrequantizedPhi1(Long prequantizedPhi1) {
			this.prequantizedPhi1 = prequantizedPhi1;
		}
		public Long getPrequantizedPhi2() {
			return prequantizedPhi2;
		}
		public void setPrequantizedPhi2(Long prequantizedPhi2) {
			this.prequantizedPhi2 = prequantizedPhi2;
		}
		public Long getPrequantizedPhi3() {
			return prequantizedPhi3;
		}
		public void setPrequantizedPhi3(Long prequantizedPhi3) {
			this.prequantizedPhi3 = prequantizedPhi3;
		}
		public Long getPrequantizedSigma0() {
			return prequantizedSigma0;
		}
		public void setPrequantizedSigma0(Long prequantizedSigma0) {
			this.prequantizedSigma0 = fixSigma(prequantizedSigma0);
		}
		public Long getPrequantizedSigma1() {
			return prequantizedSigma1;
		}
		public void setPrequantizedSigma1(Long prequantizedSigma1) {
			this.prequantizedSigma1 = fixSigma(prequantizedSigma1);
		}
		public Long getPrequantizedSigma2() {
			return prequantizedSigma2;
		}
		public void setPrequantizedSigma2(Long prequantizedSigma2) {
			this.prequantizedSigma2 = fixSigma(prequantizedSigma2);
		}
		public Long getPrequantizedSigma3() {
			return prequantizedSigma3;
		}
		public void setPrequantizedSigma3(Long prequantizedSigma3) {
			this.prequantizedSigma3 = fixSigma(prequantizedSigma3);
		}
		
		private Long fixSigma(Long sigma){
			// output is in [0, 179]
			Long toReturn = (sigma<0) ? -sigma : sigma;
			while(toReturn>=180){
				toReturn -= 180;
			}
			return toReturn;
		}

	}

	
	public PathsMethod() {
		settings = PathSettings.getInstance();
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
//			singleFingerprintMethod = new PathsMethod();
//		}
//		else{
//			FingerprintMethod.checkClass("Paths");
//		}
//		return singleFingerprintMethod;
//	}


	@Override
	public Template quantizeOne(Fingerprint fingerprint){
		Template template = new Template();
		ArrayList<Path> paths = this.fingerprintToPaths(fingerprint);
		for(Path path : paths){
			template.hashes.add(path.toBigInt());
		}
//		System.out.println(template.hashes);
		return template;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 
		templates.add(this.quantizeOne(fingerprint));
		return templates;
	}

	@Override
	public Double distance(BigInteger point1, BigInteger point2) {
		// TODO +path distance
		return null;
	}
	
	public ArrayList<Path> fingerprintToPaths(Fingerprint fingerprint){
		ArrayList<Path> paths = new ArrayList<Path>();
		
		ArrayList<Minutia> minutiaeCopy = new ArrayList<Minutia>();
		
		for(Minutia minutia : fingerprint.minutiae){
			minutiaeCopy.add(minutia);
		}
		
		for(Minutia minutia : fingerprint.minutiae){
			Collections.sort(minutiaeCopy, minutia.getComparator()); // sorts by distance to minutia
			
			int startingIndex;
			for(startingIndex=0; minutiaeCopy.get(startingIndex).distanceTo(minutia) < 0.0001; startingIndex++);
			// could do remove spurious by changing the min distance
			Path path = this.makePath(minutia, 
					minutiaeCopy.get(startingIndex), 
					minutiaeCopy.get(startingIndex+1), 
					minutiaeCopy.get(startingIndex+2), 
					minutiaeCopy.get(startingIndex+3)
					);
			
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

			paths.add(path);
//			triangles.add(triangle2);
//			triangles.add(triangle3);
//			triangles.add(triangle4);	
		}
	
		return paths;
	}
	
	private Path makePath(Minutia m0, Minutia m1, Minutia m2, Minutia m3, Minutia m4){
		
	
//		// order the minutiae
//		ArrayList<Minutia> minutiae = new ArrayList<Minutia>();
//		minutiae.add(m0);
//		minutiae.add(m1);
//		minutiae.add(m2);
//		minutiae.add(m3);
//		minutiae.add(m4);
//		Collections.sort(minutiae);
//		m0 = minutiae.get(0);
//		m1 = minutiae.get(1);
//		m2 = minutiae.get(2);
//		m3 = minutiae.get(3);
//		m4 = minutiae.get(4);
		
		Path pathToReturn = new Path();

		pathToReturn.setPrequantizedD0(Math.round(m0.distanceTo(m1)));
		pathToReturn.setPrequantizedD1(Math.round(m1.distanceTo(m2)));
		pathToReturn.setPrequantizedD2(Math.round(m2.distanceTo(m3)));
		pathToReturn.setPrequantizedD3(Math.round(m3.distanceTo(m4)));
		
		pathToReturn.setPrequantizedPhi1(Math.round(Minutia.computeInsideAngle(m0,m1,m2)));
		pathToReturn.setPrequantizedPhi2(Math.round(Minutia.computeInsideAngle(m1,m2,m3)));
		pathToReturn.setPrequantizedPhi3(Math.round(Minutia.computeInsideAngle(m2,m3,m4)));
		
		pathToReturn.setPrequantizedSigma0(m0.getTheta() - m1.getTheta());
		pathToReturn.setPrequantizedSigma1(m1.getTheta() - m2.getTheta());
		pathToReturn.setPrequantizedSigma2(m2.getTheta() - m3.getTheta());
		pathToReturn.setPrequantizedSigma3(m3.getTheta() - m4.getTheta());
		


		pathToReturn.setD0(settings.d0.findBin(pathToReturn.getPrequantizedD0()));
		pathToReturn.setD1(settings.d1.findBin(pathToReturn.getPrequantizedD1()));
		pathToReturn.setD2(settings.d2.findBin(pathToReturn.getPrequantizedD2()));
		pathToReturn.setD3(settings.d3.findBin(pathToReturn.getPrequantizedD3()));

		pathToReturn.setPhi1(settings.phi1.findBin(pathToReturn.getPrequantizedPhi1()));
		pathToReturn.setPhi2(settings.phi2.findBin(pathToReturn.getPrequantizedPhi2()));
		pathToReturn.setPhi3(settings.phi3.findBin(pathToReturn.getPrequantizedPhi3()));

		pathToReturn.setSigma0(settings.sigma0.findBin(pathToReturn.getPrequantizedSigma0()));
		pathToReturn.setSigma1(settings.sigma1.findBin(pathToReturn.getPrequantizedSigma1()));
		pathToReturn.setSigma2(settings.sigma2.findBin(pathToReturn.getPrequantizedSigma2()));
		pathToReturn.setSigma3(settings.sigma3.findBin(pathToReturn.getPrequantizedSigma3()));
		
		
		
		return pathToReturn;
	}

	
	@Override
	public void doAllTheBinning(ArrayList<Fingerprint> fingerprints) {

		ArrayList<Long> allD0 = new ArrayList<Long>();
		ArrayList<Long> allD1 = new ArrayList<Long>();
		ArrayList<Long> allD2 = new ArrayList<Long>();
		ArrayList<Long> allD3 = new ArrayList<Long>();
		ArrayList<Long> allPhi1 = new ArrayList<Long>();
		ArrayList<Long> allPhi2 = new ArrayList<Long>();
		ArrayList<Long> allPhi3 = new ArrayList<Long>();
		ArrayList<Long> allSigma0 = new ArrayList<Long>();
		ArrayList<Long> allSigma1 = new ArrayList<Long>();
		ArrayList<Long> allSigma2 = new ArrayList<Long>();
		ArrayList<Long> allSigma3 = new ArrayList<Long>();
		
		for(Fingerprint fingerprint : fingerprints){
			ArrayList<Path> paths = this.fingerprintToPaths(fingerprint);
			for(Path path : paths){
				allD0.add(path.getPrequantizedD0());
				allD1.add(path.getPrequantizedD1());
				allD2.add(path.getPrequantizedD2());
				allD3.add(path.getPrequantizedD3());
				allPhi1.add(path.getPrequantizedPhi1());
				allPhi2.add(path.getPrequantizedPhi2());
				allPhi3.add(path.getPrequantizedPhi3());
				allSigma0.add(path.getPrequantizedSigma0());
				allSigma1.add(path.getPrequantizedSigma1());
				allSigma2.add(path.getPrequantizedSigma2());
				allSigma3.add(path.getPrequantizedSigma3());
			}
		}

		settings.d0.computeBinBoundaries(allD0);
		settings.d1.computeBinBoundaries(allD1);
		settings.d2.computeBinBoundaries(allD2);
		settings.d3.computeBinBoundaries(allD3);
		settings.phi1.computeBinBoundaries(allPhi1);
		settings.phi2.computeBinBoundaries(allPhi2);
		settings.phi3.computeBinBoundaries(allPhi3);
		settings.sigma0.computeBinBoundaries(allSigma0);
		settings.sigma1.computeBinBoundaries(allSigma1);
		settings.sigma2.computeBinBoundaries(allSigma2);
		settings.sigma3.computeBinBoundaries(allSigma3);
	}


	@Override
	public Long getTotalBits() {
		return settings.d0.getBits().longValue() + 
				settings.d1.getBits().longValue() + 
				settings.d2.getBits().longValue() + 
				settings.d3.getBits().longValue() + 
				settings.phi1.getBits().longValue() + 
				settings.phi2.getBits().longValue() + 
				settings.phi3.getBits().longValue() + 
				settings.sigma0.getBits().longValue() + 
				settings.sigma1.getBits().longValue() + 
				settings.sigma2.getBits().longValue() + 
				settings.sigma3.getBits().longValue();
	}

	
	
	
}
