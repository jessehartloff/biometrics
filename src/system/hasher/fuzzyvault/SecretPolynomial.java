package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class SecretPolynomial {

	private Long termsInPoly;
	private Long totalBits;
	private ArrayList<BigInteger> polynomialTerms;

	public SecretPolynomial(Long termsInPoly, Long totalBits){
		this.termsInPoly = termsInPoly;
		this.totalBits = totalBits;
		this.generateRandomPolynomial();
	}
	
	public SecretPolynomial(Long termsInPoly, Long totalBits, ArrayList<BigInteger> polynomialTerms){
		this.termsInPoly = termsInPoly;
		this.totalBits = totalBits;
		this.polynomialTerms = polynomialTerms;
	}

	private void generateRandomPolynomial() {
		for(int i=0; i<this.termsInPoly; i++){
			BigInteger randomValue = new BigInteger(this.totalBits.intValue(), new Random());
		}
		// TODO Auto-generated method stub
		// add CRC
	}

	public BigInteger evaluateAt(BigInteger bigInt) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long getTermsInPoly() {
		return termsInPoly;
	}

	public void setTermsInPoly(Long termsInPoly) {
		this.termsInPoly = termsInPoly;
	}

	// TODO FuzzyVault - polynomial stuff
	
	
	
}
