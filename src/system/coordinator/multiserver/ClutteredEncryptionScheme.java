package system.coordinator.multiserver;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.ECParameterSpec;
//import java.security.Security;
//import java.security.spec.ECFieldFp;
//import java.security.spec.ECParameterSpec;
//import java.security.spec.ECPoint;
//import java.security.spec.EllipticCurve;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.math.ec.ECPoint;


public class ClutteredEncryptionScheme {

	private Cipher cipher;
	private AlgorithmParameters parameters;
	private KeyPairGenerator keyGen;
//	private BigInteger prime;
	private BigInteger p;
	private BigInteger q;
	private BigInteger gx;
	private BigInteger gy;
	private ECPoint g;
	
	public BigInteger pointDouble(ECPoint point){
		
		return null;
	}
	
	public ClutteredEncryptionScheme(){
		this.initialize();
	}
	
	public ECPoint getG() {
		return g;
	}

	public BigInteger getP() {
		return p;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}

	public BigInteger getQ() {
		return q;
	}

	public void setQ(BigInteger q) {
		this.q = q;
	}

	public void setG(ECPoint g) {
		this.g = g;
	}

//	public ECPoint multiply(ECPoint point, BigInteger multiplyBy){
//		ECPoint toReturn = null;
//		ECPoint currentPoint = point.multiply(BigInteger.ONE);
////		BigInteger multiplyBy = multiplyBy.mod(this.q);
////		System.out.println("start" + point);
////		System.out.println("x: " + multiplyBy);
////		System.out.println("x: " + multiplyBy.bitLength());
//		
//		BigInteger currentBit = BigInteger.ONE;
//		for(int i=0; i<multiplyBy.bitLength(); i++){
////			System.out.println(currentBit);
//			if(multiplyBy.and(currentBit).equals(currentBit)){
////				System.out.println("!!!!");
//				if(toReturn == null){
////					System.out.println("!!!!");
//					toReturn = currentPoint.multiply(BigInteger.ONE);
//				}else{
//					toReturn = toReturn.add(currentPoint);
//				}
//			}
////			System.out.println(currentPoint.getXCoord().toBigInteger());
//			currentPoint = currentPoint.twice();
//			currentBit = currentBit.shiftLeft(1);
//		}
////		System.out.println(toReturn);
//		return toReturn;
//	}
	

	private void initialize() {
//		this.prime = BigInteger.probablePrime(200, new Random());
//		ECFieldElement elem = new ECFieldElement();
//	    Fp point0 = new Fp(null, null, null, false);
//	    ECPoint ppp = new ECPoint();
	    
		X9ECParameters curve = NISTNamedCurves.getByName("P-192");
	
//		curve.getG().multiply(arg0)
		this.g = curve.getG();
//		System.out.println("a: " + curve.getCurve().getA().toBigInteger());
//		System.out.println("b: " + curve.getCurve().getB().toBigInteger());
//		Fp point3 = new Fp(null, null, null, null, false)
		this.q = curve.getN();
		
		curve.getH();
		
//		curve.getG().getAffineXCoord().toBigInteger();
//		System.out.println("n: "  + curve.getN());
//		System.out.println("gx: " + curve.getG().getAffineXCoord().toBigInteger());
//		System.out.println("gy: " + curve.getG().getAffineYCoord().toBigInteger());
		

		this.gx = curve.getG().getAffineXCoord().toBigInteger();
		this.gy = curve.getG().getAffineYCoord().toBigInteger();
//		this.g = this.gx.shiftLeft(1).add(this.gy.and(BigInteger.ONE));
		
		
//		System.out.println("gx: " + new BigInteger("188da80e"+"b03090f6"+"7cbf20eb"+"43a18800"+"f4ff0afd"+"82ff1012", 16));
//		System.out.println("gy: " + curve.getG().getAffineYCoord().toBigInteger());

//		System.out.println("q: "  + this.q);
		this.p = new BigInteger("6277101735386680763835789423207666416083908700390324961279"); //p
		this.q = new BigInteger("6277101735386680763835789423176059013767194773182842284081"); //r, q, n
//		System.out.println("q: "  + this.q);
		
		
//		System.out.println("&^" + this.gx.pow(3).add(curve.getCurve().getA().toBigInteger().multiply(this.gx)).add(curve.getCurve().getB().toBigInteger()).mod(this.p));
//		System.out.println("&^" + this.gy.pow(2).mod(this.p));
		
		
//		this.g = new BigInteger("2");
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
////			EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(
////					"fffffffffffffffffffffffffffffffeffffffffffffffff", 16)), new BigInteger(
////							"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
////									"fffffffffffffffffffffffffffffffefffffffffffffffc", 16));
//
//			ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(new BigInteger(
//					"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
//							"fffffffffffffffffffffffffffffffefffffffffffffffc", 16)), new BigInteger(
//									"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), 1);
//			
//			ECParameterSpec ee = new ECParameterSpec(curve.getCurve(), curve.getG(), this.getP(), 1);
//			keyGen.initialize(, new SecureRandom());
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

//	public BigInteger firstEncryption(BigInteger key, BigInteger message){
//		return this.gx.modPow(message, this.p).modPow(key, this.p);
//	}
	
	
	public ECPoint encodeMessage(BigInteger message){
		return this.g.multiply(message);
	}
	

	public MyECPoint myEncodeMessage(BigInteger message){
		MyECPoint myG = new MyECPoint(this.g);
		return myG.multiply(message);
	}
	
//	private BigInteger getG() {
//		return this.g;
//	}

	public ECPoint encrypt(ECPoint messagePoint, BigInteger key){
		return messagePoint.multiply(key);
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
	
	public MyECPoint encrypt(MyECPoint messagePoint, BigInteger key){
		return messagePoint.multiplyBouncy(key);
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
	
//	public BigInteger lastDecryption(BigInteger key, BigInteger minutia){
//		return minutia.modPow(key, this.p);
//	}
	
	public ECPoint decrypt(ECPoint messagePoint, BigInteger key){
		return messagePoint.multiply(key);
//	public BigInteger decrypt(BigInteger key, BigInteger minutia){
//		return minutia.modPow(key, this.p);
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
			keys.setPrivateKey(keys.getPrivate().mod(this.q));
//			System.out.println(keys.getPrivate().gcd(this.prime.add(BigInteger.valueOf(-1L))));
//			if(keys.getPrivate().gcd(this.q.add(BigInteger.valueOf(-1L))).equals(BigInteger.ONE)){
//				break;
//			}
			break;
		}
		keys.setPublicKey(keys.getPrivate().modInverse(this.q.add(BigInteger.valueOf(0L))).mod(this.q));
//		System.out.println(this.prime);
//		System.out.println(keys.getPrivate());
//		System.out.println(keys.getPublic());
//		System.out.println(keys.getPrivate().multiply(keys.getPublic()).mod(this.q));
//		System.out.println(keys.getPublic().multiply(keys.getPrivate()).mod(this.q));
		return keys;
//		KeyPair keyPair = keyGen.generateKeyPair();
//		try {
//			KeyFactory fac = KeyFactory.getInstance("ECIES");
//			KeySpec keySpec;
//			
//			try {
//				fac.getKeySpec(keyPair.getPrivate(), keySpec);
//			} catch (InvalidKeySpecException e) {
//				e.printStackTrace();
//			}
//			
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(keyPair.getPrivate().toString());
//		return keyPair;
	}

//	public BigInteger getPrime() {
//		return this.prime;
//	}
//	
}
