package system.base;

import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.RawScores;
import system.allcommonclasses.Results;

public class EvaluatePerformance {

	protected static Results computeEER(RawScores scores) {
		
		// This needs to be looked over
		Results results = new Results();
		Double eer = 1000.0;

		ArrayList<Double> gens = scores.genuineScores;
		ArrayList<Double> imps = scores.imposterScores;
		
		Collections.sort(gens);
		Collections.sort(imps);

		Double min = Math.min(gens.get(0), imps.get(0));
		Double max = Math.min(gens.get(gens.size()-1), imps.get(imps.size()-1));
		Double stepSize = 0.9;
		
		ArrayList<RatesPoint> rates = new ArrayList<RatesPoint>();
		
		for(Double i = min-stepSize; i<=max+stepSize; i+=stepSize){
			RatesPoint point = new RatesPoint();
			point.threshold=i;
			
			Double falseAccepts = Double.valueOf(imps.size());
			Double falseRejects = 0.0;
			for(Double d : gens){
				if(d<point.threshold){
					falseRejects += 1.0;
				}else{
					break;
				}
			}
			
			for(Double d : imps){
				if(d<point.threshold){
					falseAccepts -= 1.0;
				}else{
					break;
				}
			}
			point.far = falseAccepts/Double.valueOf(imps.size());
			point.frr = falseRejects/Double.valueOf(gens.size());
			
			rates.add(point);
		}
		
		for(RatesPoint point : rates){
			if(point.frr >= point.far){
				eer = (point.frr + point.far)/2.0;
				break;
			}
		}
		
		results.setRates(rates);
		results.setEer(eer);
		return results;
	}
	
}
