package system.allcommonclasses.commonstructures;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import system.biometricsystem.RatesPoint;
import system.biometricsystem.ZeroFAR;

/**
 * 
 * The results to be returned to the interface. This is processed data that is ready to be 
 * analyzed to measure performance. May be ready to be plotted if using a GUI. 
 *
 */
public class Results {
	
	private Double eer;
	private ZeroFAR zeroFAR;
	private ArrayList<RatesPoint> rates;
	private Histogram fieldHistogram;
	private ArrayList<Histogram> variableHistograms;
	private ArrayList<Long> indexingResults;
	private Double minEntropy;
	private Double failureToCapture;
	private Double totalLogOfBins;
	
	// TODO Jesse - setup for Jim, statistical distance from uniform

	private RawScores rawScores;
	
	/**  FIELD HISTO CHI SQUARED
	 * Calculate chi squared such that we are uniform in sample set of large field size
	 * This particular method assumes an uniform reference distribution with frequency 1 per bin
	 * and compares the input distribution to this theoretical reference distribution.
	 * @param h (typically field histogram)
	 * @return cs
	 */
	public Long chiSquared(Histogram h) {
		Long N = h.getNumberSamples();
		Double cs = 0.0;
		for (Long frequency : h.histogram.values()) {
			cs += Math.pow(frequency - 1, 2.0);
		}
		// missing bins will be 0 => (0-1)^2/1 = 1
		//there are N- histogram.size of these => add N- histogram.size to cs
		cs += N-h.histogram.size();
		return cs.longValue();
	}
	
	/**  VARIABLE HISTO CHI SQUARED
	 * Calculate chi squared such that we are uniform in sample set
	 * This particular method assumes an uniform reference distribution
	 * and compares the input distribution to this theoretical reference distribution.
	 * @param h (typically field histogram)
	 * @return cs
	 */
	public ArrayList<Double> chiSquared(ArrayList<Histogram> histos) {
		ArrayList<Double> chiSquareds = new ArrayList<Double>();
		for ( Histogram h : histos){
			Long N = h.getNumberSamples();
			Double n = new Double(h.histogram.values().size()); //number of bins
			Double E = N/n; //uniform bin frequency
			Double cs = 0.0;
			for (Long frequency : h.histogram.values()) {
				cs += Math.pow(frequency - E, 2.0)/E;
			}
			chiSquareds.add(cs);
		}

		return chiSquareds;
	}
	
	public ArrayList<RatesPoint> getRates() {
		return rates;
	}


	
	public Double getTotalLogOfBins() {
		return totalLogOfBins;
	}

	public void setTotalLogOfBins(Double totalLogOfBins) {
		this.totalLogOfBins = totalLogOfBins;
	}

	public ZeroFAR getZeroFAR() {
		return zeroFAR;
	}

	public void setZeroFAR(ZeroFAR zeroFAR) {
		this.zeroFAR = zeroFAR;
	}

	public void setRates(ArrayList<RatesPoint> rates) {
		this.rates = rates;
	}

	public Double getEer() {
		return eer;
	}
	
	public void setEer(Double eer) {
		this.eer = eer;
	}
	
	public Histogram getFieldHistogram() {
		return fieldHistogram;
	}

	public void setFieldHistogram(Histogram fieldHistogram) {
		this.fieldHistogram = fieldHistogram;
	}

	public ArrayList<Histogram> getVariableHistograms() {
		return variableHistograms;
	}

	public void setVariableHistograms(ArrayList<Histogram> variableHistograms) {
		this.variableHistograms = variableHistograms;
	}

	public ArrayList<Long> getIndexingResults() {
		return indexingResults;
	}

	public void setIndexingResults(ArrayList<Long> indexingResults) {
		this.indexingResults = indexingResults;
	}
	
	public Double getMinEntropy() {
		return minEntropy;
	}

	public void setMinEntropy(Double minEntropy) {
		this.minEntropy = minEntropy;
	}

	public Double getFailureToCapture() {
		return failureToCapture;
	}
	
	public void setFailureToCapture(Double failureToCapture) {
		this.failureToCapture = failureToCapture;
	}

	
	@Override
	public String toString(){
		String toReturn = "\nResults:\n"+
//				this.getFieldHistogram().toString() + "\n" + //labels are part of the histogram's "toString" method
				this.getVariableHistograms().toString() + "\n\n" +
				"EER: " + this.getEer() + "\n" + 
				"FTC: " + this.getFailureToCapture() + "\n" + 
				"ZeroFAR: " + this.getZeroFAR() + "\n" +
				"rates: " + this.getRates() + "\n" +
				"min entropy: " + this.getMinEntropy() + "\n" + 
				"bits (sum of the logs of bins): " + this.getTotalLogOfBins() + "\n";
		return toReturn;
	}







	public RawScores getRawScores() {
		return rawScores;
	}



	public void setRawScores(RawScores rawScores) {
		this.rawScores = rawScores;
	}

	
	
}
