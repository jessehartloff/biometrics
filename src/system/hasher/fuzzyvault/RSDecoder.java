package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;

public interface RSDecoder {
	
	public SecretPolynomial decode(ArrayList<FuzzyVaultPoint> fuzzyVaultPoints, Integer termsInPoly, BigInteger mod);
	
}
