package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;

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
	
	private class Path implements MeasurableDistance<Path>{

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
		
		@Override
		public Double distanceFrom(Path compareT) {
			{}// TODO +distance
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
	public Template quantizeOne(Fingerprint biometric) {
		{}// TODO quantize paths
		return null;
	}


	@Override
	public ArrayList<Template> quantizeAll(Fingerprint biometric) {
		ArrayList<Template> template = new ArrayList<Template>();
		template.add(this.quantizeOne(biometric));
		return template;
	}

	
	@Override
	public Double distance(BigInteger point1, BigInteger point2) {
		{}// TODO +path distance
		return null;
	}

	@Override
	public void doAllTheBinning(ArrayList<Fingerprint> fingerprints) {
		// TODO path binning
		
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
