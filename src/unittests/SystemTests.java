package unittests;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.*;
import system.allcommonclasses.settings.MethodVariableSettings;
import system.allcommonclasses.settings.Settings;
import system.allcommonclasses.transformations.*;
import system.hasher.Hasher;
import system.hasher.StraightHasher;
import system.makefeaturevector.fingerprintmethods.*;
import system.makefeaturevector.fingerprintmethods.Triangles.Triangle;

public class SystemTests {

	{}// TODO ongoing - tons of unit tests

	//do this test with a serialized testSettings
//	Double score1 = hasher.compareTemplateWithBiometric(enrolledTemplate, test.test);
//	Double score = hasher.compareTemplates(enrolledTemplate, testTemplates);
//	
//	assert(Math.abs(score1 - score) < 0.001); {} //  test this 
	
	//private Triangle makeTriangle(Minutia m0, Minutia m1, Minutia m2){
	
	@org.junit.Test
	public void testSHA(){
		Transformation sha = new SHA2();
		BigInteger initial = new BigInteger("546869732069732061206C6F74206D6F72652074657874207468616E2049"+
		"2077726F746520666F7220746865206669727374207465737420746F2073656520696620746865206861736820776F72"+
		"6B7320666F72206C6F6E67657220737472696E67732E2E2E0D0A0D0A616E642070756E6374756174696F6E20616E6420"+
		"6F7468657220736869742E", 16);
		BigInteger computed = sha.transform(initial);
		BigInteger expected = new BigInteger("EB2AF3C51C4F695880FAFCD889408DEAEB74E665E1205CDEEE8A23C0CB3B5C22", 16);
		// hash generated using http://www.convertstring.com/Hash/SHA256 must convert input to ascii first
		assertTrue("\nExpected: " + expected + "\nComputed: " + computed,
				 computed.compareTo(expected) == 0);
	}
	

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

		expectedTriangle.variables.get("theta0").setPrequantizedValue(2L);
		expectedTriangle.variables.get("x1").setPrequantizedValue(0L);
		expectedTriangle.variables.get("y1").setPrequantizedValue(116L);
		expectedTriangle.variables.get("theta1").setPrequantizedValue(1L);
		expectedTriangle.variables.get("x2").setPrequantizedValue(50L);
		expectedTriangle.variables.get("y2").setPrequantizedValue(-65L);
		expectedTriangle.variables.get("theta2").setPrequantizedValue(0L);
		
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
	

	@org.junit.Test
	public void testMinutiaSorting() {
		boolean result = true;

		Minutia reference = new Minutia(20,150, 359);
		Minutia m0 = new Minutia(0,0,210); // distance to reference: 151.3
		Minutia m1 = new Minutia(41, 299, 21); // distance to reference: 150.5
		Minutia m2 = new Minutia(50,50,0); // distance to reference: 104.4
		Minutia m3 = new Minutia(20, 150, 5); // distance to reference: 0.0
		Minutia m4 = new Minutia(21, 150, 27); // distance to reference: 1.0
		Minutia m5 = new Minutia(20, 148, 6); // distance to reference: 1.4
		Minutia m6 = new Minutia(-10, -10, 55); // distance to reference: 162.8
		Minutia m7 = new Minutia(1000, 1000, 12); // distance to reference: 1297.3
		Minutia m8 = new Minutia(100, 140, 400); // distance to reference: 80.6

		ArrayList<Minutia> expectedPoints = 
				new ArrayList<Minutia>(Arrays.asList(m3,m4,m5,m8,m2,m1,m0,m6,m7));
		ArrayList<Minutia> computedPoints = 
				new ArrayList<Minutia>(Arrays.asList(m0,m1,m2,m3,m4,m5,m6,m7,m8));
		
		Collections.sort(computedPoints, reference.getComparator());
		
		for(int i=0; i < expectedPoints.size(); i++){
			result &= (expectedPoints.get(i) == computedPoints.get(i) );
		}
		
		assertTrue("\nExpected: " + expectedPoints + "\nComputed: " + computedPoints,
				result);
	}
	
