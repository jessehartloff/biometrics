package system.allcommonclasses.commonstructures;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 
 * A collection of intermediate scores from the scheme such and impostor/genuine matching scores or 
 * index rankings.
 *
 */
public class RawScores {

	public ArrayList<Double> genuineScores;
	public ArrayList<Double> imposterScores;

	public ArrayList<Long> indexRankings;
	
	public ArrayList<BigInteger> fieldHistogramValues;
	public LinkedHashMap<String, ArrayList<Long>> variableHistogramValues;
	
	public RawScores(){
		genuineScores = new ArrayList<Double>();
		imposterScores = new ArrayList<Double>();
		indexRankings = new ArrayList<Long>();
		fieldHistogramValues = new ArrayList<BigInteger>();
		variableHistogramValues = new LinkedHashMap<String, ArrayList<Long>>();
	}
}
