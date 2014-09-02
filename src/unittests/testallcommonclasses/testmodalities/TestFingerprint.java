package unittests.testallcommonclasses.testmodalities;

import org.junit.Test;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Minutia;
import system.method.fingerprintmethods.FingerprintMethod;
import system.method.fingerprintmethods.PathsMethod;

import static org.junit.Assert.assertTrue;

public class TestFingerprint {

	@Test
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
	
	@Test
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
	
	@Test
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
	
	@Test
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
	
	@Test
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
	
	@Test
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
