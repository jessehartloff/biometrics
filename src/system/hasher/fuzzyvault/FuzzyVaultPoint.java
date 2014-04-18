package system.hasher.fuzzyvault;

import java.math.BigInteger;

import system.quantizer.Quantizer;

public class FuzzyVaultPoint {

	private BigInteger z; // These should probably be renamed
	private BigInteger gamma; //
	private Boolean chaff; // should be removed later

	public FuzzyVaultPoint(BigInteger bigInt) {
		this.bigIntToPoint(bigInt);
	}

	public FuzzyVaultPoint() {
	}

	/**
	 * Converts a fuzzy vault point to a big int for storage in a template.
	 * 
	 * @param point
	 * @return
	 */
	public BigInteger toBigInt() {

		Long totalBits = Quantizer.getQuantizer().getTotalBits();
		
		BigInteger toReturn = this.z;
		toReturn = toReturn.shiftLeft(totalBits.intValue());
		toReturn = toReturn.add(this.gamma);
		toReturn = toReturn.shiftLeft(1);
		toReturn = toReturn.add(this.chaff ? BigInteger.ONE : BigInteger.ZERO);

		return toReturn;
	}// TODO unit test

	/**
	 * Does what it says. This is where things get a little dangerous since this
	 * assumes that the input represents a fuzzy vault point. Is there a better
	 * solution?
	 * 
	 * @param bigInt
	 * @return
	 */
	public void bigIntToPoint(BigInteger bigInt) {
		BigInteger bigTwo = BigInteger.valueOf(2);

		Long totalBits = Quantizer.getQuantizer().getTotalBits();

		this.chaff = bigInt.and(BigInteger.ONE).equals(BigInteger.ONE) ? true : false;

		BigInteger workingCopy = bigInt.shiftRight(1);
		this.gamma = workingCopy.and(bigTwo.pow(totalBits.intValue()).subtract(BigInteger.valueOf(1)));

		this.z = workingCopy.shiftRight(totalBits.intValue());
	}// TODO unit test

	public BigInteger getZ() {
		return z;
	}

	public void setZ(BigInteger z) {
		this.z = z;
	}

	public BigInteger getGamma() {
		return gamma;
	}

	public void setGamma(BigInteger gamma) {
		this.gamma = gamma;
	}

	public Boolean getChaff() {
		return chaff;
	}

	public void setChaff(Boolean chaff) {
		this.chaff = chaff;
	}

	@Override
	public String toString() {
		return "FuzzyVaultPoint [z=" + z + ", gamma=" + gamma + ", chaff="
				+ chaff + "]";
	}

	
	
}