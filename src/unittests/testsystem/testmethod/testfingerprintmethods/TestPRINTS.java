package unittests.testsystem.testmethod.testfingerprintmethods;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;

import settings.fingerprintmethodsettings.PRINTSettings;
import system.allcommonclasses.modalities.Minutia;
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

//		expectedTriangle.variables.get("theta0").setPrequantizedValue(2L);
//		expectedTriangle.variables.get("x1").setPrequantizedValue(0L);
//		expectedTriangle.variables.get("y1").setPrequantizedValue(116L);
//		expectedTriangle.variables.get("theta1").setPrequantizedValue(1L);
//		expectedTriangle.variables.get("x2").setPrequantizedValue(50L);
//		expectedTriangle.variables.get("y2").setPrequantizedValue(-65L);
//		expectedTriangle.variables.get("theta2").setPrequantizedValue(0L);
//		
//		expectedTriangle.setCenterX(68.6666666667);
//		expectedTriangle.setCenterY(7.0);
		
		assertTrue(
				"\nExpected: " + expectedPRINT.prequantizedToString() + " " + 
				"\nComputed: " + computedPRINT.prequantizedToString() + " ",
				
				false
				);
	}
	
}
