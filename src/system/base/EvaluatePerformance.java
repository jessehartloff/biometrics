package system.base;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.commonstructures.Histogram;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Results;
import system.allcommonclasses.settings.GlobalSettings;

public class EvaluatePerformance {


	protected static Results processResults(RawScores rawScores) {
		Results results = new Results();
		
		results.setRates(EvaluatePerformance.computeRates(rawScores));
		results.setEer(EvaluatePerformance.computeEER(results.getRates()));	
		
		results.setFieldHistogram(EvaluatePerformance.computeFieldHistogram(rawScores));
		results.setVariableHistograms(EvaluatePerformance.computeVariableHistograms(rawScores));
		results.setIndexingResults(EvaluatePerformance.computeIndexingResults(rawScores));
		
		results.rawScores = rawScores;
		
		return results;
	}
	

	private static ArrayList<Long> computeIndexingResults(RawScores rawScores) {
		// TODO Jesse - compute indexing results
		return null;
	}

	private static ArrayList<Histogram> computeVariableHistograms(RawScores rawScores) {
		ArrayList<Histogram> variableHistograms = new ArrayList<Histogram>();
		for(String var : rawScores.variableHistogramValues.keySet()){
			Histogram variableHistogram = new Histogram();
			variableHistogram.setVariableName(var + " histogram");
			for(Long longVal : rawScores.variableHistogramValues.get(var)){
				BigInteger bin = BigInteger.valueOf(longVal);
				if(variableHistogram.histogram.containsKey(bin)){
					variableHistogram.histogram.put(bin, variableHistogram.histogram.get(bin) + 1L);
				} else {
					variableHistogram.histogram.put(bin, 1L);
				}
			}
			variableHistograms.add(variableHistogram);
		}
		return variableHistograms;
	}

	private static Histogram computeFieldHistogram(RawScores rawScores) {
		Histogram fieldHistogram = new Histogram();
		fieldHistogram.setVariableName("Field Histogram");
		for(BigInteger bigInt : rawScores.fieldHistogramValues){
			if(fieldHistogram.histogram.containsKey(bigInt)){
				fieldHistogram.histogram.put(bigInt, fieldHistogram.histogram.get(bigInt) + 1L);
			} else {
				fieldHistogram.histogram.put(bigInt, 1L);				
			}
		}
		return fieldHistogram;
	}

	protected static ArrayList<RatesPoint> computeRates(RawScores rawScores) {
		
		Collections.sort(rawScores.genuineScores);
		Collections.sort(rawScores.imposterScores);
		
		// This needs to be looked over
		Results results = new Results();

		ArrayList<Double> gens = rawScores.genuineScores;
		ArrayList<Double> imps = rawScores.imposterScores;
		
		//we want to know min and max scores to compute over
		Double min = 0.0;
		Double max = 0.0;

		if(!gens.isEmpty() && !imps.isEmpty()){
			min = Math.min(gens.get(0), imps.get(0));
			max = Math.max(gens.get(gens.size()-1), imps.get(imps.size()-1));
		}
		
		Double stepSize = GlobalSettings.getInstance().getEerStepSize();
		Double offset = stepSize/2.0;
		
		ArrayList<RatesPoint> rates = new ArrayList<RatesPoint>();
		
		//offset by half the resolution so we don't have to to deal with machine imprecision at the score ticks
		for(Double threshold = min - offset; threshold <= max + offset; threshold += stepSize){
			RatesPoint point = new RatesPoint();
			point.threshold= threshold;
			
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
		
		return rates;
	}

	
	protected static Double computeEER(ArrayList<RatesPoint> rates){
		
		Double eer = Double.POSITIVE_INFINITY;
		
		for(RatesPoint point : rates){
			if(point.frr >= point.far){
				eer = (point.frr + point.far)/2.0;
				break;
			}
		}
		
		return eer;
	}
	
	
}
