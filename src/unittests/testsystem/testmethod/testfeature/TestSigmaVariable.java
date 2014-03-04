package unittests.testsystem.testmethod.testfeature;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;

import settings.settingsvariables.SettingsMethodVariable;
import system.allcommonclasses.modalities.Minutia;
import system.method.feature.SigmaVariable;
import system.method.feature.Variable;
import system.method.fingerprintmethods.PRINTS;
import system.method.fingerprintmethods.PRINTS.PRINT;




public class TestSigmaVariable {


	private Double computeSigma(Double deltaTheta){
		SigmaVariable sigmaVariable = new SigmaVariable(new SettingsMethodVariable());
		sigmaVariable.setPrequantizedValue(deltaTheta);
		return sigmaVariable.getPrequantizedValue();
	}
	
	
	@org.junit.Test
	public void testSigma0() {
		
		Double theta0 = 0.0;
		Double theta1 = 0.0;
		Double expectedSigma = 0.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma1() {
		
		Double theta0 = 360.0;
		Double theta1 = 5.2;
		Double expectedSigma = 5.2;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,
				
				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma2() {
		
		Double theta0 = 0.1;
		Double theta1 = 0.1;
		Double expectedSigma = 0.1;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma2b() {
		
		Double theta0 = 630.0;
		Double theta1 = 156.0;
		Double expectedSigma = 114.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma3() {
		
		Double theta0 = 156.0;
		Double theta1 = 630.0;
		Double expectedSigma = 114.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma4() {
		
		Double theta0 = 10.0;
		Double theta1 = 359.0;
		Double expectedSigma = 11.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma5() {
		
		Double theta0 = -1.0;
		Double theta1 = 359.0;
		Double expectedSigma = 11.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma6() {
		
		Double theta0 = 735.0;
		Double theta1 = 16.0;
		Double expectedSigma = 1.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma7() {
		
		Double theta0 = 192.0;
		Double theta1 = 12.0;
		Double expectedSigma = 180.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma8() {
		
		Double theta0 = -195.0;
		Double theta1 = -10.0;
		Double expectedSigma = 175.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma9() {
		
		Double theta0 = 36000090.0;
		Double theta1 = -89.0;
		Double expectedSigma = 179.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
	@org.junit.Test
	public void testSigma10() {
		
		Double theta0 = 36000090.0;
		Double theta1 = -91.0;
		Double expectedSigma = 179.0;

		assertTrue(
				"\nExpected: " + expectedSigma + " " + 
				"\nComputed: " + this.computeSigma(theta1 - theta0) + " from: " + theta0 + "," + theta1,

				(this.computeSigma(theta1 - theta0) - expectedSigma) <0.00001
				);
	}
	
	
}
