package system.hasher.fuzzyvault.bwdecoding;

import java.math.BigInteger;

/**
 * BWDecoder class is an implementation of the Berlekamp Welch Decoder 
 * using matrix inversion process. working efficiency is O(n^3)
 */
public class BWDecoder {
	/**
	 * the vector containing the received values
	 */
	private BigInteger[] r;
	
	/**
	 * the vector containing the x values assigned into the poly
	 */
	private BigInteger[] alpha;
	
	/**
	 * the degree of the reconstructed poly
	 */
	private int k;
	
	/**
	 * the maximum number of errors
	 */
	private int e;
	
	/**
	 * the number of received values
	 */
	private int n;
	
	/**
	 * the modulo of the field used for BigInteger calculations
	 */
	private BigInteger fieldModulo;
	
	/**
	 * the reconstructed poly 
	 */
	private BigPoly N;
	
	/**
	 * the error detection poly
	 */
	private BigPoly E;
	
	/**
	 * the result of the division of N poly with E poly
	 */
	private BigPoly N_div_E;
	
		
	public BigPoly getSecretPolynomial() {
		return N_div_E;
	}

	/**
	 * Constructor for BWDecoder
	 * @param alphaArray a 1D array of BigIntegers containing the alpha values
	 * @param rArray a 1D array of BigIntegers containing the received values
	 * @param _k the degree of the poly we are reconstructing
	 * @param _fieldModulo the modulo of the field
	 */
	public BWDecoder(BigInteger[] alphaArray, BigInteger[] rArray, int _k,BigInteger _fieldModulo) {
		r = rArray;
		alpha = alphaArray;
		k = _k;
		fieldModulo = _fieldModulo;
	}

	/**
	 * interpulated the reconstructed poly and finds the value of the 
	 * poly in point.
	 * @param point the point in which the value should be interpulated
	 * @throws
	 * 	BWDecoderException if the poly cannot be reconstructed
	 */
	public BigInteger interpolate(BigInteger point) {
		// reconstruct the poly if it was not done before
		if (N_div_E == null) {
			CalcPoly();
		}
		// find the value of the poly at the given point
		return N_div_E.getYCoord(point);
	}
	
	/**
	 * reconstructs the poly from the given parameters
	 * @throws
	 * 	BWDecoderException if the poly cannot be reconstructed
	 */
	public void CalcPoly() {
		// check that the the number of points given is compatiable
		if (alpha.length != r.length) {
			throw new BWDecoderException("BW Decoder r and alpha must be of the same dimentions");
		}
		
		// set n
		n = alpha.length;
	
		// we require the following equation:
		// 2e + k + 1 = n ==> e = (n - k - 1)/ 2 or
		// 2e + k + 2 = n ==> e = (n - k) / 2
		e = (n - k - 1) / 2 + (n - k - 1) % 2;
		int dim = 2*e+k+2;

		// generate the linear homogeneous equations system				
		BigInteger[][] eqSys = new BigInteger[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (n <= i) {
					// if we need to add an equation for E(e) then add a row of zeros 
					eqSys[i][j] = BigInteger.ZERO;
				} else if (j <= e + k) {
					// (alpha_i)^j
					eqSys[i][j] = (alpha[i].pow(j)).mod(fieldModulo);
				} else {
					// -(r_i)*(alpha_i)^j
					eqSys[i][j] = (r[i].multiply(alpha[i].pow(j-e-k-1))).negate().mod(fieldModulo);
				}
			}
		}
		
		// generate a matrix from the equation system		
		BigIntegerSqrMatrix mat = new BigIntegerSqrMatrix(eqSys,fieldModulo);
		
		// generate coefficients vectors for the polys N and E
		BigInteger[] solutionNE = mat.solveHLinearEq();
		BigInteger[] arrayE = new BigInteger[e+1];
		BigInteger[] arrayN = new BigInteger[e+k+1];
				
		System.arraycopy(solutionNE,0,arrayN,0,k+e+1);
		System.arraycopy(solutionNE,e+k+1,arrayE,0,e+1);
		
		// generate polys from the coefficients
		E = new BigPoly(arrayE,fieldModulo);
		N = new BigPoly(arrayN,fieldModulo);
		
		// print polys
//		System.out.println("E = "+E);
// 		System.out.println("N = "+N);		
		
		try {
			N_div_E = N.divide(E);
//			System.out.println("N_div_E = "+N_div_E);
		} catch (ArithmeticException aexp) {
			throw new BWDecoderException("Cannot reconstruct the polynomial");
		}
	}	

	/**
	 * main testing function for the unit
	 */
	public static void main(String[] args) {
		// set the vectors for r and alpha		
		BigInteger[] r = {BigInteger.valueOf(3),
							BigInteger.valueOf(7),
							BigInteger.valueOf(13),
							BigInteger.valueOf(21),
							BigInteger.valueOf(311)
//							BigInteger.valueOf(39),
//							BigInteger.valueOf(52)
						};
		BigInteger[] alpha = {BigInteger.valueOf(1),
								BigInteger.valueOf(2),
								BigInteger.valueOf(3),
								BigInteger.valueOf(4),
								BigInteger.valueOf(5)
//								BigInteger.valueOf(6),
//								BigInteger.valueOf(7)
								};
		
		// interpulate
		BWDecoder bwDecode = new BWDecoder(alpha,r,2,BigInteger.valueOf(62537));
		System.out.println("Interpulation to 10 is "+bwDecode.interpolate(BigInteger.valueOf(10)));
	}
}

/**
 * Exception for BWDecoder class. this is used for signaling an error
 * which cause the poly to be unreconstructable
 */
class BWDecoderException extends RuntimeException {
	public BWDecoderException(String msg) {
		super(msg);	
	}		
}