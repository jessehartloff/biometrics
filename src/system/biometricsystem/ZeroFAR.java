package system.biometricsystem;

public class ZeroFAR {

	private Double FRR;
	private Double threshold;
	
	public Double getFRR() {
		return FRR;
	}
	public void setFRR(Double FRR) {
		this.FRR = FRR;
	}
	public Double getThreshold() {
		return threshold;
	}
	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public String toString() {
		return "[FRR=" + FRR + ", threshold=" + threshold + "]";
	}
	
	
	
}