	public void testThetaSetter(Long setTo, Long expected) {
		Minutia m0 = new Minutia();
		m0.setTheta(setTo);
		Long computed = m0.getTheta();
		assertTrue("\nExpected: " + expected + "\nComputed: " + computed,
				expected.equals(computed));
	}
	@org.junit.Test public void thetaSetter0(){this.testThetaSetter(360L, 0L);}
	@org.junit.Test public void thetaSetter1(){this.testThetaSetter(4340930L, 50L);}
	@org.junit.Test public void thetaSetter2(){this.testThetaSetter(0L, 0L);}
	@org.junit.Test public void thetaSetter4(){this.testThetaSetter(-50L, 310L);}
	@org.junit.Test public void thetaSetter5(){this.testThetaSetter(183L, 183L);}
	@org.junit.Test public void thetaSetter6(){this.testThetaSetter(-9770L, 310L);}
	
	@org.junit.Test
	public void testTrianglesAsBigInts() {
		Settings settings = new Settings();
		Triangles triangleMethod = new Triangles();
		
//		ArrayList<Long> boundaries = new ArrayList<Long>(Arrays.asList(5L,10L,15L,20L));
//		settings.triangleSettings.theta0.setBinBoundaries(boundaries);
//		settings.triangleSettings.x1.setBinBoundaries(boundaries);
//		settings.triangleSettings.y1.setBinBoundaries(boundaries);
//		settings.triangleSettings.theta1.setBinBoundaries(boundaries);
//		settings.triangleSettings.x2.setBinBoundaries(boundaries);
//		settings.triangleSettings.y2.setBinBoundaries(boundaries);
//		settings.triangleSettings.theta2.setBinBoundaries(boundaries);
		
		settings.triangleSettings.theta0.setBins(5);
		settings.triangleSettings.x1.setBins(8);
		settings.triangleSettings.y1.setBins(9);
		settings.triangleSettings.theta1.setBins(2);
		settings.triangleSettings.x2.setBins(14);
		settings.triangleSettings.y2.setBins(7);
		settings.triangleSettings.theta2.setBins(16);
		
		Triangle t = triangleMethod.new Triangle();

		t.variables.get("theta0").setQuantizedValue(2L);
		t.variables.get("x1").setQuantizedValue(7L);
		t.variables.get("y1").setQuantizedValue(1L);
		t.variables.get("theta1").setQuantizedValue(1L);
		t.variables.get("x2").setQuantizedValue(0L);
		t.variables.get("y2").setQuantizedValue(1L);
		t.variables.get("theta2").setQuantizedValue(10L);
		
		BigInteger expected = BigInteger.valueOf(1513498);
		BigInteger computed = t.toBigInt();
		
		assertTrue("\nExpected: " + expected + "\nComputed: " + computed,
				expected.equals(computed));
	}
	

	@org.junit.Test
	public void testBigIntsAsTriangles() {
		Settings settings = new Settings();
		Triangles triangleMethod = new Triangles();
		
		settings.triangleSettings.theta0.setBins(5);
		settings.triangleSettings.x1.setBins(8);
		settings.triangleSettings.y1.setBins(9);
		settings.triangleSettings.theta1.setBins(2);
		settings.triangleSettings.x2.setBins(14);
		settings.triangleSettings.y2.setBins(7);
		settings.triangleSettings.theta2.setBins(16);
		
		Triangle computed = triangleMethod.new Triangle();
		Triangle expected = triangleMethod.new Triangle();

		expected.variables.get("theta0").setQuantizedValue(2L);
		expected.variables.get("x1").setQuantizedValue(7L);
		expected.variables.get("y1").setQuantizedValue(1L);
		expected.variables.get("theta1").setQuantizedValue(1L);
		expected.variables.get("x2").setQuantizedValue(0L);
		expected.variables.get("y2").setQuantizedValue(1L);
		expected.variables.get("theta2").setQuantizedValue(10L);
		
		BigInteger bigInt = BigInteger.valueOf(1513498);
		computed.fromBigInt(bigInt);
		
		assertTrue("\nExpected: " + expected + "\nComputed: " + computed,
				expected.equals(computed));
	}

