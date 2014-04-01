package unittests.testsystem.testhasher;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import system.hasher.fuzzyvault.BerlekampWelchWrapper;
import system.hasher.fuzzyvault.CRC;
import system.hasher.fuzzyvault.CRCPolyMap;
import system.hasher.fuzzyvault.FieldSizeMap;
import system.hasher.fuzzyvault.FuzzyVaultPoint;
import system.hasher.fuzzyvault.SecretPolynomial;
import system.hasher.fuzzyvault.Vault;

public class TestFuzzyVault {


	@Test
	public void testCRC() {
//		SecretPolynomial polynomial = new SecretPolynomial(10L, 21L);
		int termsInPoly = 21;
		int bits = 10;
		ArrayList<BigInteger> terms = new ArrayList<BigInteger>();
		for (int i = 0; i < termsInPoly; i++) {
			BigInteger randomValue = new BigInteger(bits, new Random());
			terms.add(randomValue);
		}
		BigInteger crc = CRC.ComputeCRC(terms, CRCPolyMap.getCRCPoly(bits));
		terms.add(crc);
		assertTrue("crc: " + crc + "\npoly: " +  terms, 
				CRC.CheckCRC(terms, CRCPolyMap.getCRCPoly(bits)));
	}


	@Test
	public void testCRCSimple() {
		SecretPolynomial polynomial = new SecretPolynomial(10L, 21L);
		assertTrue("CRC failed on new polynomial", 
				CRC.CheckCRC(polynomial.getPolynomialTerms(), CRCPolyMap.getCRCPoly(10)));
	}
	
	
	@Test
	public void testBWDecoder() {
		
		ArrayList<FuzzyVaultPoint> vaultPoints = new ArrayList<FuzzyVaultPoint>();
		BerlekampWelchWrapper decoder = new BerlekampWelchWrapper();
		Long termsInPoly = 10L;
		Long bits = 21L;
		BigInteger fieldSize = FieldSizeMap.getPrime(new Long(bits));
		
		SecretPolynomial polynomial = new SecretPolynomial(termsInPoly, bits);
		
		ArrayList<BigInteger> values = new ArrayList<BigInteger>();
		values.add(BigInteger.valueOf(5));
		values.add(BigInteger.valueOf(15));
		values.add(BigInteger.valueOf(654));
		values.add(BigInteger.valueOf(912347));
		values.add(BigInteger.valueOf(2000000));
		values.add(BigInteger.valueOf(67345));
		values.add(BigInteger.valueOf(6345));
		values.add(BigInteger.valueOf(132469));
		values.add(BigInteger.valueOf(487456));
		values.add(BigInteger.valueOf(745895));
		values.add(BigInteger.valueOf(36543));
		values.add(BigInteger.valueOf(2223564));
		values.add(BigInteger.valueOf(62234));
		
		for(BigInteger bigInt : values){
			FuzzyVaultPoint point = new FuzzyVaultPoint();
			point.setZ(bigInt);
			point.setGamma(polynomial.evaluateAt(bigInt));
			vaultPoints.add(point);
		}
		
		System.out.println("vp: " + vaultPoints);
		
		SecretPolynomial result = decoder.decode(vaultPoints, termsInPoly.intValue()-1, fieldSize);
		
		assertTrue("\nsecret: " + polynomial + "\ndecoded: " +  result, 
				polynomial.equals(result));
	}
	
	
}
