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
		for (int i = 0; i < this.termsInPoly-1; i++) {
			BigInteger randomValue = new BigInteger(this.totalBits.intValue(), new Random());
			this.polynomialTerms.add(randomValue);
		}
		Polynomial crcPoly = new Polynomial();
		crcPoly = Polynomial.createIrreducible(this.termsInPoly.intValue());
		this.polynomialTerms.add(CRC.ComputeCRC(polynomialTerms, crcPoly.toArrayList()));
	}

	public BigInteger evaluateAt(BigInteger bigInt) {
		BigInteger evaluatedInteger = BigInteger.valueOf(0);
		BigInteger fieldSize = BigInteger.valueOf(2).pow(this.totalBits.intValue());
		for (int i = 0; i < this.polynomialTerms.size(); i++) {
			BigInteger currentTerm = (bigInt.pow(this.polynomialTerms.size()-1-i)).multiply(this.polynomialTerms.get(i)).mod(fieldSize);
			evaluatedInteger = evaluatedInteger.add(currentTerm);
		}
		return evaluatedInteger.mod(fieldSize);
	}

	public Long getTermsInPoly() {
		return termsInPoly;
	}

	public void setTermsInPoly(Long termsInPoly) {
		this.termsInPoly = termsInPoly;
	}

}
