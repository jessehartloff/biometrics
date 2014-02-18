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
	
	public RawScores rawScores;
	
		
	public ArrayList<RatesPoint> getRates() {
		return rates;
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

	@Override
	public String toString(){
		String toReturn = "\nResults:\n"+
//				this.getFieldHistogram().toString() + "\n" + //labels are part of the histogram's "toString" method
				this.getVariableHistograms().toString() + "\n\n" +
				"EER: " + this.getEer() + "\n" + 
				"ZeroFAR: " + this.getZeroFAR() + "\n" +
//				"rates: " + this.getRates() + "\n" +
				"min entropy: " + this.getMinEntropy() + "\n";
		return toReturn;
	}
	
	
}
