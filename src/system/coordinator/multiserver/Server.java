package system.coordinator.multiserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import settings.coordinatorsettings.multiservercoordinatorsettings.AllMultiserverCoordinatorSettings;
import system.allcommonclasses.commonstructures.Users;
import system.coordinator.Coordinator;
import system.hasher.Hasher;

public abstract class Server extends Coordinator {
	protected EncryptionScheme encryptionScheme;
	protected HashMap<String, Long> enrollTiming;
	protected HashMap<String, Long> testTiming;
	protected String serverName;
	
	public Server(Hasher hasher, Users users) {
		super(hasher, users);
		encryptionScheme = new EncryptionScheme();
		serverName = AllMultiserverCoordinatorSettings.getInstance().getMultiserverCoordinator();
		enrollTiming = new HashMap<String, Long>();
		testTiming = new HashMap<String, Long>();
	}

	public void addToEnrollTiming(String timeName, Long time){
		//accumulate all timings.  Average will be taken at end
		if( !enrollTiming.containsKey(timeName)) {
			enrollTiming.put(timeName, time);
		} else{
			enrollTiming.put(timeName, enrollTiming.get(timeName) + time);
		}
	}
	
	public void addToTestTiming(String timeName, Long time){
		if( !testTiming.containsKey(timeName)) {
			testTiming.put(timeName, time);
		} else{
			testTiming.put(timeName, testTiming.get(timeName) + time);
		}
	}
	
	// base server class extends coordinator

	protected InterServerObjectWrapper receive(ServerSocket serverSocket, boolean enroll, String timeName){//enroll used for differentiating timing
		long t0 = System.currentTimeMillis();
		try{
			long start = System.currentTimeMillis();
			Socket client = serverSocket.accept();
			long stop = System.currentTimeMillis();
//			System.out.println(serverName+" "+timeName+" Receive accept time = " + (stop-start)+ " ms");
			if(enroll) addToEnrollTiming(serverName+" "+timeName+" Enroll receive accept time", (stop-start));
			else addToTestTiming(serverName+" "+timeName+" Test receive accept time", (stop-start));
			
			start = System.currentTimeMillis();
			ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
			InterServerObjectWrapper receivedObject = (InterServerObjectWrapper) objIn.readObject();
			stop = System.currentTimeMillis();
//			System.out.println(serverName+" "+timeName+" Receive object stream & read time = " + (stop-start)+ " ms");
			if(enroll) addToEnrollTiming(serverName+" "+timeName+" Enroll receive object stream & read time", (stop-start));
			else addToTestTiming(serverName+" "+timeName+" Test receive object stream & read time", (stop-start));
			
			if (receivedObject.getOrigin() != null && receivedObject.getOrigin().equals("getEnrollTiming")) {
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				InterServerObjectWrapper toC = new InterServerObjectWrapper();
				toC.setContents(enrollTiming);
				out.writeObject(toC);
			}
			else if (receivedObject.getOrigin() != null && receivedObject.getOrigin().equals("getTestTiming")) {
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				InterServerObjectWrapper toC = new InterServerObjectWrapper();
				toC.setContents(testTiming);
				out.writeObject(toC);
			}
			
			client.close();
			return receivedObject;
		}catch (Exception e) {
			e.printStackTrace();
		}
		long t1 = System.currentTimeMillis();
//		System.out.println(serverName+" "+timeName+" Total receive time = "+ (t1 -t0)+ " ms");
		if(enroll) addToEnrollTiming(serverName+" "+timeName+" Enroll Total receive time", (t1-t0));
		else addToTestTiming(serverName+" "+timeName+" Test Total receive time", (t1-t0));
		return null;
	}

