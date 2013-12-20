package system.allcommonclasses.transformations;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SHA2 extends Transformation {

	@Override
	public BigInteger transform(BigInteger bigInt) {
			BigInteger hashedBigInt;
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
				messageDigest.update(bigInt.toByteArray());

				hashedBigInt = new BigInteger(1,messageDigest.digest());
				
				//System.out.println("Hashed: "+hashedBigInt.toString()+'\n');
				//System.out.println("Original: "+ bigInt.toString()+'\n'+'\n');
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				System.out.println("SHA2.java: Exception thrown when instantiating MessageDigest; NoSuchAlgorithmException");
				return bigInt;
			}
		{}// TODO -Verify SHA2  java.security.MessageDigest
		return hashedBigInt;
	}

}
