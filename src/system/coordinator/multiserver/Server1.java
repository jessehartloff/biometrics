package system.coordinator.multiserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Server1 extends system.coordinator.Coordinator {
	
	
	Server1(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
	}
	

	@Override
	public RawScores run() {
		return null;
	}
	
	public static void receivePrivateKey() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		/*
		int port = 0;
		ServerSocket serv_socket = null;
		Socket client = null;
		serv_socket = new ServerSocket(0);
		port = serv_socket.getLocalPort();
		System.out.println(port);
		client = serv_socket.accept();
		*/
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
	public static void main( String[] args){
		try {
			receivePrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

}
