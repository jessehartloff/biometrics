package system.allcommonclasses.commonstructures;

import java.util.ArrayList;

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
	//indexingResults
	
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
	
}
