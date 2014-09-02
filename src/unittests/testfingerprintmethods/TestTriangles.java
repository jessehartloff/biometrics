package unittests.testfingerprintmethods;

import system.allcommonclasses.modalities.Minutia;
import system.method.fingerprintmethods.Triangles;
import system.method.fingerprintmethods.Triangles.Triangle;

import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

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
	
}
