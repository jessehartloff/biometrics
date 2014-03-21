package system.allcommonclasses.encryption;

import java.math.BigInteger;

public abstract class EncryptionScheme {

	public abstract void generateEncryptionKeys();
	
	public abstract void getPublicKey();
	
	public abstract void getPrivateKey(); // it feels weird making this public.

	public abstract BigInteger encrypt(BigInteger message);
	
	public abstract BigInteger decrypt(BigInteger cryptotext);
	
}
