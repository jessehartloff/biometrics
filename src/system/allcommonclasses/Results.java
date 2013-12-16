package system.allcommonclasses;

import java.util.ArrayList;

import system.base.FARFRR;

/**
 * 
 * The results to be returned to the interface. This is processed data that is ready to be 
 * analyzed to measure performance. May be ready to be plotted if using a GUI. 
 *
 */
public class Results {

	private Double eer;
	private ArrayList<FARFRR> rates;
	
	public ArrayList<FARFRR> getRates() {
		return rates;
	}

	public void setRates(ArrayList<FARFRR> rates) {
		this.rates = rates;
	}

	public Double getEer() {
		return eer;
	}
	
	public void setEer(Double eer) {
		this.eer = eer;
	}
	
	{}// TODO Results FARFRR class is kinda dumb. At least needs to be renamed
	
}
