package unittests;

import static org.junit.Assert.*;
import system.allcommonclasses.modalities.*;
import system.makefeaturevector.fingerprintmethods.*;

public class SystemTests {

	{}// TODO tons of unit tests
	
	
	@org.junit.Test
	public void translateFingerprint() {
		FingerprintMethod method = Paths.getInstance();
		
		Fingerprint test = new Fingerprint(method);
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(500, 500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint(method);
		expected.minutiae.add(new Minutia(11, 11, 230));
		expected.minutiae.add(new Minutia(64, 38, 25));
		expected.minutiae.add(new Minutia(968, 332, 109));
		expected.minutiae.add(new Minutia(505, 508, 359));
		expected.minutiae.add(new Minutia(5, 8, 0));
		
		Fingerprint computed = new Fingerprint(method);
		test.translate(computed, 5, 8);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void translateFingerprintNoTranslation() {
		FingerprintMethod method = Paths.getInstance();
		
		Fingerprint test = new Fingerprint(method);
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(500, 500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint(method);
		expected.minutiae.add(new Minutia(6, 3, 230));
		expected.minutiae.add(new Minutia(59, 30, 25));
		expected.minutiae.add(new Minutia(963, 324, 109));
		expected.minutiae.add(new Minutia(500, 500, 359));
		expected.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint computed = new Fingerprint(method);
		test.translate(computed, 0, 0);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprintDefaultCenter() {
		FingerprintMethod method = Paths.getInstance();
		
		Fingerprint test = new Fingerprint(method);
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(-500, -500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint(method);
		expected.minutiae.add(new Minutia(4, 6, 260));
		expected.minutiae.add(new Minutia(36, 55, 55));
		expected.minutiae.add(new Minutia(672, 762, 139));
		expected.minutiae.add(new Minutia(-183, -683, 29));
		expected.minutiae.add(new Minutia(0, 0, 30));
		
		Fingerprint computed = new Fingerprint(method);
		test.rotate(computed, 30.0);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprint() {
		FingerprintMethod method = Paths.getInstance();
		
		Fingerprint test = new Fingerprint(method);
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(-500, -500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint(method);
		expected.minutiae.add(new Minutia(151, 57, 330));
		expected.minutiae.add(new Minutia(116, 105, 125));
		expected.minutiae.add(new Minutia(-331, 944, 209));
		expected.minutiae.add(new Minutia(735, -354, 99));
		expected.minutiae.add(new Minutia(155, 52, 100));
		
		Fingerprint computed = new Fingerprint(method);
		test.rotate(computed, 100.0, 56, 91);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprintNegativeDegrees() {
		FingerprintMethod method = Paths.getInstance();
		
		Fingerprint test = new Fingerprint(method);
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(-500, -500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint(method);
		expected.minutiae.add(new Minutia(-153, 45, 130));
		expected.minutiae.add(new Minutia(-136, -12, 285));
		expected.minutiae.add(new Minutia(-3, -953, 9));
		expected.minutiae.add(new Minutia(-561, 631, 259));
		expected.minutiae.add(new Minutia(-155, 52, 260));
		
		Fingerprint computed = new Fingerprint(method);
		test.rotate(computed, -100.0, -56, 91);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprintNoRotation() {
		FingerprintMethod method = Paths.getInstance();
		
		Fingerprint test = new Fingerprint(method);
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(500, 500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint(method);
		expected.minutiae.add(new Minutia(6, 3, 230));
		expected.minutiae.add(new Minutia(59, 30, 25));
		expected.minutiae.add(new Minutia(963, 324, 109));
		expected.minutiae.add(new Minutia(500, 500, 359));
		expected.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint computed = new Fingerprint(method);
		test.rotate(computed, 0.0, 50, 34);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	{}// TODO test Singleton creation
	{}// TODO test Singleton errors for wrong class

}
