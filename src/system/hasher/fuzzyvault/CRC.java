package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;

public class CRC {
	// store chart of crc polynomials from wikipedia

	static public boolean CheckCRC(ArrayList<BigInteger> poly, ArrayList<BigInteger> CRCpoly) {
		BigInteger remainder;
		remainder = ComputeCRC(poly, CRCpoly);
		return remainder == BigInteger.ZERO;
	}

	public static BigInteger ComputeCRC(ArrayList<BigInteger> poly, ArrayList<BigInteger> CRCpoly) {
		int CRClength = CRCpoly.size();
		int polyLength = poly.size();
		BigInteger dummy = BigInteger.ZERO;
		BigInteger crcTemporary = BigInteger.ZERO;
		for (int i = 0; i < CRClength; i++) {
			dummy = CRCpoly.get(i);
			crcTemporary = crcTemporary.add(dummy.shiftLeft(CRClength - 1 - i));
		}
		dummy = BigInteger.ZERO;
		BigInteger polyInZZForm = BigInteger.ZERO;
		for (int i = 0; i < polyLength; i++) {
			dummy = poly.get(i);
			polyInZZForm = polyInZZForm
					.add(dummy.shiftLeft(polyLength - 1 - i));
		}
		polyInZZForm = polyInZZForm.shiftLeft(CRClength - 1);
		BigInteger bitXOR = polyInZZForm;
		int sum = 1;
		while (bitXOR.compareTo(crcTemporary) > 0) {
			sum = 1;
			BigInteger polyTemp = polyInZZForm;
			while (polyTemp.compareTo(BigInteger.ONE) != 0) {
				polyTemp = polyTemp.shiftRight(1);
				sum++;
			}
			crcTemporary = crcTemporary.shiftLeft(sum - CRClength);
			bitXOR = crcTemporary.xor(polyInZZForm);
			crcTemporary = crcTemporary.shiftRight(sum - CRClength);
			polyInZZForm = bitXOR;
		}
		BigInteger remainder = bitXOR;// may have to do some modulo stuff here
		return remainder;
	}

}