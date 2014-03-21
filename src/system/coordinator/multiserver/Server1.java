package system.coordinator.multiserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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

public class Server1 extends Server {

	// FIXME EVERYONE EVER
	Server1(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
		
	}

	@Override
	public RawScores run() {
		return null;
		

	}

	// put all four of these functions in Server
	@Override
	public Object receive(Object object) {
		// make an ID class to handle uuid per transaction, server2 and client
		// origin and user id, plus enrollment/testing boolean
		// 0.) if it's the first time S1 has seen the user id, then it must be
		// an enrollment

		// 1.) Receive input from client and Server_2
		// 2.) Determine where the the input is coming from based on UUID
		// presence/matching in some external data structure
		// 2a.) Making an external thread to handle this process
		// 3.) if there's a match, then call other functions that handle each
		// case( from client or serve 2); these functions then call send(..)
		return object;
	}

	public void enroll() {
		// 1.) Call Hasher.hashEnrollTemplate on enrolling Templates
		// 2.) Store that Template in RAM BECAUSE SQL JIM AND JEN!
	}

	public void test() {
		// 1.) Get test Templates from Hasher with Hasher.hashTestTemplates
		// 2.) Compare the test Templates and the enrolled Templates with
		// Hasher.compareTemplates
		//
	}

	@Override
	public void send(Object object) {
		// Send that bitch back to the client
	}

	public static void receivePrivateKey() throws Exception {

		/*
		 * int port = 0; ServerSocket serv_socket = null; Socket client = null;
		 * serv_socket = new ServerSocket(8000);
		 * serv_socket.setReuseAddress(true); port = serv_socket.getLocalPort();
		 * System.out.println(port); client = serv_socket.accept();
		 * 
		 * ObjectInputStream objInput = new
		 * ObjectInputStream(client.getInputStream()); PublicKey pk =
		 * (PublicKey) objInput.readObject(); System.out.println(pk.toString());
		 * 
		 * long startTime = System.currentTimeMillis(); String password =
		 * "ThisIsMyPassword"; //Symmetric Key Distribution Using Asymmetric
		 * Encryption KeyPairGenerator keyGenerator =
		 * KeyPairGenerator.getInstance("RSA"); keyGenerator.initialize(1024);
		 * KeyPair rsaKeys = keyGenerator.generateKeyPair(); PublicKey publicKey
		 * = rsaKeys.getPublic(); String publicKeyString = publicKey.toString();
		 * PrivateKey privateKey = rsaKeys.getPrivate();
		 * 
		 * long keyGenTime = System.currentTimeMillis() - startTime;
		 * System.out.println("Took "+ keyGenTime +
		 * " milliseconds to generate key pair "); //long startEncryption =
		 * System.currentTimeMillis();
		 * 
		 * Cipher cipher = Cipher.getInstance("RSA"); long startEncryption =
		 * System.currentTimeMillis(); byte[] encryptedKey = null;
		 * System.out.println("BEFORE WE ENCRYPT");
		 * 
		 * for ( int i= 0; i<5; i++){
		 * 
		 * cipher.init(Cipher.ENCRYPT_MODE,publicKey); encryptedKey =
		 * cipher.doFinal(password.getBytes()); //cipher.update(encryptedKey);
		 * System.out.println(new String(encryptedKey, "UTF-8")); }
		 * System.out.println("AFTER WE ENCRYPT"); long finishEncryption =
		 * System.currentTimeMillis();
		 * 
		 * System.out.println("Time to encrypt is : " + (finishEncryption -
		 * startEncryption));
		 * 
		 * cipher.init(Cipher.DECRYPT_MODE, privateKey); byte[] decryptedKey =
		 * cipher.doFinal(encryptedKey);
		 * System.out.println("Time to decrypt is : " + (
		 * System.currentTimeMillis()- finishEncryption));
		 * System.out.println(new String (decryptedKey, "UTF-8"));
		 */
	}

	public static void main(String[] args) {
		try {
			receivePrivateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
