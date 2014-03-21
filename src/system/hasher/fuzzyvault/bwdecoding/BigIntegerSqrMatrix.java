package system.hasher.fuzzyvault.bwdecoding;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

/**
 * BigIntegerSqrMatrix class is used for manipulating BigInteger Squere Matrix.
 * provides the following amin features: inversion, turning a matrix to upper
 * diagonal form, solving homogenious equations system based on the matrix.
 */
public class BigIntegerSqrMatrix {

	/**
	 * Testing main function
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		SecureRandom SRNG = SecureRandom.getInstance("SHA1PRNG");
		boolean invertiable = true;
		int dim = 4;

		// init the matrix
		BigInteger[][] A = new BigInteger[dim][dim];
		for (int i = 0; i < dim - 1; i++) {
			// generate random numbers
			for (int j = 0; j < dim; j++) {
				A[i][j] = new BigInteger(5, SRNG);
			}
			// generate dependent vector
			if (!invertiable)
				A[dim - 1] = A[2];
		}

		// generate a matrix from the values
		BigIntegerSqrMatrix mat = new BigIntegerSqrMatrix(A,
				BigInteger.valueOf(7919));
		System.out.println("Matrix:");
		mat.Print();

		// turn the matrix into upper diagonal matrix
		BigIntegerSqrMatrix diagMat = mat.diag();
		System.out.println("Diag Matrix:");
		diagMat.Print();

		// find a private solution
		BigInteger[] privSol = diagMat.solveHLinearEq();

		// check the private solution
		System.out.println("private solution for H linear equitions system");
		for (int j = 0; j < dim; j++) {
			System.out.println(privSol[j]);
		}

		System.out
				.println("test by multiplying the matrix with a private solution vector");
		BigInteger[] tempVec = mat.multiply(privSol);

		for (int j = 0; j < dim; j++) {
			System.out.println(tempVec[j]);
		}

		// check matrix inversion
		if (invertiable) {
			BigIntegerSqrMatrix invMat = mat.invert();
			System.out.println("Inversion of the matrix:");
			invMat.Print();
			System.out.println("Checking Inversion Should be Unity matrix:");
			mat.multiply(invMat).Print();
		}

	}

	/**
	 * Prints the Matrix to System.out
	 */
	public void Print() {
		String res = new String();

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				res = res.concat(values[i][j] + " ");
			}
			res = res.concat("\n");
		}
		System.out.println("The Matrix is:\n" + res + "\n");
	}

	/**
	 * c'tor, creates the matrix instance from a BigInteger 2D - array All
	 * calculations will be performed in Zp where p == modulo
	 * 
	 * @param mat
	 *            a 2D array containing the values of the matrix, should be of
	 *            the same dimensions (nXn)
	 * @param modulo
	 *            the modulo of the field. all calculations will be performed
	 *            with mod(modulo)
	 * @throws MatrixDimensionsException
	 *             if mat dimensions are not equal
	 */
	public BigIntegerSqrMatrix(BigInteger[][] mat, BigInteger modulo) {
		// check dimensions
		if (mat.length == 0 || (mat[0].length != mat.length)) {
			throw new MatrixDimensionsException("Matrix is not square");
		}

		// init the values of the matrix
		values = new BigInteger[mat.length][mat.length];
		copyArray(values, mat, mat.length, mat.length, 0, 0);
		n = mat.length;
		fieldModulo = modulo;
	}

	/**
	 * c'tor for creating a new empty matrix.
	 * 
	 * @param dim
	 *            the dimensions of the matrix
	 * @param modulo
	 *            the modulo of the field on which the calculations are
	 *            performed
	 */
	public BigIntegerSqrMatrix(int dim, BigInteger modulo) {
		values = new BigInteger[dim][dim];
		fieldModulo = modulo;
		n = dim;
	}

	/**
	 * copy c'tor
	 * 
	 * @param from
	 *            the source of the copy
	 */
	public BigIntegerSqrMatrix(BigIntegerSqrMatrix from) {
		values = new BigInteger[from.n][from.n];
		n = from.n;
		copyArray(values, from.values, n, n, 0, 0);
		fieldModulo = from.fieldModulo;
	}

	/**
	 * helper function for copying large areas of 2D BigInteger arrays
	 * 
	 * @param target
	 *            the target of the copy
	 * @param source
	 *            the source of the copy
	 * @param sizeY
	 *            the Y dimensions of the area to copy
	 * @param izeX
	 *            the X dimensions of the area to copy
	 * @param y
	 *            - y dimesion skew between source and destination
	 * @param x
	 *            - x dimesion skew between source and destination
	 */
	private static void copyArray(BigInteger[][] target, BigInteger[][] source,
			int sizeY, int sizeX, int y, int x) {
		for (int i = y; i < sizeY; i++) {
			for (int j = x; j < sizeX; j++) {
				target[i - y][j - x] = source[i][j];
			}
		}
	}

	/**
	 * inverts the matrix using gaussian reduction method
	 * 
	 * @return the inversion matrix B
	 * @throws MatrixNotInvertiableException
	 *             if the matrix is not invertiable
	 */
	public BigIntegerSqrMatrix invert() {
		BigIntegerSqrMatrix res = new BigIntegerSqrMatrix(this);
		BigInteger[][] tempArray = new BigInteger[n][2 * n];

		copyArray(tempArray, this.values, n, n, 0, 0);
		tempArray = invertArray(tempArray, n, fieldModulo);
		copyArray(res.values, tempArray, n, 2 * n, 0, n);
		return res;
	}

	/**
	 * turn the matrix into a upper diagonal matrix using linear actions on the
	 * row vectors
	 * 
	 * @return the upper diagonal matrix created from this
	 */
	public BigIntegerSqrMatrix diag() {
		BigIntegerSqrMatrix res = new BigIntegerSqrMatrix(this);
		diagonalArray(res.values, n, fieldModulo);
		return res;
	}

	/**
	 * helper function for inverting a BigInteger 2D matrix.
	 * 
	 * @return the inverted matrix in the locations [0..n]X[n..2n-1]
	 * @throws MatrixNotInvertiableException
	 *             if the matrix is not invertiable
	 * @param D
	 *            the array to be inverted n the diemnsion to be inverted
	 *            (assuming D is of dimensions nX2n) fieldModulo the modulo of
	 *            the field on which the calculation are performed
	 */
	private static BigInteger[][] invertArray(BigInteger[][] D, int n,
			BigInteger fieldModulo) {
		BigInteger alpha;
		BigInteger beta;
		int i;
		int j;
		int k;
		int error;

		error = 0;
		int n2 = 2 * n;

		// init the reduction matrix
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				D[i][j + n] = BigInteger.ZERO;
			}
			D[i][i + n] = BigInteger.ONE;
		}

		// perform the reductions
		for (i = 0; i < n; i++) {
			alpha = D[i][i];

			if (alpha.equals(BigInteger.ZERO)) /* error - singular matrix */{
				throw new MatrixNotInvertiableException(
						"Singular matrix, cannot invert");
			} else {
				// normalize the vector
				for (j = 0; j < n2; j++) {
					D[i][j] = D[i][j].multiply(alpha.modInverse(fieldModulo))
							.mod(fieldModulo);
				}
				// subtract the vector from all other vectors to zero the
				// relevant matrix elements in the current column
				for (k = 0; k < n; k++) {
					if ((k - i) != 0) {
						beta = D[k][i];
						for (j = 0; j < n2; j++) {
							D[k][j] = D[k][j].subtract(
									beta.multiply(D[i][j]).mod(fieldModulo))
									.mod(fieldModulo);
						}
					}
				}
			}
		}
		return D;
	}

	/**
	 * helper function for turning a 2D array of BigIntegers to an upper
	 * diagonal matrix using linear actions. Assuming array dimensions are nXn.
	 * 
	 * @return BigInteger 2D array containing the upper diagonal matrix
	 * @param D
	 *            2D array to be turned into diagonal form n the dimension of
	 *            the array fieldModulo the modulo of the field used for
	 *            BigInteger calculation
	 */
	private static BigInteger[][] diagonalArray(BigInteger[][] D, int n,
			BigInteger fieldModulo) {
		BigInteger alpha;
		BigInteger beta;
		int i;
		int j;
		int k;

		// perform the reduction
		for (i = 0; i < n; i++) {
			alpha = D[i][i];

			if (alpha.equals(BigInteger.ZERO)) {
				// search for a vector which doesnt have a zero value in the
				// same position
				for (j = i; j < n; j++) {
					if (!D[j][i].equals(BigInteger.ZERO)) {
						break;
					}
				}

				// found a vector swap them
				if (j != n) {
					for (int t = i; t < n; t++) {
						BigInteger temp = D[j][t];
						D[j][t] = D[i][t];
						D[i][t] = temp;
					}
					alpha = D[i][i];
				} else {
					// didnt find a vector - continue
					continue;
				}
			}

			// normalize the current vector
			for (j = 0; j < n; j++) {
				D[i][j] = D[i][j].multiply(alpha.modInverse(fieldModulo)).mod(
						fieldModulo);
			}

			// reduce the vector from the next vectors thus causing the
			// current column to be zero
			for (k = 0; k < n; k++) {
				if ((k - i) != 0) {
					beta = D[k][i];
					for (j = 0; j < n; j++) {
						D[k][j] = D[k][j].subtract(
								beta.multiply(D[i][j]).mod(fieldModulo)).mod(
								fieldModulo);
					}
				}
			}
		}
		return D;
	}

	/**
	 * multiply two matices and return the result.
	 * 
	 * @param mat
	 *            the matrix to be multiplied with this
	 * @return the result of the multiplation
	 */
	public BigIntegerSqrMatrix multiply(BigIntegerSqrMatrix mat) {
		BigIntegerSqrMatrix res = new BigIntegerSqrMatrix(this.n,
				this.fieldModulo);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				res.values[i][j] = BigInteger.ZERO;
				for (int k = 0; k < n; k++)
					res.values[i][j] = res.values[i][j].add(
							this.values[i][k].multiply(mat.values[k][j])).mod(
							fieldModulo);
			}
		}
		return res;
	}

	/**
	 * multiply the matrix with a vector
	 * 
	 * @param vector
	 *            the vector to be multiplied
	 * @return the vector which is the result of the multiplation
	 */
	public BigInteger[] multiply(BigInteger[] vector) {
		if (vector.length != this.n)
			throw new RuntimeException("illegal dimensions for multiply");

		BigInteger[] res = new BigInteger[n];

		for (int i = 0; i < n; i++) {
			res[i] = BigInteger.ZERO;
			for (int j = 0; j < n; j++) {
				res[i] = res[i].add(this.values[i][j].multiply(vector[j])).mod(
						fieldModulo);
			}
		}
		return res;
	}

	/**
	 * solve the linear equation system which is represented by this matrix and
	 * returns a private solution.
	 * 
	 * @return a private solution to the equation system
	 */
	public BigInteger[] solveHLinearEq() {
		BigIntegerSqrMatrix tempMat = this.diag();
		BigInteger[] privateSolution = new BigInteger[n];

		// init the private solution vector and rest of the elements
		// of the matrix which are dependent on others
		for (int i = 0; i < n; i++) {
			if (tempMat.values[i][i].equals(BigInteger.ZERO)) {
				tempMat.values[i][i] = BigInteger.ONE;
				privateSolution[i] = BigInteger.ONE;
			} else {
				privateSolution[i] = BigInteger.ZERO;
			}
		}

		// invert the equations system and multiply with the private
		// solution
		tempMat = tempMat.invert();
		privateSolution = tempMat.multiply(privateSolution);

		/*
		 * for (int i=0; i<privateSolution.length; i++) {
		 * System.out.print(privateSolution[i] + "\n"); }
		 */

		return privateSolution;
	}

	/**
	 * the dimension of the matrix
	 */
	private int n;

	/**
	 * the matrix's elements
	 */
	private BigInteger[][] values;

	/**
	 * the field modulo used for BigInteger calculation
	 */
	private BigInteger fieldModulo;
}

/**
 * MatrixDimensionsException class used for signaling an error in matrix
 * dimensions
 */
class MatrixDimensionsException extends RuntimeException {
	public MatrixDimensionsException(String msg) {
		super(msg);
	}
}

/**
 * MatrixNotInvertiableException class used for signaling that the matrix is not
 * invertiable
 */
class MatrixNotInvertiableException extends RuntimeException {
	public MatrixNotInvertiableException(String msg) {
		super(msg);
	}
}
