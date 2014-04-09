package system.coordinator.multiserver;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;

public class EncryptionScheme {

	private Cipher cipher;
	private AlgorithmParameters parameters;
	private KeyPairGenerator keyGen;
	
	public EncryptionScheme(){
		this.initialize();
	}
	
	private void initialize() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			this.cipher = Cipher.getInstance("ECIES","BC");
			this.parameters = this.cipher.getParameters();
			
			X9ECParameters parameters = NISTNamedCurves.getByName("P-192");
			
			this.keyGen = KeyPairGenerator.getInstance("ECIES", "BC");
			EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(
					"fffffffffffffffffffffffffffffffeffffffffffffffff", 16)), new BigInteger(
							"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
									"fffffffffffffffffffffffffffffffefffffffffffffffc", 16));

			ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(new BigInteger(
					"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
							"fffffffffffffffffffffffffffffffefffffffffffffffc", 16)), new BigInteger(
									"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), 1);

			keyGen.initialize(ecSpec, new SecureRandom());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public BigInteger encrypt(Key key, BigInteger minutia){
		BigInteger encryptedMinutia = null;
		try{
			cipher.init(Cipher.ENCRYPT_MODE, key, this.parameters);
			encryptedMinutia = new BigInteger(cipher.doFinal(minutia.toByteArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedMinutia;
	}
	

	public BigInteger decrypt(Key key, BigInteger minutia){
		BigInteger encryptedMinutia = null;
		try{
			this.cipher.init(Cipher.DECRYPT_MODE, key, this.parameters);
			encryptedMinutia = new BigInteger(this.cipher.doFinal(minutia.toByteArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedMinutia;
	}

	public KeyPair generateKeyPair(){
		KeyPair keyPair = keyGen.generateKeyPair();
//		System.out.println(keyPair.getPrivate().toString());
		return keyPair;
	}
	
}
