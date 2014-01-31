package unittests.testsystem.testallcommonclasses.testsettings;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import settings.settingsvariables.SettingsMethodVariable;

public class TestMethodVariableSettings {

	@org.junit.Test
	public void testComputeBinBoundaries() {
		
		SettingsMethodVariable var = new SettingsMethodVariable();
		var.setBins(4);
		
		ArrayList<Long> prequantizedValue = new ArrayList<Long>();
		prequantizedValue.add(-1000L);
		prequantizedValue.add(1100L);
		prequantizedValue.add(2L);
		prequantizedValue.add(100L);
		prequantizedValue.add(-10L);
		prequantizedValue.add(15L);
		prequantizedValue.add(1600L);
		prequantizedValue.add(35L);
		var.computeBinBoundaries(prequantizedValue);
		
		ArrayList<Long> expected = new ArrayList<Long>();
		expected.add(-4L);
		expected.add(25L);
		expected.add(600L);
		
		ArrayList<Long> computed = var.getBinBoundaries();
		
		assertTrue("\nExpected: " + expected + "\nComputed: " + computed,
				expected.get(0).equals(computed.get(0)) && 
				expected.get(1).equals(computed.get(1)) && 
				expected.get(2).equals(computed.get(2)));
	}
	
	
	@org.junit.Test
	public void testBinPlacement() {
		
		SettingsMethodVariable var = new SettingsMethodVariable();
		var.setBins(4);
		
		ArrayList<Long> prequantizedValue = new ArrayList<Long>();
		prequantizedValue.add(-1000L);
		prequantizedValue.add(1100L);
		prequantizedValue.add(2L);
		prequantizedValue.add(100L);
		prequantizedValue.add(-10L);
		prequantizedValue.add(15L);
		prequantizedValue.add(1600L);
		prequantizedValue.add(35L);
		var.computeBinBoundaries(prequantizedValue);
		
//		ArrayList<Long> expected = new ArrayList<Long>();
//		expected.add(-4L);
//		expected.add(25L);
//		expected.add(600L);
		
		Boolean result = true;
		result &= var.findBin(-5L).equals(0L);
		result &= var.findBin(-500L).equals(0L);
		result &= var.findBin(-3L).equals(1L);
		result &= var.findBin(24L).equals(1L);
		result &= var.findBin(26L).equals(2L);
		result &= var.findBin(599L).equals(2L);
		result &= var.findBin(601L).equals(3L);
		result &= var.findBin(1045L).equals(3L);
		
		ArrayList<Long> computed = var.getBinBoundaries();
		
		assertTrue("This message is not helpful",
				result);
	}
	
	@org.junit.Test
	public void testBinsToBits() {
		Boolean result = true;
		ArrayList<Integer> bins = new ArrayList<Integer>(Arrays.asList(2,3,4,5,6,7,8,9,10,13,15,16,17)); 
		ArrayList<Integer> expectedBits = new ArrayList<Integer>(Arrays.asList(1,2,2,3,3,3,3,4, 4, 4, 4, 4, 5)); 
		ArrayList<Integer> computedBits = new ArrayList<Integer>(); 
		int n=bins.size();
		SettingsMethodVariable forTheFunction = new SettingsMethodVariable();
		for(int i=0; i<n; i++){
			result = result && forTheFunction.binsToBits(bins.get(i)) == expectedBits.get(i);
			computedBits.add(forTheFunction.binsToBits(bins.get(i)));
		}
		assertTrue("\nexpected: " + expectedBits + "\ncomputed: " + computedBits, result);
	}
	
	
	
}
