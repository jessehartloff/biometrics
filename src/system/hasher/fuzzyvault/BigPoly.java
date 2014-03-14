package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * BigPoly class represents a polynomial over BigIntegers
 */
public class BigPoly {

	/**
	 * the coefficients of the polynomial
	 */
	private BigInteger coefficients[] = null;

	/**
	 * the modulo of the field used for BigInteger calculations
	 */
	private BigInteger modulos;

	/**
	 * c'tor for creating a poly using the coefficients list
	 * @param _coefficients 1D array containing the coefficients of the poly
	 * @param _modulos the field modulo
	 */
	public BigPoly(BigInteger _coefficients[], BigInteger _modulos) {
		int degree;

		modulos = _modulos;
		// Reduce the polynomial to its actual degree
		for (degree = _coefficients.length - 1;
			degree > 0 && _coefficients[degree].equals(BigInteger.ZERO);
			degree--);
			
		coefficients = new BigInteger[degree + 1];
		System.arraycopy(_coefficients, 0, coefficients, 0, degree + 1);
	}

	/**
	 * c'tor for creating a BigPoly based on random coefficients.
	 * @param degree the degree of the poly to be created
	 * @param bitLength the length of the coefficients in bits
	 * @param SRNG the source for the random coefficients
	 * @param _modulos the modulo of the field to be used in BigInteger calculations
	 */
	public BigPoly(
		int degree,
		int bitLength,
		SecureRandom SRNG,
		BigInteger _modulos) {

		int idx;

		modulos = _modulos;
		coefficients = new BigInteger[degree + 1];
		for (idx = 0; idx <= degree; idx++) {
			coefficients[idx] = new BigInteger(bitLength, SRNG);
			// The last coefficient should not be zero, to ensure that the degree is correct
			if (idx == degree)
				while (coefficients[idx].compareTo(BigInteger.ONE) < 0
					|| coefficients[idx].compareTo(modulos) >= 0)
					coefficients[idx] = new BigInteger(bitLength, SRNG);
			else
				while (coefficients[idx].compareTo(modulos) >= 0)
					coefficients[idx] = new BigInteger(bitLength, SRNG);
		}
	}

	/**
	 * c'tor for creating a BigPoly based on random coefficients and a 
	 * given first term.
	 * @param firstTerm the first term to be used
	 * @param degree the degree of the poly to be created
	 * @param bitLength the length of the coefficients in bits
	 * @param SRNG the source for the random coefficients
	 * @param _modulos the modulo of the field to be used in BigInteger calculations
	 */
	public BigPoly(
		BigInteger firstTerm,
		int degree,
		int bitLength,
		SecureRandom SRNG,
		BigInteger _modulos) {

		int idx;

		modulos = _modulos;
		coefficients = new BigInteger[degree + 1];
		coefficients[0] = firstTerm;
		for (idx = 1; idx <= degree; idx++) {
			coefficients[idx] = new BigInteger(bitLength, SRNG);
			// The last coefficient should not be zero, to ensure that the degree is correct
			if (idx == degree)
				while (coefficients[idx].compareTo(BigInteger.ONE) < 0
					|| coefficients[idx].compareTo(modulos) >= 0)
					coefficients[idx] = new BigInteger(bitLength, SRNG);
			else
				while (coefficients[idx].compareTo(modulos) >= 0)
					coefficients[idx] = new BigInteger(bitLength, SRNG);
		}
	}

	/**
	 * devide this poly with the divisor
	 * @param the divisor poly
	 * @return the result of the division
	 * @throws ArithmeticException if the divisor is the zero poly
	 */
	public BigPoly divide(BigPoly divisor) {
		BigInteger curCoeffs[], resCoeffs[], divCoeffs[];
		BigInteger mult;
		int ourDegree, divDegree;
		int idx;

		divCoeffs = divisor.coefficients;
		ourDegree = coefficients.length - 1;
		divDegree = divCoeffs.length - 1;
		// Check if the division is allowed
		if (divCoeffs[divDegree].equals(BigInteger.ZERO))
			throw new ArithmeticException("Division by zero");
		// Check if we can divide the polynomial at least once
		if (ourDegree < divDegree
			|| coefficients[ourDegree].compareTo(divCoeffs[divDegree]) < 0) {
			// If it can't be divided, return zero
			resCoeffs = new BigInteger[1];
			resCoeffs[0] = BigInteger.ZERO;
			return new BigPoly(resCoeffs, modulos);
		}
		// Divide the polynomial
		curCoeffs = (BigInteger[]) coefficients.clone();
		resCoeffs = new BigInteger[ourDegree - divDegree + 1];
		while (ourDegree > divDegree
			|| (ourDegree == divDegree
				&& curCoeffs[ourDegree].compareTo(divCoeffs[divDegree]) >= 0)) {
			mult =
				curCoeffs[ourDegree].multiply(
					divCoeffs[divDegree].modInverse(modulos).mod(modulos));
			resCoeffs[ourDegree - divDegree] = mult;
			for (idx = 0; idx <= divDegree; idx++)
				curCoeffs[ourDegree - idx] =
					curCoeffs[ourDegree
						- idx].subtract(
							divCoeffs[divDegree - idx].multiply(mult)).mod(
							modulos);
			// Look for our next chance to divide the polynomial
			for (ourDegree--;
				ourDegree >= divDegree
					&& curCoeffs[ourDegree].equals(BigInteger.ZERO);
				ourDegree--)
				resCoeffs[ourDegree - divDegree] = BigInteger.ZERO;
		}
		return new BigPoly(resCoeffs, modulos);
	}

	/**
	 * returns the Y coord which is the result of assigning XCoord into
	 * the poly
	 * @param XCoord the value of X to be assigned into the poly
	 * @return the Y value which is the result of assigning X into the 
	 * poly 
	 */
	public BigInteger getYCoord(BigInteger XCoord) {

		int idx;
		BigInteger result;

		if (coefficients == null)
			return null;
		result = BigInteger.ZERO;
		for (idx = 0; idx < coefficients.length; idx++)
			result =
				result
					.add(
						coefficients[idx].multiply(
							XCoord.modPow(
								new BigInteger(String.valueOf(idx)),
								modulos)))
					.mod(modulos);
		return result;
	}

	/**
	 * returns the Y coord which is the result of assigning XCoord into
	 * the poly
	 * @param XCoord the value of X to be assigned into the poly
	 * @return the Y value which is the result of assigning X into the 
	 * poly 
	 */
	public BigInteger getYCoord(long XCoord) {
		return getYCoord(BigInteger.valueOf(XCoord));
	}

	/**
	 * returns the idx's coefficient of the poly
	 * @param idx the index of the required coefficient
	 * @return the value of the coefficient which index is idx
	 */
	public BigInteger getCoeff(int idx) {
		return coefficients[idx];
	}

	/**
	 * returns the degree of the poly
	 * @return the degree of the poly
	 */
	public int getDegree() {
		return coefficients.length - 1;
	}

	/**
	 * returns a string representation of the poly
	 * @return a string representation of the poly
	 */
	public String toString() {

		int idx;
		String result = new String();

		for (idx = getDegree(); idx > 0; idx--)
			if (idx == 1)
				result =result.concat(coefficients[idx].toString() + "x + ");
			else
				result =
					result.concat(
						coefficients[idx].toString() + "x^" + idx + " + ");
		result = result.concat(coefficients[0].toString());
		return result;
	}
}