package unittests.testsystem.testmethod.testfingerprintmethods;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;

import settings.fingerprintmethodsettings.PRINTSettings;
import system.allcommonclasses.modalities.Minutia;
import system.method.feature.DistanceVariable;
import system.method.feature.PhiVariable;
import system.method.feature.SigmaVariable;
import system.method.fingerprintmethods.PRINTS;
import system.method.fingerprintmethods.Triangles;
import system.method.fingerprintmethods.PRINTS.PRINT;
import system.method.fingerprintmethods.Triangles.Triangle;

public class TestPRINTS {
	
	private PRINTSettings getSettings(){
		PRINTSettings settings = PRINTSettings.getInstance();
		
		settings.n().setValue(5);
		
		return settings;
	}
	
	@org.junit.Test
	public void testMakePRINT() {
		
//		private PRINT makePRINT(ArrayList<Minutia> minutiaList)
		
		this.getSettings();
		
		PRINTS printMethod = new PRINTS();
		PRINT computedPRINT = printMethod.new PRINT();
		
		
		try {
			Method makePRINT = printMethod.getClass().getDeclaredMethod("makePRINT", ArrayList.class);
			makePRINT.setAccessible(true);
			
			ArrayList<Minutia> points = new ArrayList<Minutia>();
			points.add(new Minutia(102,  -75, 360));
			points.add(new Minutia(52, 106, 721));
			points.add(new Minutia(60, -100, 2));
			points.add(new Minutia(32, -86, 2));
			points.add(new Minutia(200, 56, 12));
			
			computedPRINT = (PRINT) makePRINT.invoke(printMethod, points);
			
		} 
		catch (Exception e) {e.printStackTrace();}
		
		PRINT expectedPRINT = printMethod.new PRINT();

		expectedPRINT.variables.get("distance0").setPrequantizedValue(5.5);
		expectedPRINT.variables.get("sigma0").setPrequantizedValue(0.0);
		expectedPRINT.variables.get("phi0").setPrequantizedValue(116.0);
		
		expectedPRINT.variables.get("distance1").setPrequantizedValue(2.0);
		expectedPRINT.variables.get("sigma1").setPrequantizedValue(.0);
		expectedPRINT.variables.get("phi1").setPrequantizedValue(116.0);
		
		expectedPRINT.variables.get("distance2").setPrequantizedValue(2.0);
		expectedPRINT.variables.get("sigma2").setPrequantizedValue(0.0);
		expectedPRINT.variables.get("phi2").setPrequantizedValue(116.0);
		
		expectedPRINT.variables.get("distance3").setPrequantizedValue(2.0);
		expectedPRINT.variables.get("sigma3").setPrequantizedValue(0.0);
		expectedPRINT.variables.get("phi3").setPrequantizedValue(116.0);
		
		expectedPRINT.variables.get("distance4").setPrequantizedValue(2.0);
		expectedPRINT.variables.get("sigma4").setPrequantizedValue(0.0);
		expectedPRINT.variables.get("phi4").setPrequantizedValue(116.0);

		
		assertTrue(
				"\nExpected: " + expectedPRINT.prequantizedToString() + " " + 
				"\nComputed: " + computedPRINT.prequantizedToString() + " ",
				
				false // prequantized compare
				);
	}
	
}