	protected void send(String ip, int port, InterServerObjectWrapper message, boolean enroll, String timeName){
		long t0 = System.currentTimeMillis();
		try {
			long start = System.currentTimeMillis();
			Socket socket = new Socket(InetAddress.getByName(ip), port);
			long stop = System.currentTimeMillis();
//			System.out.println(serverName+" "+timeName+" Send connect time = " + (stop-start)+ " ms");
			if(enroll) addToEnrollTiming(serverName+" "+timeName+" Enroll Send connect time", (stop-start));
			else addToTestTiming(serverName+" "+timeName+" Test Send connect time", (stop-start));
			start = System.currentTimeMillis();
			ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
			objOutput.writeObject(message);
			stop = System.currentTimeMillis();
//			System.out.println(serverName+" "+timeName+" Send stream & write time = " + (stop-start)+ " ms");
			if(enroll) addToEnrollTiming(serverName+" Enroll Send stream & write time", (stop-start));
			else addToTestTiming(serverName+" "+timeName+" Test Send stream & write time", (stop-start));
			socket.close();
		}catch(ConnectException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		long t1 = System.currentTimeMillis();
//		System.out.println(serverName+" "+timeName+" Total send time = "+ (t1 -t0)+ " ms");
		if(enroll) addToEnrollTiming(serverName+" "+timeName+" Enroll Total send time", (t1-t0));
		else addToTestTiming(serverName+" "+timeName+" Test Total send time", (t1-t0));
	}
	
	protected Set<BigInteger> multiEncrypt(BigInteger key, HashSet<BigInteger> messageSet) {
		ArrayList<BigInteger> messages = new ArrayList<BigInteger>(messageSet);
		Set<BigInteger> encryptions = Collections.synchronizedSet(new HashSet<BigInteger>());
		int cores = Runtime.getRuntime().availableProcessors();
		int numMessages = messages.size();
		int stride = numMessages/cores;
		
		//create all the threads
		ArrayList<EncryptThread> threads = new ArrayList<EncryptThread>();
		for (int thread = 0; thread < cores; thread++) {
			int start = thread*stride;
			//don't want to go over the end
			int stop = ((thread+1) == cores) ? messages.size()-1 : (thread+1)*stride;
			List<BigInteger> subList = messages.subList(start, stop);
			EncryptThread t = new EncryptThread(encryptionScheme.getP(), key, subList);
			t.start();
			threads.add(t);
		}
		
		for( EncryptThread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//pull out the encryptions
		for (EncryptThread thread : threads) {
			ArrayList<BigInteger> these = thread.getEncryptions();
			encryptions.addAll(these);
//			thread.finish();
//			while(!thread.isAlive());
//			System.out.println("Am I alive? "+thread.isAlive());

		}
		
		
		
		return encryptions;
		
	}
	//shifting case
	protected Set<BigInteger> multiEncrypt(BigInteger key, HashSet<BigInteger> messageSet, int shiftVal) {
		ArrayList<BigInteger> messages = new ArrayList<BigInteger>(messageSet);
		Set<BigInteger> encryptions = Collections.synchronizedSet(new HashSet<BigInteger>());
		int cores = Runtime.getRuntime().availableProcessors();
		int numMessages = messages.size();
		int stride = numMessages/cores;
		
		//create all the threads
		ArrayList<EncryptThread> threads = new ArrayList<EncryptThread>();
		int totalMessages = 0;
		for (int thread = 0; thread < cores; thread++) {
			int start = thread*stride;
			//don't want to go over the end
			int stop = ((thread+1) == cores) ? messages.size()-1 : (thread+1)*stride;
			List<BigInteger> subList = messages.subList(start, stop);

			totalMessages += subList.size();

			EncryptThread t = new EncryptThread(encryptionScheme.getP(), key, subList, shiftVal);

			t.start();
			threads.add(t);
			
		}
//		System.out.println("messages:"+ numMessages+", "+ totalMessages);
		//wait for them to finish
		for( EncryptThread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//pull out the encryptions
		for (EncryptThread thread : threads) {
//			System.out.println(thread.getMessages().size()+", "+thread.getEncryptions().size());
			encryptions.addAll(thread.getEncryptions());
		}
//		System.out.println(encryptions.size() + ", "+ totalMessages);
		
		return encryptions;
		
	}
	protected Set<BigInteger> multiDecrypt(BigInteger key, HashSet<BigInteger> messageSet) {
		ArrayList<BigInteger> messages = new ArrayList<BigInteger>(messageSet);
		Set<BigInteger> decryptions = Collections.synchronizedSet(new HashSet<BigInteger>());
		int cores = Runtime.getRuntime().availableProcessors();
		int numMessages = messages.size();
		int stride = numMessages/cores;
		
		//create all the threads
		ArrayList<DecryptThread> threads = new ArrayList<DecryptThread>();
		for (int thread = 0; thread < cores; thread++) {
			int start = thread*stride;
			//don't want to go over the end
			int stop = ((thread+1) == cores) ? messages.size()-1 : (thread+1)*stride;
			List<BigInteger> subList = messages.subList(start, stop);
			DecryptThread t = new DecryptThread(encryptionScheme.getP(), key, subList);
			t.start();
			threads.add(t);
		}
		for( DecryptThread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//pull out the decryptions
		for (int i=0; i <= threads.size()-1; i++) {
			DecryptThread thread = threads.get(i);
			decryptions.addAll(thread.getDecryptions());
//			thread.finish();
//			System.out.println("Am I alive? "+thread.isAlive());
		}
		return decryptions;
		
	}

	//shifting case
	protected Set<BigInteger> multiDecrypt(BigInteger key, HashSet<BigInteger> messageSet, boolean shift) {
		ArrayList<BigInteger> messages = new ArrayList<BigInteger>(messageSet);
		Set<BigInteger> decryptions = Collections.synchronizedSet(new HashSet<BigInteger>());
		int cores = Runtime.getRuntime().availableProcessors();
		int numMessages = messages.size();
		int stride = numMessages/cores;
		
		//create all the threads
		ArrayList<DecryptThread> threads = new ArrayList<DecryptThread>();
		for (int thread = 0; thread < cores; thread++) {
			int start = thread*stride;
			//don't want to go over the end
			int stop = ((thread+1) == cores) ? messages.size()-1 : (thread+1)*stride;
			List<BigInteger> subList = messages.subList(start, stop);
			DecryptThread t = new DecryptThread(encryptionScheme.getP(), key, subList, shift);
			t.start();
			threads.add(t);
		}
		for( DecryptThread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//pull out the decryptions
		for (int i=0; i <= threads.size()-1; i++) {
			DecryptThread thread = threads.get(i);
			decryptions.addAll(thread.getDecryptions());
//			thread.finish();
//			System.out.println("Am I alive? "+thread.isAlive());
		}
		return decryptions;
		
	}


	/*
	 * public void RSA() throws NoSuchAlgorithmException, IOException,
	 * InvalidKeySpecException, ClassNotFoundException, NoSuchPaddingException,
	 * InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
	 * 
	 * String encryptThis = "jacordar calculus"; byte[] keyS =
	 * encryptThis.getBytes(); MessageDigest sha =
	 * MessageDigest.getInstance("SHA-1"); keyS=sha.digest(keyS); keyS=
	 * Arrays.copyOf(keyS, 16); SecretKeySpec secretKeySpec = new
	 * SecretKeySpec(keyS,"DH"); System.out.println("Secret Key :" + new
	 * String(secretKeySpec.getEncoded(),"UTF-8"));
	 * 
	 * int port = 0; ServerSocket serv_socket = null; Socket client = null;
	 * serv_socket = new ServerSocket(0); port = serv_socket.getLocalPort();
	 * System.out.println(port);
	 * 
	 * client = serv_socket.accept(); System.out.println("Connected!  " );
	 * ObjectInputStream objInput = new
	 * ObjectInputStream(client.getInputStream()); PublicKey pk = (PublicKey)
	 * objInput.readObject(); System.out.println(pk.toString()); long
	 * startEncryption = System.currentTimeMillis(); Cipher cipher =
	 * Cipher.getInstance("DH"); cipher.init(Cipher.ENCRYPT_MODE,pk); byte[]
	 * encryptedKey = cipher.doFinal(secretKeySpec.getEncoded()); long
	 * finishEncryption = System.currentTimeMillis();
	 * System.out.println("Time to encrypt is " + (finishEncryption -
	 * startEncryption)); System.out.println("Key Encrypted With Public Key : "
	 * + new String(encryptedKey, "UTF-8")); DataOutputStream dataOut = new
	 * DataOutputStream(client.getOutputStream());
	 * dataOut.write(encryptedKey,0,encryptedKey.length);
	 * 
	 * }
	 * 
	 * public BigInteger encrypt(PublicKey publicKey, BigInteger minutia){
	 * byte[] encryptedKey = null; try{ Cipher cipher =
	 * Cipher.getInstance("DH"); cipher.init(Cipher.ENCRYPT_MODE,publicKey);
	 * encryptedKey = cipher.doFinal(minutia.toByteArray());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return new BigInteger(encryptedKey); }
	 * 
	 * public static void main(String[] args) throws Exception{ Server server =
	 * new Server(); server.RSA();
	 * 
	 * }
	 */

}
