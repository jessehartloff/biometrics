package unittests.testsystem.testmethod.testfingerprintmethods;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.math.BigInteger;

import settings.AllSettings;
import settings.fingerprintmethodsettings.TriangleSettings;
import system.allcommonclasses.modalities.Minutia;
import system.method.fingerprintmethods.Triangles;
import system.method.fingerprintmethods.Triangles.Triangle;

public class TestTriangles {

	@org.junit.Test
	public void testMakingATrianglePrequantized() {
		Triangles triangleMethod = new Triangles();
		Triangle computedTriangle = triangleMethod.new Triangle();
		
		try {
			Method makeTriangle = triangleMethod.getClass().getDeclaredMethod("makeTriangle", Minutia.class, Minutia.class, Minutia.class);
			makeTriangle.setAccessible(true);
			
			Minutia m0 = new Minutia(102,  -75, 360);
			Minutia m1 = new Minutia(52, 106, 721);
			Minutia m2 = new Minutia(52, -10, 2);
			
			computedTriangle = (Triangle) makeTriangle.invoke(triangleMethod, m0,m1,m2);
			
		} 
		catch (Exception e) {e.printStackTrace();}
		
		Triangle expectedTriangle = triangleMethod.new Triangle();

		expectedTriangle.variables.get("theta0").setPrequantizedValue(2.0);
		expectedTriangle.variables.get("x1").setPrequantizedValue(0.0);
		expectedTriangle.variables.get("y1").setPrequantizedValue(116.0);
		expectedTriangle.variables.get("theta1").setPrequantizedValue(1.0);
		expectedTriangle.variables.get("x2").setPrequantizedValue(50.0);
		expectedTriangle.variables.get("y2").setPrequantizedValue(-65.0);
		expectedTriangle.variables.get("theta2").setPrequantizedValue(0.0);
		
		expectedTriangle.setCenterX(68.6666666667);
		expectedTriangle.setCenterY(7.0);
		
		assertTrue(
				"\nExpected: " + expectedTriangle.prequantizedToString() + " " + 
				expectedTriangle.getCenterX() + " " + expectedTriangle.getCenterY() +
				"\nComputed: " + computedTriangle.prequantizedToString() + " " +
				computedTriangle.getCenterX() + " " + computedTriangle.getCenterY(),
				
				expectedTriangle.prequantizedEquals(computedTriangle) &&
				Math.abs(expectedTriangle.getCenterX() - computedTriangle.getCenterX()) < 0.001 &&
				Math.abs(expectedTriangle.getCenterY() - computedTriangle.getCenterY()) < 0.001
				);
	}
	
//	@org.junit.Test
//	public void testTrianglesAsBigInts() {
//		TriangleSettings settings = TriangleSettings.getInstance();
//		Triangles triangleMethod = new Triangles();
//		
////		ArrayList<Long> boundaries = new ArrayList<Long>(Arrays.asList(5L,10L,15L,20L));
////		settings.triangleSettings.theta0.setBinBoundaries(boundaries);
////		settings.triangleSettings.x1.setBinBoundaries(boundaries);
////		settings.triangleSettings.y1.setBinBoundaries(boundaries);
////		settings.triangleSettings.theta1.setBinBoundaries(boundaries);
////		settings.triangleSettings.x2.setBinBoundaries(boundaries);
////		settings.triangleSettings.y2.setBinBoundaries(boundaries);
////		settings.triangleSettings.theta2.setBinBoundaries(boundaries);
//		
//		settings.theta0().setBins(5);
//		settings.x1().setBins(8);
//		settings.y1().setBins(9);
//		settings.theta1().setBins(2);
//		settings.x2().setBins(14);
//		settings.y2().setBins(7);
//		settings.theta2().setBins(16);
//		
//		Triangle t = triangleMethod.new Triangle();
//
//		t.variables.get("theta0").setQuantizedValue(2L);
//		t.variables.get("x1").setQuantizedValue(7L);
//		t.variables.get("y1").setQuantizedValue(1L);
//		t.variables.get("theta1").setQuantizedValue(1L);
//		t.variables.get("x2").setQuantizedValue(0L);
//		t.variables.get("y2").setQuantizedValue(1L);
//		t.variables.get("theta2").setQuantizedValue(10L);
//		
//		BigInteger expected = BigInteger.valueOf(1513498);
//		BigInteger computed = t.toBigInt();
//		
//		assertTrue("\nExpected: " + expected + "\nComputed: " + computed,
//				expected.equals(computed));
//	}
	
//	@org.junit.Test
//	public void testBigIntsAsTriangles() {
//		TriangleSettings settings = TriangleSettings.getInstance();
//		Triangles triangleMethod = new Triangles();
//		
//		settings.theta0().setBins(5);
//		settings.x1().setBins(8);
//		settings.y1().setBins(9);
//		settings.theta1().setBins(2);
//		settings.x2().setBins(14);
//		settings.y2().setBins(7);
//		settings.theta2().setBins(16);
//		
//		Triangle computed = triangleMethod.new Triangle();
//		Triangle expected = triangleMethod.new Triangle();
//
//		expected.variables.get("theta0").setQuantizedValue(2L);
//		expected.variables.get("x1").setQuantizedValue(7L);
//		expected.variables.get("y1").setQuantizedValue(1L);
//		expected.variables.get("theta1").setQuantizedValue(1L);
//		expected.variables.get("x2").setQuantizedValue(0L);
//		expected.variables.get("y2").setQuantizedValue(1L);
//		expected.variables.get("theta2").setQuantizedValue(10L);
//		
//		BigInteger bigInt = BigInteger.valueOf(1513498);
//		computed.fromBigInt(bigInt);
//		
//		assertTrue("\nExpected: " + expected + "\nComputed: " + computed,
//				expected.equals(computed));
//	}
//	
	
}
