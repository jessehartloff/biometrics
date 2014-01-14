package system.allcommonclasses.commonstructures;

import java.util.ArrayList;

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
	
	public ArrayList<Long> histogram;
	
	public RawScores(){
		genuineScores = new ArrayList<Double>();
		imposterScores = new ArrayList<Double>();

		indexRankings = new ArrayList<Long>();
		
		histogram = new ArrayList<Long>();
	}
}
