package system.hasher.fuzzyvault.bwdecoding.anotherdecoder;

import java.math.BigInteger;
import java.util.ArrayList;

import system.hasher.fuzzyvault.CRCPolyMap;
import system.hasher.fuzzyvault.FieldSizeMap;
import system.hasher.fuzzyvault.FuzzyVaultPoint;
import system.hasher.fuzzyvault.RSDecoder;
import system.hasher.fuzzyvault.SecretPolynomial;

public class AnotherDecoderWrapper implements RSDecoder{

	public AnotherDecoderWrapper(){
		
	}
	
	@Override
	public SecretPolynomial decode(ArrayList<FuzzyVaultPoint> fuzzyVaultPoints, Integer termsInPoly, BigInteger mod) {
		int totalBits = FieldSizeMap.getBitsFromPrime(mod).intValue();
		int irreduciblePoly = CRCPolyMap.getPolyInteger(totalBits);
		Double fieldSize = Math.pow(2, totalBits);
		
		ReedSolomonDecoder decoder = new ReedSolomonDecoder(new GenericGF(irreduciblePoly, fieldSize.intValue(), 1));
		
		// crap!
		
		return null;
	}

}
