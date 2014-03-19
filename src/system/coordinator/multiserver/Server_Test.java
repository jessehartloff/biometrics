package system.coordinator.multiserver;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Server_Test {
	

	public Server_Test() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		long startTime = System.currentTimeMillis();
		String password = "ThisIsMyPassword";
		//Symmetric Key Distribution Using Asymmetric Encryption
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		keyGenerator.initialize(1024);
		KeyPair rsaKeys = keyGenerator.generateKeyPair();
		PublicKey publicKey = rsaKeys.getPublic();
		String publicKeyString = publicKey.toString();
		PrivateKey privateKey = rsaKeys.getPrivate();
		
		long keyGenTime = System.currentTimeMillis() - startTime; 
		System.out.println("Took "+ keyGenTime + " milliseconds to generate key pair ");
		//long startEncryption = System.currentTimeMillis();
		
		Cipher cipher = Cipher.getInstance("RSA");
		long startEncryption = System.currentTimeMillis();
		byte[] encryptedKey = null;
		System.out.println("BEFORE WE ENCRYPT");
	
		for ( int i= 0; i<500; i++){
			
			cipher.init(Cipher.ENCRYPT_MODE,publicKey);
			encryptedKey = cipher.doFinal(password.getBytes());
			//cipher.update(encryptedKey);
			System.out.println(new String(encryptedKey, "UTF-8"));
		}
		System.out.println("AFTER WE ENCRYPT");
		long finishEncryption = System.currentTimeMillis();
		
		System.out.println("Time to encrypt is : " + (finishEncryption - startEncryption));
		
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedKey = cipher.doFinal(encryptedKey);
		System.out.println("Time to decrypt is : " + ( System.currentTimeMillis()- finishEncryption));
		System.out.println(new String (decryptedKey, "UTF-8"));
	}
	
	public static void main( String[] args ){
		
		try {
			new Thread((Runnable) new Server_Test()).start();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
