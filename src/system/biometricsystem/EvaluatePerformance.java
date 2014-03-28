package system.biometricsystem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import system.allcommonclasses.commonstructures.Histogram;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Results;
import system.quantizer.Quantizer;

public class EvaluatePerformance {

	
	protected static Results processResults(RawScores rawScores) {
		Results results = new Results();
		
		results.setRates(EvaluatePerformance.computeRates(rawScores));
		results.setEer(EvaluatePerformance.computeEER(results.getRates()));	
		
		results.setFieldHistogram(EvaluatePerformance.computeFieldHistogram(rawScores));
		results.setVariableHistograms(EvaluatePerformance.computeVariableHistograms(rawScores));
		results.setChiSquare(EvaluatePerformance.computeChiSquare(results));
		results.setChiSquareValues(EvaluatePerformance.computeChiSquareValues(results));
		results.setHitRates(EvaluatePerformance.computeIndexingResults(rawScores));
		results.setPenetrationCoefficient(EvaluatePerformance.computePenetrationCoefficient(results.getHitRates()));

		results.setMinEntropy(results.getFieldHistogram().getMinEntropy());
		results.setZeroFAR(EvaluatePerformance.computeZeroFAR(results));
		
		for(Histogram varHist : results.getVariableHistograms()){
			System.out.println(varHist.getVariableName() + ": " + varHist.getMinEntropy());
		}
		results.setRawScores(rawScores);
		
		results.setTotalLogOfBins(Quantizer.getQuantizer().getTotalLogOfBins());
		
		return results;
	}
	
	private static Double computePenetrationCoefficient(ArrayList<Double> hitRates) {
		Double sum = 0.0;
		for(Double d : hitRates){
			sum += d;
		}
		return sum/new Double(hitRates.size());
	}

	/**  VARIABLE HISTO CHI SQUARED
	 * Calculate chi squared such that we are uniform in sample set
	 * This particular method assumes an uniform reference distribution
	 * and compares the input distribution to this theoretical reference distribution.
	 * @param h (typically field histogram)
	 * @return cs
	 */
	private static ArrayList<Double> computeChiSquareValues(Results results) {
		ArrayList<Double> chiSquareds = new ArrayList<Double>();
		for ( Histogram h : results.getVariableHistograms()){
			Long N = h.getNumberSamples();
			if(N != null){
				Double n = new Double(h.histogram.values().size()); //number of bins
				Double E = N/n; //uniform bin frequency
				Double cs = 0.0;
				for (Long frequency : h.histogram.values()) {
					cs += Math.pow(frequency - E, 2.0)/E;
				}
				chiSquareds.add(cs);
			}
		}
		return chiSquareds;
	}
	
	/**  FIELD HISTO CHI SQUARED
	 * Calculate chi squared such that we are uniform in sample set of large field size
	 * This particular method assumes an uniform reference distribution with frequency 1 per bin
	 * and compares the input distribution to this theoretical reference distribution.
	 * @param h (typically field histogram)
	 * @return cs
	 */
	private static Double computeChiSquare(Results results) {
		Long N = results.getFieldHistogram().getNumberSamples();
		Double cs = 0.0;
		if(N != null){
			for (Long frequency : results.getFieldHistogram().histogram.values()) {
				cs += Math.pow(frequency - 1, 2.0);
			}
			// missing bins will be 0 => (0-1)^2/1 = 1
			//there are N- histogram.size of these => add N- histogram.size to cs
			cs += N-results.getFieldHistogram().histogram.size();
			return cs;
		}
		return Double.POSITIVE_INFINITY;
	}


	private static ZeroFAR computeZeroFAR(Results results) {
		ZeroFAR zeroFAR = new ZeroFAR();
		zeroFAR.setFRR(Double.POSITIVE_INFINITY);
		zeroFAR.setThreshold(Double.POSITIVE_INFINITY);
		
		for(RatesPoint r: results.getRates()){
			if (r.getFar().equals(0.0)) {
				zeroFAR.setFRR(r.getFrr());
				zeroFAR.setThreshold(r.getThreshold());
				break;
			}
		}
		return zeroFAR;
	}


	private static ArrayList<Double> computeIndexingResults(RawScores rawScores) {
		ArrayList<Double> hitRates = new ArrayList<Double>();
		ArrayList<Long> rankings = rawScores.indexRankings;
		Collections.sort(rankings);
//		Long min = rankings.get(0);
		Integer totalRankings = rankings.size();
		if(totalRankings.equals(0)){
			return hitRates;
		}
		Long max = rankings.get(totalRankings-1);
		
		Integer currentIndex = 0;
		for(int i=0; i<=max.intValue(); i++){
			while(currentIndex < totalRankings && rankings.get(currentIndex) <= i){
				currentIndex++;
			}
			hitRates.add(currentIndex.doubleValue()/totalRankings.doubleValue());
		}
		
		return hitRates;
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

		ArrayList<Double> gens = rawScores.genuineScores;
		ArrayList<Double> imps = rawScores.imposterScores;
		
		//we want to know min and max scores to compute over
		Double min = 0.0;
		Double max = 0.0;

		if(!gens.isEmpty() && !imps.isEmpty()){
			min = Math.min(gens.get(0), imps.get(0));
			max = Math.max(gens.get(gens.size()-1), imps.get(imps.size()-1));
		}

//		Double stepSize = GlobalSettings.getInstance().getEerStepSize(); //LATER if we want non-integer scores
		Double stepSize = 1.0; 
		Double offset = stepSize/10.0;
		
		ArrayList<RatesPoint> rates = new ArrayList<RatesPoint>();
		
		//offset by half the resolution so we don't have to to deal with machine imprecision at the score ticks
		for(Double threshold = min - offset; threshold <= max + offset; threshold += stepSize){
			RatesPoint point = new RatesPoint();
			point.setThreshold(threshold);
			
			Double falseAccepts = Double.valueOf(imps.size());
			Double falseRejects = 0.0;
			
			for(Double d : gens){
				if(d<point.getThreshold()){
					falseRejects += 1.0;
				}else{
					break;
				}
			}
			
			for(Double d : imps){
				if(d<point.getThreshold()){
					falseAccepts -= 1.0;
				}else{
					break;
				}
			}
			
			point.setFar(falseAccepts/Double.valueOf(imps.size()));
			point.setFrr(falseRejects/Double.valueOf(gens.size()));
			
			rates.add(point);
		}
		
		return rates;
	}

	
	protected static Double computeEER(ArrayList<RatesPoint> rates){
		
		Double eer = Double.POSITIVE_INFINITY;
		
		for(RatesPoint point : rates){
			if(point.getFrr() >= point.getFar()){
				eer = (point.getFrr() + point.getFar())/2.0;
				break;
			}
		}
		
		return eer;
	}
	
	
}