	@org.junit.Test
	public void testComputeBinBoundaries() {
		
		MethodVariableSettings var = new MethodVariableSettings();
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
		
		MethodVariableSettings var = new MethodVariableSettings();
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
	public void testStraightHashingCompareTemplates() {
		Hasher straightHasher = new StraightHasher();
		Template enrolledTemplate = new Template();
		Template testTemplate = new Template();
		ArrayList<Template> testTemplates = new ArrayList<Template>();
		testTemplates.add(testTemplate);
		enrolledTemplate.hashes.add(BigInteger.valueOf(5));
		enrolledTemplate.hashes.add(BigInteger.valueOf(102));
		enrolledTemplate.hashes.add(BigInteger.valueOf(567));
		enrolledTemplate.hashes.add(BigInteger.valueOf(9820));
		enrolledTemplate.hashes.add(BigInteger.valueOf(-23));

		testTemplate.hashes.add(BigInteger.valueOf(-23));
		testTemplate.hashes.add(BigInteger.valueOf(89));
		testTemplate.hashes.add(BigInteger.valueOf(157));
		testTemplate.hashes.add(BigInteger.valueOf(9820));
		testTemplate.hashes.add(BigInteger.valueOf(43));
		testTemplate.hashes.add(BigInteger.valueOf(102));
		testTemplate.hashes.add(BigInteger.valueOf(6));
		testTemplate.hashes.add(BigInteger.valueOf(54222));
		
		Double expectedScore = 3.0;
		Double computedScore = straightHasher.compareTemplates(enrolledTemplate, testTemplates);
		assertTrue("\nExpected: " + expectedScore + "\nComputed: " + computedScore,
				Math.abs(expectedScore - computedScore) < 0.0001);
	}
	
	@org.junit.Test
	public void testBinsToBits() {
		Boolean result = true;
		ArrayList<Integer> bins = new ArrayList<Integer>(Arrays.asList(2,3,4,5,6,7,8,9,10,13,15,16,17)); 
		ArrayList<Integer> expectedBits = new ArrayList<Integer>(Arrays.asList(1,2,2,3,3,3,3,4, 4, 4, 4, 4, 5)); 
		ArrayList<Integer> computedBits = new ArrayList<Integer>(); 
		int n=bins.size();
		MethodVariableSettings forTheFunction = new MethodVariableSettings();
		for(int i=0; i<n; i++){
			result = result && forTheFunction.binsToBits(bins.get(i)) == expectedBits.get(i);
			computedBits.add(forTheFunction.binsToBits(bins.get(i)));
		}
		assertTrue("\nexpected: " + expectedBits + "\ncomputed: " + computedBits, result);
	}
	
	@org.junit.Test
	public void translateFingerprint() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6L, 3L, 230L));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(500, 500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(11, 11, 230));
		expected.minutiae.add(new Minutia(64, 38, 25));
		expected.minutiae.add(new Minutia(968, 332, 109));
		expected.minutiae.add(new Minutia(505, 508, 359));
		expected.minutiae.add(new Minutia(5, 8, 0));
		
		Fingerprint computed = test.translate(5L, 8L);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void translateFingerprintNoTranslation() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(500, 500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(6, 3, 230));
		expected.minutiae.add(new Minutia(59, 30, 25));
		expected.minutiae.add(new Minutia(963, 324, 109));
		expected.minutiae.add(new Minutia(500, 500, 359));
		expected.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint computed = test.translate(0L, 0L);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprintDefaultCenter() {
		//FingerprintMethod method = PathsMethod.getInstance();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(-500, -500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(4, 6, 260));
		expected.minutiae.add(new Minutia(36, 55, 55));
		expected.minutiae.add(new Minutia(672, 762, 139));
		expected.minutiae.add(new Minutia(-183, -683, 29));
		expected.minutiae.add(new Minutia(0, 0, 30));
		
		Fingerprint computed = test.rotate(30.0);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprint() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(-500, -500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(151, 57, 330));
		expected.minutiae.add(new Minutia(116, 105, 125));
		expected.minutiae.add(new Minutia(-331, 944, 209));
		expected.minutiae.add(new Minutia(735, -354, 99));
		expected.minutiae.add(new Minutia(155, 52, 100));
		
		Fingerprint computed = test.rotate(100.0, 56L, 91L);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprintNegativeDegrees() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(-500, -500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(-153, 45, 130));
		expected.minutiae.add(new Minutia(-136, -12, 285));
		expected.minutiae.add(new Minutia(-3, -953, 9));
		expected.minutiae.add(new Minutia(-561, 631, 259));
		expected.minutiae.add(new Minutia(-155, 52, 260));
		
		Fingerprint computed = test.rotate(-100.0, -56L, 91L);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprintNoRotation() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(500, 500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(6, 3, 230));
		expected.minutiae.add(new Minutia(59, 30, 25));
		expected.minutiae.add(new Minutia(963, 324, 109));
		expected.minutiae.add(new Minutia(500, 500, 359));
		expected.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint computed = test.rotate(0.0, 50L, 34L);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	


}
