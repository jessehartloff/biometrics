package system.coordinator.multiserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.ArrayList;

import javax.crypto.Cipher;

import settings.coordinatorsettings.multiservercoordinatorsettings.ClientSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerTwoSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Client extends Server{
	// extends server
	private ObjectOutputStream S1Out;
	private ObjectOutputStream S2Out;
	private ObjectInputStream S1In;
	private Socket S1;
	private Socket S2;


	public Client() {
		super(null, null);
		
//		try {
//			System.out.println("Connecting to Socket 1...");
//			S1 = new Socket(InetAddress.getByName(ServerOneSettings.getInstance().ip().getValue()), ServerOneSettings.getInstance().portNumber().getValue().intValue());
////			System.out.println("Connecting to Socket 2...");
////			Socket S2 = new Socket(InetAddress.getByName(ServerTwoSettings.getInstance().ip().getValue()), ServerTwoSettings.getInstance().portNumber().getValue().intValue());
//
//			S1Out = new ObjectOutputStream (S1.getOutputStream());
//			System.out.println("Got Socket 1 Outputstream...");
//			
//
//
//
//			//System.out.println("Getting Socket 2 Inputstream...");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}


//	public void sendDecryptiontoServerOne(Long userID, KeyPair pair) {
//		
//
////		//establish connection to Server 1
////		
////		try {
////			S1 = new Socket(InetAddress.getByName(ServerOneSettings.getInstance().ip().getValue()), ServerOneSettings.getInstance().portNumber().getValue().intValue());
////		} catch (UnknownHostException e) {
////			// TODO Auto-generated catch block
////			System.out.println("Unknown Host encountered...");
////			e.printStackTrace();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			System.out.println("IO Exception...");
////			e.printStackTrace();
////		}
////		
////		//send out the decryption key to S1
////		try {
////			S1Out.writeObject(toS1);
////			System.out.println("Client sent decyption to S1");
////		}
////		catch (Exception e) {
////			System.out.println("Sending decyprtion to S1 Failed...");
////			e.printStackTrace();
////		}
////		
////		//establish that S1 received the decryption key
////		try {
////			S1In = new ObjectInputStream(S1.getInputStream());
////			int check =  S1In.read();
////			if (check == 0) System.out.println("Server 1 successfully received decryption key");
////			else System.out.println("Server 1 didn't receive decryption key");
////
////		}
////		catch( Exception e) {
////			System.out.println("Receiveing S1 responds failed...");
////			e.printStackTrace();
////		}
//
//
//	}
//	
//	public void sendEncryptedTemplatetoServerTwo(Long userID, KeyPair pair, Template template) {
//
//		
//		//encrypt the template
//		Template encryptedBiometric = new Template();
//		System.out.println("Encrypting template with e_u...");
//		try{
//			//it may be better to abstract this cipher later...
//			Cipher cipher = Cipher.getInstance("ECIES","BC");
//			cipher.init(Cipher.ENCRYPT_MODE,pair.getPublic());
//			for (BigInteger bigInt : template.getHashes()) {
//				encryptedBiometric.getHashes().add(new BigInteger(cipher.doFinal(bigInt.toByteArray())));
//			}
//
//		}catch(Exception e){
//			System.out.println("Client encryption of Tempalte failed...");
//			e.printStackTrace();
//		}
//		
//		//setup object to send
//		InterServerObjectWrapper toS2 = new InterServerObjectWrapper();
//		toS2.setEnrolling(true);
//		toS2.setTesting(false);
//		toS2.setOrigin("client");
//		toS2.setUserID(userID);
//		toS2.setContents(encryptedBiometric);
//
//		//establish connection to S2
//		System.out.println("Connecting to S2...");
//		try {
//			S2 = new Socket(InetAddress.getByName(ServerTwoSettings.getInstance().ip().getValue()), ServerTwoSettings.getInstance().portNumber().getValue().intValue());
//			S2Out = new ObjectOutputStream (S2.getOutputStream());
//		}
//		catch(Exception e) {
//			System.out.println("Establishing connection to S2 failed...");
//			e.printStackTrace();
//		}
//		
//		
//		//send e(Template) to Server_2
//		System.out.println("Sending Encrypting template to S2...");
//		try{	
//			
//			S2Out.writeObject(toS2);
//			S2.close();
//
//
//
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//		System.out.println("Successfully sent e(Template) to Server_2");
//	}
//	
//	public void waitForDecision() {
//		ServerSocket client;
//		try {
//			client = new ServerSocket(ClientSettings.getInstance().portNumber().getValue().intValue());
//			Socket c = client.accept();
//			S1In = new ObjectInputStream(c.getInputStream());
//			int check = S1In.read();
//			if (check == 0) System.out.println("Server 1 has successfully enrolled this template");
//			else System.out.println("Server 1 failed to enroll this template");
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Failed to get decision from Server 1");
//			e.printStackTrace();
//		}
//	}
//
//
//	public KeyPair getKeyPair(){
//		try{
//			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//			//TODO figure out what this ECDH stuff does
//			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECIES", "BC");
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
//			return keyGen.generateKeyPair();
//		} catch (Exception e){
//			e.printStackTrace();
//			return null;
//		}
//
//	}
//
//	@Override
//	public RawScores run() {
////		long start = System.currentTimeMillis();
////		enroll(hasher.makeEnrollTemplate(users.users.get(0).readings.get(0)), users.users.get(0).id);
////		long stop = System.currentTimeMillis();
////		System.out.println("enrolled in "+ (stop-start) + "sec");
////		test(hasher.makeTestTemplates(users.users.get(0).readings.get(0)), users.users.get(0).id);
////		System.out.println("tested!!");
//
//		System.exit(0);
//		return null;
//	}

	public void enroll(Template template, Long userID) {
		
		System.out.println("Sending Decryption Key to S1...");
		//generate key pair
		KeyPair pair = encryptionScheme.generateKeyPair();

		/**
		 * send d(u) [private key] to Server_1
		 */
		//wrap up decryption key to send
		InterServerObjectWrapper toS1 = new InterServerObjectWrapper();
		toS1.setContents(pair.getPrivate());
		toS1.setEnrolling(true);
		toS1.setTesting(false);
		toS1.setUserID(userID);
		toS1.setOrigin("client");
		
		//sendDecryptiontoServerOne(userID, pair);
		send(ServerOneSettings.getInstance().ip().getValue(),
				ServerOneSettings.getInstance().portNumber().getValue().intValue(),
				toS1);
		
		/**
		 * send e_u(T) [user encrypted template] to Server_2
		 */
		//encrypt the template
		System.out.println("Encrypting template with e_u...");
		Template encryptedBiometric = new Template();
		for (BigInteger bigInt : template.getHashes()) {
			BigInteger encryptedPoint = encryptionScheme.encrypt(pair.getPublic(), bigInt);
			encryptedBiometric.getHashes().add(encryptedPoint);
		}
		
		//setup object to send
		InterServerObjectWrapper toS2 = new InterServerObjectWrapper();
		toS2.setEnrolling(true);
		toS2.setTesting(false);
		toS2.setOrigin("client");
		toS2.setUserID(userID);
		toS2.setContents(encryptedBiometric);

		//send it to S2
		System.out.println("Sending e_u(T) to S2...");
		send(ServerTwoSettings.getInstance().ip().getValue(), 
				ServerTwoSettings.getInstance().portNumber().getValue().intValue(),
				toS2);
		
		/**
		 * wait for decision from server1
		 */
		System.out.println("Waiting for enroll response from S1...");
		InterServerObjectWrapper decision = new InterServerObjectWrapper();
		decision.setContents("No decision was received");
		try {
			ServerSocket feedBack = new ServerSocket(ClientSettings.getInstance().portNumber().getValue().intValue());
			decision = receive(feedBack);

		} catch (IOException e) {
			System.out.println("Couldn't create client server socket");
			e.printStackTrace();
		}
		
		//print out the received decision
		System.out.println(decision.getContents().toString());
		
	}

	public Double test(ArrayList<Template> testTemplates,  Long userID) {
		//generate key pair
		KeyPair pair = encryptionScheme.generateKeyPair();

		/**
		 * send d(u) [private key] to Server_1
		 */
		System.out.println("Sending Decryption Key to S1...");
		//wrap up decryption key to send
		InterServerObjectWrapper toS1 = new InterServerObjectWrapper();
		toS1.setContents(pair.getPrivate());
		toS1.setEnrolling(false);
		toS1.setTesting(true);
		toS1.setUserID(userID);
		toS1.setOrigin("client");
		
		//sendDecryptiontoServerOne(userID, pair);
		send(ServerOneSettings.getInstance().ip().getValue(),
				ServerOneSettings.getInstance().portNumber().getValue().intValue(),
				toS1);
		
		/**
		 * send e_u({T}) [each user encrypted template] to Server_2
		 */
		//encrypt the template
		//for all testing templates
		System.out.println("Encrypting templates with e_u...");
		ArrayList<Template> encryptedTemplates = new ArrayList<Template>();
		for ( Template template : testTemplates) {
			Template encryptedBiometric = new Template();
			System.out.println("Encrypting template with e_u...");
			//for each point in this template
			for (BigInteger bigInt : template.getHashes()) {
				BigInteger encryptedPoint = encryptionScheme.encrypt(pair.getPublic(), bigInt);
				encryptedBiometric.getHashes().add(encryptedPoint);
			}
			encryptedTemplates.add(encryptedBiometric);
		}
		
		//setup object to send
		InterServerObjectWrapper toS2 = new InterServerObjectWrapper();
		toS2.setEnrolling(false);
		toS2.setTesting(true);
		toS2.setOrigin("client");
		toS2.setUserID(userID);
		toS2.setContents(encryptedTemplates);

		//send it to S2
		System.out.println("Sending e_u({T}) to S2...");
		send(ServerTwoSettings.getInstance().ip().getValue(), 
				ServerTwoSettings.getInstance().portNumber().getValue().intValue(),
				toS2);
		
		/**
		 * wait for decision from server1
		 */
		System.out.println("Waiting for test score from S1...");
		InterServerObjectWrapper decision = new InterServerObjectWrapper();
		decision.setContents("No decision was received");
		try {
			ServerSocket feedBack = new ServerSocket();
			decision = receive(feedBack);

		} catch (IOException e) {
			System.out.println("Couldn't create client server socket");
			e.printStackTrace();
		}
		
		//print out the received decision
		return (Double) decision.getContents();
	
	}


	@Override
	public RawScores run() {
		System.out.println("Cleint run should never be called... so somethings wrong");
		System.exit(0);
		return null;
	}

}
