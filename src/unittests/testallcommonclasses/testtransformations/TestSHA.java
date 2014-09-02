package unittests.testallcommonclasses.testtransformations;

import org.junit.Test;
import system.allcommonclasses.transformations.SHA2;
import system.allcommonclasses.transformations.Transformation;

import java.math.BigInteger;

import static org.junit.Assert.assertTrue;

public class TestSHA {

	@Test
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
	
}
