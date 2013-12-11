package unittests;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.*;
import system.allcommonclasses.settings.GlobalSettings;
import system.allcommonclasses.settings.MethodVariable;
import system.allcommonclasses.settings.Settings;
import system.allcommonclasses.utilities.Functions;
import system.hasher.Hasher;
import system.hasher.StraightHasher;
import system.makefeaturevector.fingerprintmethods.*;
import system.makefeaturevector.fingerprintmethods.Triangles.Triangle;

public class SystemTests {

	{}// TODO tons of unit tests

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
		
		Triangle t = triangleMethod.new Triangle(settings.triangleSettings);

		t.theta0 = 2L;
		t.x1 = 7L;
		t.y1 = 1L;
		t.theta1 = 1L;
		t.x2 = 0L;
		t.y2 = 1L;
		t.theta2 = 10L;
		
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
		
		Triangle computed = triangleMethod.new Triangle(settings.triangleSettings);
		Triangle expected = triangleMethod.new Triangle(settings.triangleSettings);
		
		expected.theta0 = 2L;
		expected.x1 = 7L;
		expected.y1 = 1L;
		expected.theta1 = 1L;
		expected.x2 = 0L;
		expected.y2 = 1L;
		expected.theta2 = 10L;
		
		BigInteger bigInt = BigInteger.valueOf(1513498);
		computed.fromBigInt(bigInt);
		
		assertTrue("\nExpected: " + expected + "\nComputed: " + computed,
				expected.equals(computed));
	}

	@org.junit.Test
	public void testComputeBinBoundaries() {
		
		MethodVariable var = new MethodVariable();
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
		
		MethodVariable var = new MethodVariable();
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
		ArrayList<Integer> bits = new ArrayList<Integer>(Arrays.asList(1,2,2,3,3,3,3,4, 4, 4, 4, 4, 5)); 
		ArrayList<Integer> computedBits = new ArrayList<Integer>(); 
		int n=bins.size();
		for(int i=0; i<n; i++){
			result = result && Functions.binsToBits(bins.get(i)) == bits.get(i);
			computedBits.add(Functions.binsToBits(bins.get(i)));
		}
		assertTrue("\nexpected: " + bits + "\ncomputed: " + computedBits, result);
	}
	
	@org.junit.Test
	public void translateFingerprint() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
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
		
		Fingerprint computed = test.translate(5, 8);
		
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
		
		Fingerprint computed = test.translate(0, 0);
		
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
		
		Fingerprint computed = test.rotate(100.0, 56, 91);
		
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
		
		Fingerprint computed = test.rotate(-100.0, -56, 91);
		
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
		
		Fingerprint computed = test.rotate(0.0, 50, 34);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	


}
