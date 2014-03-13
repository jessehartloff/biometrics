package optimize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import system.allcommonclasses.commonstructures.Results;

/**
 * Keeps lists of all optimization results
 * Each optimization criteria is represented as an ArrayList of Results
 * When pushing a Results to the OptimizationResults object, it is analyzed within optimization result
 *  and added to the correct optimization criteria through sorting methods
 *  
 * @author thomaseffland
 *
 */

public class OptimizationResults {
	/**
	 * Member Variables
	 */
	ArrayList< Results > bestEERs;
	ArrayList< Results > bestZeroFARs;
	ArrayList< Results > bestAverageEERandZeroFARs;
	//ArrayList< Results > bestEERZeroFARDiff;
	Integer maxArraySize;
	/**
	 * Contructor
	 */
	public OptimizationResults(Integer maxArrSize) {
		bestEERs = new ArrayList< Results >();
		bestZeroFARs = new ArrayList< Results >();
		bestAverageEERandZeroFARs = new ArrayList< Results >();
		//ArrayList< Results > bestEERZeroFARDiff = new ArrayList< Results >();
		maxArraySize = maxArrSize;
	}
	/**
	 * Display the top N results out of maxArraySize.
	 * Since results are stored in each list by descending order, it is just the first N
	 */
	public void displayTopNResults ( Integer N ) {
		//make sure there are enough results to display
		if (bestEERs.size() < N ) {
			N = bestEERs.size();
			System.out.println("Not enough results... Displaying all " + N.toString() + " of them:");
		}
		for (Integer i=0; i < N; i++) {
			System.out.println("\n====== Result #"+i.toString()+" ======");
			System.out.println("EER:"+ bestEERs.get(i).getEer().toString());
			System.out.println("FRR at ZeroFAR:"+ bestZeroFARs.get(i).getZeroFAR().getFRR().toString());
			System.out.println("ERR & ZeroFAR Avg:"+ bestAverageEERandZeroFARs.get(i).getAverageEERandZeroFAR().toString());
		}
	}
	
	/**
	 * Take in a Results object and analyze it.
	 * For each optimization criteria, we will keep the top maxArraySize according to that criteria.
	 */
	public void commitResult( Results result ) {
		tryBestEERs( result );
		tryBestZeroFARs( result );
		tryBestAverageEERandZeroFARs( result );
	}
	
	//TODO Tom - make result array searches binary instead of linear
	/**
	 * See if result is in bestEERs
	 * If so, add to result, sort them, and remove the highest if it exceeds the maxArraySize
	 * @param result
	 * @return
	 */
	public void tryBestEERs ( Results result ) {
		//if we haven't filled it up yet
		if( bestEERs.isEmpty() ) bestEERs.add(result);
		else {
			for( Results r : bestEERs) {
				//if this is smaller than one, we want to add it
				if( result.getEer() < r.getEer() ) {
					bestEERs.add(result);
					//sort to see which result we should drop
					Collections.sort(bestEERs, new CompareResultEER());
					//drop a result
					if (bestEERs.size() > maxArraySize) {
						bestEERs.remove(maxArraySize - 1);
					}
					System.out.println("New low result commited to:"+ bestEERs.toString());
					return;
				}
			}
			//if this result isn't smaller than any other, but the array isn't filled up
			if ( bestEERs.size() < maxArraySize ) bestEERs.add( result );
		}
	}
	
	/**
	 * See if result is in bestZeroFARs
	 * If so, add to result, sort them, and remove the highest if it exceeds the maxArraySize
	 * @param result
	 * @return
	 */
	public void tryBestZeroFARs ( Results result ) {
		//make sure it's not empty
		if( bestZeroFARs.isEmpty() ) bestZeroFARs.add(result);
		else {
			for( Results r : bestZeroFARs) {
				//if this is smaller than one, we want to add it
				if( result.getZeroFAR().getFRR() < r.getZeroFAR().getFRR() ) {
					bestZeroFARs.add(result);
					//sort to see which result we should drop
					Collections.sort(bestZeroFARs, new CompareResultZeroFAR());
					//drop a result
					if (bestZeroFARs.size() > maxArraySize) {
						bestZeroFARs.remove(maxArraySize - 1);
					}
					System.out.println("New low result commited to:"+ bestEERs.toString());
					return;
				}
			}
			//if this result isn't smaller than any other, but the array isn't filled up
			if ( bestZeroFARs.size() < maxArraySize ) bestZeroFARs.add( result );
		}
		
	}
	
	/**
	 * See if result is in bestAverageEERandZeroFARs.
	 * If so, add to result, sort them, and remove the highest if it exceeds the maxArraySize
	 * @param result
	 * @return
	 */
	public void tryBestAverageEERandZeroFARs ( Results result ) {
		Double resultAvg = result.getAverageEERandZeroFAR();
		//make sure it's not empty
		if( bestAverageEERandZeroFARs.isEmpty() ) bestAverageEERandZeroFARs.add(result);
		else {
			for( Results r : bestAverageEERandZeroFARs) {
				//if this is smaller than one, we want to add it
				Double rAvg = r.getAverageEERandZeroFAR();
				if( resultAvg < rAvg ) {
					bestAverageEERandZeroFARs.add(result);
					//sort to see which result we should drop
					Collections.sort(bestAverageEERandZeroFARs, new CompareResultAvgEERandZeroFAR());
					//drop a result
					if (bestAverageEERandZeroFARs.size() > maxArraySize) {
						bestAverageEERandZeroFARs.remove(maxArraySize - 1);
	
					}
					System.out.println("New low result commited to:"+ bestEERs.toString());
					return;
				}
			}
			//if this result isn't smaller than any other, but the array isn't filled up
			if ( bestAverageEERandZeroFARs.size() < maxArraySize ) bestAverageEERandZeroFARs.add( result );
		}
		
	}
	
	/**
	 * Override comparators for sorting optimization results.
	 * Each comparator is used for its optimization criteria anaylzer method.
	 * Since we want low numbers to get pushed to the front, these return reverse order.
	 * 
	 * @author thomaseffland
	 *
	 */
	public class CompareResultEER implements Comparator<Results> {
	    @Override
	    public int compare(Results r1, Results r2) {
	        return - r1.getEer().compareTo(r2.getEer()); //negative returns reverse order
	    }
	}
	public class CompareResultZeroFAR implements Comparator<Results> {
	    @Override
	    public int compare(Results r1, Results r2) {
	        return - r1.getZeroFAR().getFRR().compareTo(r2.getZeroFAR().getFRR()); //negative returns reverse order
	    }
	}
	public class CompareResultAvgEERandZeroFAR implements Comparator<Results> {
	    @Override
	    public int compare(Results r1, Results r2) {
	    	Double r1Avg = r1.getAverageEERandZeroFAR();
	    	Double r2Avg = r2.getAverageEERandZeroFAR();
	        return - r1Avg.compareTo(r2Avg); //negative returns reverse order
	    }
	}
}