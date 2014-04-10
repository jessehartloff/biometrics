package system.coordinator.multiserver;

import java.math.BigInteger;

public class SimpleKeyPair {

	private BigInteger privateKey;
	private BigInteger publicKey;
	
	
	public BigInteger getPrivate() {
		return privateKey;
	}
	public void setPrivateKey(BigInteger privateKey) {
		this.privateKey = privateKey;
	}
	public BigInteger getPublic() {
		return publicKey;
	}
	public void setPublicKey(BigInteger publicKey) {
		this.publicKey = publicKey;
	}
	
}
