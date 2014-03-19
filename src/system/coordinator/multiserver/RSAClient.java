package system.coordinator.multiserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAClient {
	public void RSAKeyExchange() throws NoSuchAlgorithmException, UnknownHostException, IOException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		
		long startTime = System.currentTimeMillis();
		
		//Symmetric Key Distribution Using Asymmetric Encryption
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		keyGenerator.initialize(1024);
		KeyPair rsaKeys = keyGenerator.generateKeyPair();
		PublicKey publicKey = rsaKeys.getPublic();
		String publicKeyString = publicKey.toString();
		PrivateKey privateKey = rsaKeys.getPrivate();
		long keyGenTime = System.currentTimeMillis() - startTime; 
		System.out.println("Took "+ keyGenTime + " milliseconds to generate key pair ");
		
		System.out.println("Public key is " + publicKeyString);
		//System.out.println("Length of public key is :" + publicKeyString.length());
		
		
		Socket clnt_socket;
		int port = 58196;
		clnt_socket = new Socket(InetAddress.getLocalHost(),port);
		ObjectOutputStream objOut = new ObjectOutputStream(clnt_socket.getOutputStream());
		objOut.writeObject(publicKey);
		
		byte[] inputBuffer = new byte[128];
		BufferedInputStream inputStream = new BufferedInputStream(clnt_socket.getInputStream());
		inputStream.read(inputBuffer,0,128);
		System.out.println("Received Encrypted Key :" +new String(inputBuffer, "UTF-8"));
		
		long startDecryption = System.currentTimeMillis();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedKey = cipher.doFinal(inputBuffer);
		System.out.println("Decrypted Key :" +new String(decryptedKey, "UTF-8"));
		
		long finishTime = System.currentTimeMillis();
		System.out.println("Time for Decryption is :" + (finishTime - startDecryption));
		System.out.println("Time For RSA Key Exchange Was : " + (finishTime-startTime) + " milliseconds");
		
	}
	public Cipher getCipherInstance(String cipherType){
		
		try {
			return Cipher.getInstance(cipherType);
		} catch (Exception e) {
			System.out.println("Something is broke: could not get Cipher Instance");
		} 
		return null;
	}
	
	public BigInteger decryption(Cipher cipher, PrivateKey privateKey, BigInteger minutia){
		byte[] decryptedKey = null;
		try{
			//cipher = Cipher.getInstance("RSA"); 
			cipher.init(Cipher.DECRYPT_MODE, privateKey); //make sure to pull this out before doing multiple decryptions when you integrate this function
			decryptedKey = cipher.doFinal(minutia.toByteArray());
		}catch(Exception e){
			
		}	
		// if we need to send it can use an Object Output Stream : return (Object)decryptedKey;
		return new BigInteger(decryptedKey);
	}
	
	public String decryptionString(Cipher cipher, PrivateKey privateKey, byte[] minutia) throws UnsupportedEncodingException{
		byte[] decryptedKey = null;
		try{
			//cipher = Cipher.getInstance("RSA"); 
			cipher.init(Cipher.DECRYPT_MODE, privateKey); //make sure to pull this out before doing multiple decryptions when you integrate this function
			decryptedKey = cipher.doFinal(minutia);
		}catch(Exception e){
			
		}	
		// if we need to send it can use an Object Output Stream : return (Object)decryptedKey;
		return new String(decryptedKey, "UTF-8");
	}
	public byte[] encryptString(PublicKey publicKey, String minutia){
		byte[] encryptedKey = null;
		try{
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE,publicKey);
			encryptedKey = cipher.doFinal(minutia.getBytes());
		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return encryptedKey;
	}
	public void testMethods() throws NoSuchAlgorithmException{
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		keyGenerator.initialize(1024);
		KeyPair rsaKeys = keyGenerator.generateKeyPair();
		PublicKey publicKey = rsaKeys.getPublic();
		String publicKeyString = publicKey.toString();
		PrivateKey privateKey = rsaKeys.getPrivate();
		String password = "thisIsMyPassword";
		Cipher cipher = getCipherInstance("RSA");
		String decryptedPassword = null;
		try {
			decryptedPassword = decryptionString(cipher, privateKey, encryptString(publicKey, password)  );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		System.out.println("DecryptedString is : " +decryptedPassword);
	}
	
	public KeyPair generateKeyPair() throws NoSuchAlgorithmException{
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		keyGenerator.initialize(1024);
		KeyPair rsaKeys = keyGenerator.generateKeyPair();
		// How to get public Key  :  PublicKey publicKey = rsaKeys.getPublic();	
		// To get private key     :  PrivateKey privateKey = rsaKeys.getPrivate();
		return rsaKeys;
	}
	
	public static void main ( String[] args){
		RSAClient client = new RSAClient();
		try {
			client.testMethods();
			//client.RSAKeyExchange();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
