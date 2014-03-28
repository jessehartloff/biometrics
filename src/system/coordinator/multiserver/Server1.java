package system.coordinator.multiserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.PrivateKey;
import java.util.ArrayList;

import javax.crypto.Cipher;

import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Server1 extends Server {
	
	public static void main(String[] args){
		
		
		
	}
	
	
	// FIXME EVERYONE EVER
	Server1(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
		
	}

	@Override
	public RawScores run() {
		int clientPort = 8000, S2Port = 8002;

		try {
			Socket client = new Socket("localhost", clientPort);
			Socket S2 = new Socket("localhost", S2Port);
			InputStream clientIn = client.getInputStream();
			InputStream S2In = S2.getInputStream();
			OutputStream S1Out = client.getOutputStream();
			InputStreamReader clientReader = new InputStreamReader(clientIn); 
			InputStreamReader S2reader = new InputStreamReader(S2In); 
		
			Cipher cipher = Cipher.getInstance("DH");
	        
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        int data = clientReader.read();
	        while(data != -1){
	        	buffer.write(data);
	            data = clientReader.read();
	        }
	        buffer.flush();
	        clientReader.close();  

			ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer.toByteArray());
	        ObjectInputStream objStream = new ObjectInputStream(byteStream);
	        PrivateKey pk = (PrivateKey) objStream.readObject();
	        
	        buffer = new ByteArrayOutputStream();
	        data = S2reader.read();
	        while(data != -1){
	        	buffer.write(data);
	            data = S2reader.read();
	        }
	        buffer.flush();
	        
	        
	        byteStream = new ByteArrayInputStream(buffer.toByteArray());
	        objStream = new ObjectInputStream(byteStream);
	        ArrayList<Template> encryptedFingerprint = (ArrayList<Template>) objStream.readObject();       
	        
			cipher.init(Cipher.DECRYPT_MODE, pk);
			
			ArrayList<Template> decryptedFingerprint = new ArrayList<Template>();
			for(Template template : encryptedFingerprint){
				Template decryptedTemplate = new Template();
				for(BigInteger bigInt : template.getHashes()){
					decryptedTemplate.getHashes().add(new BigInteger(cipher.doFinal(bigInt.toByteArray())));	
				}
				decryptedFingerprint.add(decryptedTemplate);
			}
			this.hasher.hashEnrollTemplate(template);

			
			
		} catch(Exception e){
			e.printStackTrace();
		}

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
