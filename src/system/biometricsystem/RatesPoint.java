package system.biometricsystem;


public class RatesPoint {
	Double far;
	Double frr;
	Double threshold;
	
	
	@Override
	public String toString() {
		return "RatesPoint [far=" + far + ", frr=" + frr + ", threshold="
				+ threshold + "]";
	}


	public Double getFar() {
		return far;
	}


	public void setFar(Double far) {
		this.far = far;
	}


	public Double getFrr() {
		return frr;
	}


	public void setFrr(Double frr) {
		this.frr = frr;
	}


	public Double getThreshold() {
		return threshold;
	}


	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
	
	
}
