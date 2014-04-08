package system.coordinator.multiserver;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

import javax.crypto.Cipher;

public class EncryptionScheme {

	public BigInteger encrypt(Key key, BigInteger minutia, Cipher cipher){

		BigInteger encryptedMinutia = null;
		try{
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedMinutia = new BigInteger (cipher.doFinal(minutia.toByteArray()));
			//		    System.out.println("Encrypted!");

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return encryptedMinutia;
	}

	public KeyPair generateKeyPair() throws Exception{

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECIES", "BC");
		System.out.println("Generated key pair");
		EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(
				"fffffffffffffffffffffffffffffffeffffffffffffffff", 16)), new BigInteger(
				"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
				"fffffffffffffffffffffffffffffffefffffffffffffffc", 16));

		ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(new BigInteger(
				"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
				"fffffffffffffffffffffffffffffffefffffffffffffffc", 16)), new BigInteger(
				"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), 1);

		keyGen.initialize(ecSpec, new SecureRandom());

		//KeyAgreement aKeyAgree = KeyAgreement.getInstance("ECDH", "BC");
		KeyPair keyPair = keyGen.generateKeyPair();

		return keyPair;

	}
	
}
