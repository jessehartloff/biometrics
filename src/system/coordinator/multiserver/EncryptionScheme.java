package system.coordinator.multiserver;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;

public class EncryptionScheme {

	private Cipher cipher;
	private AlgorithmParameters parameters;
	private KeyPairGenerator keyGen;
	private BigInteger prime;
	
	public EncryptionScheme(){
		this.initialize();
	}
	
	public EncryptionScheme(BigInteger prime) {
		this.prime = prime;
	}
	
	private void initialize() {
		this.prime = BigInteger.probablePrime(200, new Random());
	                            
//		this .prime = new BigInteger("6277101735386680763835789423207666416083908700390324961279"); //p
//		this .prime = new BigInteger("6277101735386680763835789423176059013767194773182842284081"); //r.. maybe q
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//		try {
////			this.cipher = Cipher.getInstance("DHIES","BC");
//			this.cipher = Cipher.getInstance("ECIES","BC");
//			
////			 Provider[] provs = Security.getProviders();
////			 for(Provider p : provs){
////				 System.out.println(p);
////				 System.out.println(p.getServices());
////			 }
//			
//			this.parameters = this.cipher.getParameters();
////			
//			X9ECParameters parameters = NISTNamedCurves.getByName("P-192");
//////			parameters.
////			
//			
//
////			this.keyGen = KeyPairGenerator.getInstance("DH", "BC");
//			this.keyGen = KeyPairGenerator.getInstance("ECIES", "BC");
//			this.prime = new BigInteger("fffffffffffffffffffffffffffffffeffffffffffffffff", 16);
//			
//			EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(
//					"fffffffffffffffffffffffffffffffeffffffffffffffff", 16)), new BigInteger(
//							"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
//									"fffffffffffffffffffffffffffffffefffffffffffffffc", 16));
//
//			ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(new BigInteger(
//					"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
//							"fffffffffffffffffffffffffffffffefffffffffffffffc", 16)), new BigInteger(
//									"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), 1);
//
//			keyGen.initialize(ecSpec, new SecureRandom());
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	
	public BigInteger encrypt(BigInteger key, BigInteger minutia){
		return minutia.modPow(key, this.prime);
//		BigInteger encryptedMinutia = null;
//		try{
//			cipher.init(Cipher.ENCRYPT_MODE, key, this.parameters);
//			encryptedMinutia = new BigInteger(cipher.doFinal(minutia.toByteArray()));
////			encryptedMinutia = new BigInteger(new String(this.cipher.doFinal(minutia.toString().getBytes("ISO-8859-1")), "ISO-8859-1"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return encryptedMinutia;
	}
	

	public BigInteger decrypt(BigInteger key, BigInteger minutia){
		return minutia.modPow(key, this.prime);
//		BigInteger encryptedMinutia = null;
//		try{
//			this.cipher.init(Cipher.DECRYPT_MODE, key, this.parameters);
//			encryptedMinutia = new BigInteger(this.cipher.doFinal(minutia.toByteArray()));
////			encryptedMinutia = new BigInteger(new String(this.cipher.doFinal(minutia.toString().getBytes("ISO-8859-1")), "ISO-8859-1"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return encryptedMinutia;
	}

	public SimpleKeyPair generateKeyPair(){
		SimpleKeyPair keys = new SimpleKeyPair();
		while(true){
			keys.setPrivateKey(new BigInteger(190, new SecureRandom()));
//			System.out.println(keys.getPrivate().gcd(this.prime.add(BigInteger.valueOf(-1L))));
			if(keys.getPrivate().gcd(this.prime.add(BigInteger.valueOf(-1L))).equals(BigInteger.ONE)){
				break;
			}
//			break;
		}
		keys.setPublicKey(keys.getPrivate().modInverse(this.prime.add(BigInteger.valueOf(-1L))));
//		System.out.println(this.prime);
//		System.out.println(keys.getPrivate());
//		System.out.println(keys.getPublic());
//		System.out.println(keys.getPrivate().multiply(keys.getPublic()).mod(this.prime));
//		System.out.println(keys.getPublic().multiply(keys.getPrivate()).mod(this.prime));
		return keys;
//		KeyPair keyPair = keyGen.generateKeyPair();
//		System.out.println(keyPair.getPrivate().toString());
//		return keyPair;
	}

	public BigInteger getPrime() {
		return this.prime;
	}
	
}
