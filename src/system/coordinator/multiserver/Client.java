package system.coordinator.multiserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import settings.coordinatorsettings.multiservercoordinatorsettings.ClientSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerTwoSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;

public class Client extends Server{
	// extends server
	private ObjectOutputStream S1Out;
	private ObjectOutputStream S2Out;
	private ObjectInputStream S1In;
	private Socket S1;
	private Socket S2;


	public Client() {
		super(null, null);
	}

	public HashMap<String, ArrayList<Long>> getAllEnrollTiming() {
		HashMap<String, ArrayList<Long>> allTiming = enrollTiming;
		
		try {
			Socket s1 = new Socket(InetAddress.getByName(ServerOneSettings.getInstance().ip().getValue()), ServerOneSettings.getInstance().portNumber().getValue().intValue());
			
			//send the signal to give back the timing
			ObjectOutputStream toS1 = new ObjectOutputStream(s1.getOutputStream());
			InterServerObjectWrapper signal = new InterServerObjectWrapper();
			signal.setOrigin("getEnrollTiming");
			toS1.writeObject(signal);
			
			//get S1's response
			ObjectInputStream fromS1 = new ObjectInputStream(s1.getInputStream());
			InterServerObjectWrapper s1times = (InterServerObjectWrapper) fromS1.readObject();
			HashMap<String, ArrayList<Long>> s1Timing = (HashMap<String, ArrayList<Long>>) s1times.getContents();
			allTiming.putAll(s1Timing);
			s1.close();
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Socket s2 = new Socket(InetAddress.getByName(ServerTwoSettings.getInstance().ip().getValue()), ServerTwoSettings.getInstance().portNumber().getValue().intValue());
			
			//send the signal to give back the timing
			ObjectOutputStream toS2 = new ObjectOutputStream(s2.getOutputStream());
			InterServerObjectWrapper signal = new InterServerObjectWrapper();
			signal.setOrigin("getEnrollTiming");
			toS2.writeObject(signal);
			
			//get S1's response
			ObjectInputStream fromS2 = new ObjectInputStream(s2.getInputStream());
			InterServerObjectWrapper s2times = (InterServerObjectWrapper) fromS2.readObject();
			HashMap<String, ArrayList<Long>> s2Timing = (HashMap<String, ArrayList<Long>>) s2times.getContents();
			allTiming.putAll(s2Timing);
			s2.close();
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return allTiming;
	}
	
	public HashMap<String, ArrayList<Long>> getAllTestTiming() {
		HashMap<String, ArrayList<Long>> allTiming = testTiming;
		
		try {
			Socket s1 = new Socket(InetAddress.getByName(ServerOneSettings.getInstance().ip().getValue()), ServerOneSettings.getInstance().portNumber().getValue().intValue());
			
			//send the signal to give back the timing
			ObjectOutputStream toS1 = new ObjectOutputStream(s1.getOutputStream());
			InterServerObjectWrapper signal = new InterServerObjectWrapper();
			signal.setOrigin("getTestTiming");
			toS1.writeObject(signal);
			
			//get S1's response
			ObjectInputStream fromS1 = new ObjectInputStream(s1.getInputStream());
			InterServerObjectWrapper s1times = (InterServerObjectWrapper) fromS1.readObject();
			HashMap<String, ArrayList<Long>> s1Timing = (HashMap<String, ArrayList<Long>>) s1times.getContents();
			allTiming.putAll(s1Timing);
			s1.close();
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Socket s2 = new Socket(InetAddress.getByName(ServerTwoSettings.getInstance().ip().getValue()), ServerTwoSettings.getInstance().portNumber().getValue().intValue());
			
			//send the signal to give back the timing
			ObjectOutputStream toS2 = new ObjectOutputStream(s2.getOutputStream());
			InterServerObjectWrapper signal = new InterServerObjectWrapper();
			signal.setOrigin("getTestTiming");
			toS2.writeObject(signal);
			
			//get S1's response
			ObjectInputStream fromS2 = new ObjectInputStream(s2.getInputStream());
			InterServerObjectWrapper s2times = (InterServerObjectWrapper) fromS2.readObject();
			HashMap<String, ArrayList<Long>> s2Timing = (HashMap<String, ArrayList<Long>>) s2times.getContents();
			allTiming.putAll(s2Timing);
			s2.close();
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return allTiming;
	}
	
	public void enroll(Template template, Long userID) {
//		System.out.println(template.getHashes());

//		System.out.println("temp size "+ template.getHashes().size());
//		System.out.println("\tSending Decryption Key to S1...");
		//generate key pair
		long start = System.currentTimeMillis();
		SimpleKeyPair pair = encryptionScheme.generateKeyPair();
		long stop = System.currentTimeMillis();
//		System.out.println("KeyGen time = "+ (stop - start) + " ms");
		addToEnrollTiming("Client KeyGen Time", (stop-start));

		/**
		 * send d(u) [private key] to Server_1
		 */
		//wrap up decryption key to send
		start = System.currentTimeMillis();
		InterServerObjectWrapper toS1 = new InterServerObjectWrapper();
		toS1.setContents(pair.getPrivate());
		toS1.setEnrolling(true);
		toS1.setTesting(false);
		toS1.setUserID(userID);
		toS1.setOrigin("client");
		stop = System.currentTimeMillis();
//		System.out.println("Decrypt object wrapup time = "+ (stop - start) + " ms");
//		addToEnrollTiming("Client Decrypt object wrapup time", (stop-start));
		
		//sendDecryptiontoServerOne(userID, pair);
//		System.out.println("Sending decrypt key...");
		
//		System.out.println("sending key to server 1 at: " + ServerOneSettings.getInstance().ip().getValue() + " port " +
//				ServerOneSettings.getInstance().portNumber().getValue().intValue());
		
		this.send(ServerOneSettings.getInstance().ip().getValue(),
				ServerOneSettings.getInstance().portNumber().getValue().intValue(),
				toS1, true, "decrypt key");
		
		/**
		 * send e_u(T) [user encrypted template] to Server_2
		 */
		//encrypt the template
		System.out.println("\tEncrypting template with e_u...");
//		long start = System.currentTimeMillis();
		Template encryptedBiometric = new Template();
//		for (BigInteger bigInt : template.getHashes()) {
//			BigInteger encryptedPoint = encryptionScheme.encodeAndEncrypt(bigInt, pair.getPublic());
//			encryptedBiometric.getHashes().add(encryptedPoint);
//		}
//		long stop = System.currentTimeMillis();
//		System.out.println("Single time = " +(stop - start));
//		
		start = System.currentTimeMillis();
		encryptedBiometric.setHashes(multiEncrypt(pair.getPublic(), template.getHashes(), true));
		stop = System.currentTimeMillis();
//		System.out.println("MultiEncrypt time = " +(stop - start));
		addToEnrollTiming("Client MultiEncrypt Time", (stop-start));
//		System.out.println(encryptedBiometric.getHashes().size());
		//setup object to send
		start = System.currentTimeMillis();
		InterServerObjectWrapper toS2 = new InterServerObjectWrapper();
		toS2.setEnrolling(true);
		toS2.setTesting(false);
		toS2.setOrigin("client");
		toS2.setUserID(userID);

//		System.out.println("EnrollID:"+userID);
		toS2.setContents(encryptedBiometric);
		stop = System.currentTimeMillis();
//		System.out.println("Encrypted template wrapup time = "+ (stop - start) + " ms");
//		addToEnrollTiming("Client Encrypted template wrapup time", (stop-start));
		//send it to S2
//		System.out.println("\tSending e_u(T) to S2...");
		ServerSocket feedBack = null;
		try {
			feedBack = new ServerSocket(ClientSettings.getInstance().portNumber().getValue().intValue());
		} catch (IOException e) {
			System.out.println("Couldn't make server socket...");
			e.printStackTrace();
		}
		
		
		
		send(ServerTwoSettings.getInstance().ip().getValue(), 
				ServerTwoSettings.getInstance().portNumber().getValue().intValue(),
				toS2, true, "e_u(T)");
		
		/**
		 * wait for decision from server1
		 */
		start = System.currentTimeMillis();
//		System.out.println("Waiting for enroll response from S1...");
		//create socket for when server 1 responds
		
//		System.out.println("Listening on port " + feedBack.getLocalPort());
		InterServerObjectWrapper decision = receive(feedBack, true, "enroll feedback");
//		System.out.println("got something");
		try {
			feedBack.close();
		} catch (IOException e) {
			System.out.println("Failed to close client server socket");
			e.printStackTrace();
		}
		//print out the received decision
//		System.out.println(decision.getContents().toString());
		stop = System.currentTimeMillis();
		addToEnrollTiming("Client Wait for enroll decision time", (stop-start));
		return;
	}

	public Double test(ArrayList<Template> testTemplates,  Long userID) {
//		System.out.println(testTemplates.get(0).getHashes());
		//generate key pair
		long start = System.currentTimeMillis();
		SimpleKeyPair pair = encryptionScheme.generateKeyPair();
		long stop = System.currentTimeMillis();
//		addToTestTiming("Client KeyGen Time", (stop-start));


		/**
		 * send d(u) [private key] to Server_1
		 */
//		System.out.println("\tSending Decryption Key to S1...");
		//wrap up decryption key to send
		start = System.currentTimeMillis();
		InterServerObjectWrapper toS1 = new InterServerObjectWrapper();
		toS1.setContents(pair.getPrivate());
		toS1.setEnrolling(false);
		toS1.setTesting(true);
		toS1.setUserID(userID);		
//		System.out.println("TestID:"+userID);

		toS1.setOrigin("client");
		stop = System.currentTimeMillis();
//		addToTestTiming("Client Decrypt key", (stop-start));
		
		//sendDecryptiontoServerOne(userID, pair);
		send(ServerOneSettings.getInstance().ip().getValue(),
				ServerOneSettings.getInstance().portNumber().getValue().intValue(),
				toS1, false, "decrypt key");
		
		/**
		 * send e_u({T}) [each user encrypted template] to Server_2
		 */
		//encrypt the template
		//for all testing templates
//		System.out.println("\tEncrypting templates with e_u...");
		start = System.currentTimeMillis();
		ArrayList<Template> encryptedTemplates = new ArrayList<Template>();
		for ( Template template : testTemplates) {
//			Template encryptedBiometric = new Template();
//			multiEncrypt(pair.getPublic(), template.getHashes());
//			encryptedTemplates.add(template.setHashes());
			Template encryptedHashes = new Template();
			encryptedHashes.setHashes(multiEncrypt(pair.getPublic(), template.getHashes(), true));
//			template.setHashes(hashes);
//			Template insertT = new Template();
//			insertT.getHashes().addAll(hashes);
//			insertT.setHashes(hashes);
			encryptedTemplates.add(encryptedHashes);
////			System.out.println("Encrypting template with e_u...");
//			//for each point in this template
			
//			for (BigInteger bigInt : template.getHashes()) {
//				BigInteger encryptedPoint = encryptionScheme.encrypt(pair.getPublic(), bigInt);
//				encryptedBiometric.getHashes().add(encryptedPoint);
//			}
//			encryptedTemplates.add(encryptedBiometric);
		}
		stop = System.currentTimeMillis();
//		addToTestTiming("Client encrypt all templates time", (stop-start));
		addToTestTiming("Client encrypt per template time", (stop-start)/testTemplates.size());
		
		//setup object to send
		start = System.currentTimeMillis();
		InterServerObjectWrapper toS2 = new InterServerObjectWrapper();
		toS2.setEnrolling(false);
		toS2.setTesting(true);
		toS2.setOrigin("client");
		toS2.setUserID(userID);
		toS2.setContents(encryptedTemplates);
		stop = System.currentTimeMillis();
//		addToTestTiming("Client e_u({T}) wrapup time", (stop-start));

		//send it to S2
//		System.out.println("\tSending e_u({T}) to S2...");
		send(ServerTwoSettings.getInstance().ip().getValue(), 
				ServerTwoSettings.getInstance().portNumber().getValue().intValue(),
				toS2, false, "e_u({T})");
		
		/**
		 * wait for decision from server1
		 */
		//create socket for when server 1 responds
		start = System.currentTimeMillis();
		ServerSocket feedBack = null;
		try {
			feedBack = new ServerSocket(ClientSettings.getInstance().portNumber().getValue().intValue());
		} catch (IOException e) {
			System.out.println("Couldn't make server socket...");
			e.printStackTrace();
		}
//		System.out.println("\tWaiting for test score from S1...");
		InterServerObjectWrapper decision = receive(feedBack, false, "test decision");
//		System.out.println(decision.getContents().toString());
//		System.out.println(feedBack);

		try {
			feedBack.close();
		} catch (IOException e) {
			System.out.println("Failed to close client server socket");
			e.printStackTrace();
		}
		//print out the received decision
		stop = System.currentTimeMillis();
		addToEnrollTiming("Client Wait for test decision time", (stop-start));

		return (Double) decision.getContents();
	
	}


	@Override
	public RawScores run() {
		System.out.println("Client run should never be called... so something's wrong");
		System.exit(0);
		return null;
	}

}
