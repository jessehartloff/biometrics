package system.coordinator.multiserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.math.ec.ECPoint;


public class EncryptionScheme {

	private BigInteger p;
	private BigInteger q;
	private ECPoint g;
	private X9ECParameters curve;

	public EncryptionScheme(){
		this.initialize();
	}
	
	public ECPoint getG() {
		return g;
	}

	public BigInteger getP() {
		return p;
	}

	private void initialize() {
		curve = NISTNamedCurves.getByName("P-192");
	
		this.g = curve.getG();
		this.q = curve.getN();
		
		this.p = new BigInteger("6277101735386680763835789423207666416083908700390324961279"); //p
//		this.q = new BigInteger("6277101735386680763835789423176059013767194773182842284081"); //r, q, n
	}


	
	
	
	public BigInteger encodeMessage(BigInteger message){
		return new BigInteger(this.g.multiply(message).getEncoded());
	}
	
	public BigInteger encodeAndEncrypt(BigInteger message, BigInteger key){ //bingsheng's encrypt
		return new BigInteger(this.g.multiply(message.multiply(key)).getEncoded());
	}


	public BigInteger encrypt(BigInteger messagePoint, BigInteger key){ //bingsheng's reencrypt
		ECPoint decoded = this.curve.getCurve().decodePoint(messagePoint.toByteArray());
		return new BigInteger(decoded.multiply(key).getEncoded());
	}

	public BigInteger decrypt(BigInteger messagePoint, BigInteger key){
		return this.encrypt(messagePoint, key);
	}
	

	public SimpleKeyPair generateKeyPair(){
		SimpleKeyPair keys = new SimpleKeyPair();
		keys.setPrivateKey(new BigInteger(190, new SecureRandom()));
		keys.setPrivateKey(keys.getPrivate().mod(this.q));
		
		keys.setPublicKey(keys.getPrivate().modInverse(this.q.add(BigInteger.valueOf(0L))).mod(this.q));

		return keys;
	}

}
