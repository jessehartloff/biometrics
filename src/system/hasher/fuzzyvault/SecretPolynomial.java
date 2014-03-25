package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class SecretPolynomial {

	private Long termsInPoly;
	private Long totalBits;
	private ArrayList<BigInteger> polynomialTerms;

	public SecretPolynomial(Long termsInPoly, Long totalBits) {
		this.termsInPoly = termsInPoly;
		this.totalBits = totalBits;
		this.generateRandomPolynomial();
	}

	public SecretPolynomial(Long termsInPoly, Long totalBits, ArrayList<BigInteger> polynomialTerms) {
		this.termsInPoly = termsInPoly;
		this.totalBits = totalBits;
		this.polynomialTerms = polynomialTerms;
	}

	private void generateRandomPolynomial() {
		this.polynomialTerms = new ArrayList<BigInteger>();
		for (int i = 0; i < this.termsInPoly; i++) {
			BigInteger randomValue = new BigInteger(this.totalBits.intValue(), new Random());
			this.polynomialTerms.add(randomValue);
		}
		// TODO CRC
		// remove last term and add CRC
	}

	public BigInteger evaluateAt(BigInteger bigInt) {
		BigInteger evaluatedInteger = BigInteger.valueOf(0);
		for (int i = 0; i < this.polynomialTerms.size(); i++) {
			BigInteger currentTerm = (bigInt.pow(i)).multiply(this.polynomialTerms.get(i));
			evaluatedInteger = evaluatedInteger.add(currentTerm);
		}
		return evaluatedInteger;
	}

	public Long getTermsInPoly() {
		return termsInPoly;
	}

	public void setTermsInPoly(Long termsInPoly) {
		this.termsInPoly = termsInPoly;
	}

}
