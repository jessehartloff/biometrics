package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import system.hasher.fuzzyvault.bwdecoding.BWDecoder;
import system.hasher.fuzzyvault.bwdecoding.BigPoly;

public class BerlekampWelchWrapper implements RSDecoder {

	public BerlekampWelchWrapper() {
	}

	@Override
	public SecretPolynomial decode(ArrayList<FuzzyVaultPoint> fuzzyVaultPoints, Integer termsInPoly, BigInteger mod) {
//		ArrayList<BigInteger> zValues = new ArrayList<BigInteger>();
//		ArrayList<BigInteger> gammaValues = new ArrayList<BigInteger>();
//		for (FuzzyVaultPoint vp : fuzzyVaultPoints) {
//			zValues.add(vp.getZ());
//			gammaValues.add(vp.getGamma());
//		}
//		BigInteger[] zValueArray = (BigInteger[]) zValues.toArray();
//		BigInteger[] gammaValueArray = (BigInteger[]) gammaValues.toArray();

		int numberOfPoints = fuzzyVaultPoints.size();
//		if(numberOfPoints == 0)System.out.println("empty vault");
		
		if(numberOfPoints < termsInPoly+1){
			System.out.println("not enough points to try");
			System.out.println("need: " + (termsInPoly+1) + "  have: "+ numberOfPoints);
			return null;
		}
		
		BigInteger[] zValueArray = new BigInteger[numberOfPoints];
		BigInteger[] gammaValueArray = new BigInteger[numberOfPoints];
		
		for(int i=0; i<numberOfPoints; i++){
			zValueArray[i] = fuzzyVaultPoints.get(i).getZ();
			gammaValueArray[i] = fuzzyVaultPoints.get(i).getGamma();
		}
		
		BWDecoder decoder = new BWDecoder(zValueArray, gammaValueArray, termsInPoly, mod);
		decoder.CalcPoly();
		BigPoly polynomial = decoder.getSecretPolynomial();
		return new SecretPolynomial(termsInPoly.longValue()+1, FieldSizeMap.getBitsFromPrime(mod), new ArrayList<BigInteger>(Arrays.asList(polynomial.getCoefficients())));
	}

	
}
