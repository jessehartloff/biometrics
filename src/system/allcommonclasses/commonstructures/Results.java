package system.allcommonclasses.commonstructures;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import system.base.RatesPoint;

/**
 * 
 * The results to be returned to the interface. This is processed data that is ready to be 
 * analyzed to measure performance. May be ready to be plotted if using a GUI. 
 *
 */
public class Results {

	// combine RawScores with this?
	
	private Double eer;
	private ArrayList<RatesPoint> rates;
	private Histogram fieldHistogram;
	private ArrayList<Histogram> variableHistograms;
	private ArrayList<Long> indexingResults;
	
	// TODO Jim - histograms
	
	
	public ArrayList<RatesPoint> getRates() {
		return rates;
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
				"EER:" + this.getEer() + "\n" + 
				"rates:" + this.getRates() + "\n";
		
		return toReturn;
	}
	
	
}
