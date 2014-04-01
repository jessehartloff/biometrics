package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import system.allcommonclasses.transformations.SHA2;

public class SecretPolynomial {

	private Long termsInPoly;
	private Long totalBits;
	private ArrayList<BigInteger> polynomialTerms;
	private BigInteger modulo;

	public SecretPolynomial(Long termsInPoly, Long totalBits) {
		this.termsInPoly = termsInPoly;
		this.totalBits = totalBits;
		this.generateRandomPolynomial();
		this.modulo = FieldSizeMap.getPrime(totalBits);
	}

	public SecretPolynomial(Long termsInPoly, Long totalBits, ArrayList<BigInteger> polynomialTerms) {
		this.termsInPoly = termsInPoly;
		this.totalBits = totalBits;
		this.polynomialTerms = polynomialTerms;
		this.modulo = FieldSizeMap.getPrime(totalBits);
	}

	private void generateRandomPolynomial() {
		this.polynomialTerms = new ArrayList<BigInteger>();
		for (int i = 0; i < this.termsInPoly-1; i++) {
			BigInteger randomValue = new BigInteger(this.totalBits.intValue(), new Random());
			this.polynomialTerms.add(randomValue);
		}
//		CRCPolynomial crcPoly = new CRCPolynomial(); //createIrreducbile creates a random polynomial every call -- now hard coded into CRCPolyMap
//		crcPoly = CRCPolynomial.createIrreducible(this.termsInPoly.intValue());
//		this.polynomialTerms.add(CRC.ComputeCRC(polynomialTerms, crcPoly.toArrayList()));
//		System.out.println("Computing CRC for the secret polynomial");
//		System.out.println(CRCPolyMap.getCRCPoly(this.termsInPoly.intValue()));
//		System.out.println(CRC.ComputeCRC(polynomialTerms, CRCPolyMap.getCRCPoly(this.termsInPoly.intValue())));
		this.polynomialTerms.add(CRC.ComputeCRC(polynomialTerms, CRCPolyMap.getCRCPoly(this.totalBits.intValue())));
	}

	public BigInteger evaluateAt(BigInteger bigInt) {
		BigInteger evaluatedInteger = BigInteger.valueOf(0);
//		BigInteger fieldSize = BigInteger.valueOf(2).pow(this.totalBits.intValue());
		for (int i = 0; i < this.polynomialTerms.size(); i++) {
			BigInteger currentTerm = (bigInt.pow(i)).multiply(this.polynomialTerms.get(i)).mod(modulo);
//			BigInteger currentTerm = (bigInt.pow(this.polynomialTerms.size()-1-i)).multiply(this.polynomialTerms.get(i)).mod(modulo);
			evaluatedInteger = evaluatedInteger.add(currentTerm);
		}
		return evaluatedInteger.mod(modulo);
	}

	public Long getTermsInPoly() {
		return termsInPoly;
	}

	public void setTermsInPoly(Long termsInPoly) {
		this.termsInPoly = termsInPoly;
	}
	
	public ArrayList<BigInteger> getPolynomialTerms() {
		return polynomialTerms;
	}

	public void setPolynomialTerms(ArrayList<BigInteger> polynomialTerms) {
		this.polynomialTerms = polynomialTerms;
	}

	@Override
	public String toString() {
		return "SecretPolynomial [termsInPoly=" + termsInPoly + ", totalBits="
				+ totalBits + ", polynomialTerms=" + polynomialTerms + "]";
	}

	@Override
	public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof SecretPolynomial))
            return false;

        SecretPolynomial other = (SecretPolynomial) obj;
      
        if(this.polynomialTerms.size() != other.polynomialTerms.size()){
        	return false;
        }
        
        boolean result = true;
        for(int i=0; i<this.polynomialTerms.size(); i++){
        	result &= this.polynomialTerms.get(i).equals(other.polynomialTerms.get(i));
        }
        return result;
    }

	public BigInteger computeHash(){
		BigInteger allTerms = BigInteger.ZERO;
		
		for(BigInteger term : this.polynomialTerms){
			allTerms = allTerms.shiftLeft(this.totalBits.intValue());
			allTerms = allTerms.add(term);
		}
		
		SHA2 sha = new SHA2();
		return sha.transform(allTerms);
	}
	
}
