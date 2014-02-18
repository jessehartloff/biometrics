package system.allcommonclasses.commonstructures;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import system.biometricsystem.RatesPoint;

/**
 * 
 * The results to be returned to the interface. This is processed data that is ready to be 
 * analyzed to measure performance. May be ready to be plotted if using a GUI. 
 *
 */
public class Results {
	
	private Double eer;
	private ArrayList<RatesPoint> rates;
	private Histogram fieldHistogram;
	private ArrayList<Histogram> variableHistograms;
	private ArrayList<Long> indexingResults;
	
	public RawScores rawScores;
	
		
	public ArrayList<RatesPoint> getRates() {
		return rates;
	}
	
	public String zeroFAR() {
		for( RatesPoint r: rates){
			if (r.getFar() == 0.0) {
				String zfar = "ZeroFAR: FAR = " + r.getFar().toString() +
							  ", FRR = " + r.getFrr().toString() +
							  ", Threshold = "+ r.getThreshold().toString();
				return zfar;
			}
		}
		return "ZeroFAR Never Reached...find a better matcher";
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

	@Override
	public String toString(){
		String toReturn = "\nResults:\n"+
//				this.getFieldHistogram().toString() + "\n" + //labels are part of the histogram's "toString" method
				this.getVariableHistograms().toString() + "\n" +
				"EER:" + this.getEer() + "\n" + 
				"rates:" + this.getRates() + "\n";
		return toReturn;
	}
	
	
}
