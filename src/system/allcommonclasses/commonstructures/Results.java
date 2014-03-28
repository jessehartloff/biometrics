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
	private Double AverageEERandZeroFAR;
	private ArrayList<RatesPoint> rates;
	private Histogram fieldHistogram;
	private ArrayList<Histogram> variableHistograms;
	private ArrayList<Long> indexingResults;
	private Double minEntropy;
	private Double failureToCapture;
	private Double totalLogOfBins;
	private Double chiSquare;
	private ArrayList<Double> chiSquareValues;
	private Long runTime;
	private String paramString;

	private RawScores rawScores;
	
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

	public Double getChiSquare() {
		return chiSquare;
	}

	public void setChiSquare(Double chiSquare) {
		this.chiSquare = chiSquare;
	}

	public ArrayList<Double> getChiSquareValues() {
		return chiSquareValues;
	}

	public void setChiSquareValues(ArrayList<Double> chiSquareValues) {
		this.chiSquareValues = chiSquareValues;
	}
	
	public RawScores getRawScores() {
		return rawScores;
	}

	public void setRawScores(RawScores rawScores) {
		this.rawScores = rawScores;
	}
	
	public Double getAverageEERandZeroFAR() {
		if (AverageEERandZeroFAR == null) {
			AverageEERandZeroFAR = (eer + zeroFAR.getFRR())/2.;
		}
		return AverageEERandZeroFAR;
	}

	public void setAverageEERandZeroFAR(Double averageEERandZeroFAR) {
		AverageEERandZeroFAR = averageEERandZeroFAR;
	}	
	@Override
	public String toString(){
		String toReturn = "\nResults:\n"+
//				this.getFieldHistogram().toString() + "\n" + //labels are part of the histogram's "toString" method
				this.getVariableHistograms().toString() + "\n" +
				"Variable Histogram Chi Square Values:" + this.getChiSquareValues().toString() + "\n"+
				"Field Histogram Chi Square: " + this.getChiSquare().toString() + "\n" +
				"EER: " + this.getEer() + "\n" + 
				"FTC: " + this.getFailureToCapture() + "\n" + 
				"ZeroFAR: " + this.getZeroFAR() + "\n" +
				"rates: " + this.getRates() + "\n" +
				"min entropy: " + this.getMinEntropy() + "\n" + 
				"bits (sum of the logs of bins): " + this.getTotalLogOfBins() + "\n";
		return toReturn;
	}
	public String toFitnessString() {
		String toReturn = "\nResults:\n"+
//				this.getFieldHistogram().toString() + "\n" + //labels are part of the histogram's "toString" method
//				this.getVariableHistograms().toString() + "\n" +
//				"Variable Histogram Chi Square Values:" + this.getChiSquareValues().toString() + "\n"+
//				"Field Histogram Chi Square: " + this.getChiSquare().toString() + "\n" +
				"EER: " + this.getEer() + "\n" + 
//				"FTC: " + this.getFailureToCapture() + "\n" + 
				"FRR at ZeroFAR: " + this.getZeroFAR().getFRR() + "\n" +
				"AverageEERandZeroFAR: " + this.getAverageEERandZeroFAR() + "\n" +
//				"rates: " + this.getRates() + "\n" +
//				"min entropy: " + this.getMinEntropy() + "\n" + 
				"bin bits: " + this.getTotalLogOfBins() + "\n";
		return toReturn;
	}
	/**
	 * Reutrns result represented as string with the parameters written as text
	 * @return
	 */
	public String toResultString() {
		String toReturn = "\nParameters:\n"+paramString+
				"\nResults:\n"+
//				this.getFieldHistogram().toString() + "\n" + //labels are part of the histogram's "toString" method
//				this.getVariableHistograms().toString() + "\n" +
//				"Variable Histogram Chi Square Values:" + this.getChiSquareValues().toString() + "\n"+
//				"Field Histogram Chi Square: " + this.getChiSquare().toString() + "\n" +
				"EER: " + this.getEer() + "\n" + 
//				"FTC: " + this.getFailureToCapture() + "\n" + 
				"FRR at ZeroFAR: " + this.getZeroFAR().getFRR() + "\n" +
				"AverageEERandZeroFAR: " + this.getAverageEERandZeroFAR() + "\n" +
//				"rates: " + this.getRates() + "\n" +
//				"min entropy: " + this.getMinEntropy() + "\n" + 
				"bin bits: " + this.getTotalLogOfBins() + "\n"+
				"run time: " +this.runTime+" sec\n";
				
		return toReturn;
	}
	
	public String toRatesString() {
		String toReturn = "([ ";
		for (RatesPoint r : rates) {
			toReturn += "["+r.getFar()+", "+r.getFrr()+"],";
		}
		toReturn += "])";
		return toReturn;
	}

	public Long getRunTime() {
		return runTime;
	}

	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}

	public String getParamString() {
		return paramString;
	}

	public void setParamString(String paramString) {
		this.paramString = paramString;
	}

}
