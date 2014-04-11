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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import system.allcommonclasses.commonstructures.Users;
import system.coordinator.Coordinator;
import system.hasher.Hasher;

public abstract class Server extends Coordinator {
	protected EncryptionScheme encryptionScheme;
	
	public Server(Hasher hasher, Users users) {
		super(hasher, users);
		encryptionScheme = new EncryptionScheme();
	}

	// base server class extends coordinator

	protected InterServerObjectWrapper receive(ServerSocket serverSocket){
		long t0 = System.currentTimeMillis();
		try{
			long start = System.currentTimeMillis();
			Socket client = serverSocket.accept();
			long stop = System.currentTimeMillis();
			System.out.println("Receive accept time = " + (stop-start)+ " ms");
			start = System.currentTimeMillis();
			ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
			InterServerObjectWrapper receivedObject = (InterServerObjectWrapper) objIn.readObject();
			stop = System.currentTimeMillis();
			System.out.println("Receive object stream & read time = " + (stop-start)+ " ms");

			client.close();
			return receivedObject;
		}catch (Exception e) {
			e.printStackTrace();
		}
		long t1 = System.currentTimeMillis();
		System.out.println("Total receive time = "+ (t1 -t0)+ " ms");
		return null;
	}

	protected void send(String ip, int port, InterServerObjectWrapper message){
		long t0 = System.currentTimeMillis();
		try {
			long start = System.currentTimeMillis();
			Socket socket = new Socket(InetAddress.getByName(ip), port);
			long stop = System.currentTimeMillis();
			System.out.println("Send connect time = " + (stop-start)+ " ms");
			start = System.currentTimeMillis();
			ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
			objOutput.writeObject(message);
			stop = System.currentTimeMillis();
			System.out.println("Send stream & write time = " + (stop-start)+ " ms");
			socket.close();
		}catch(ConnectException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		long t1 = System.currentTimeMillis();
		System.out.println("Total send time = "+ (t1 -t0)+ " ms");
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
			int stop = ((thread+1)*stride >= messages.size()) ? messages.size()-1 : (thread+1)*stride;
			List<BigInteger> subList = messages.subList(start, stop);
			EncryptThread t = new EncryptThread(encryptionScheme.getPrime(), key, subList);
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
		for (int i=0; i < threads.size()-1; i++) {
			EncryptThread thread = threads.get(i);
			ArrayList<BigInteger> these = thread.getEncryptions();
			encryptions.addAll(these);
//			thread.finish();
//			while(!thread.isAlive());
			System.out.println("Am I alive? "+thread.isAlive());

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
		for (int thread = 0; thread < cores; thread++) {
			int start = thread*stride;
			//don't want to go over the end
			int stop = ((thread+1)*stride >= messages.size()) ? messages.size()-1 : (thread+1)*stride;
			List<BigInteger> subList = messages.subList(start, stop);
			EncryptThread t = new EncryptThread(encryptionScheme.getPrime(), key, subList, shiftVal);
			t.start();
			threads.add(t);
			
		}
		//wait for them to finish
		for( EncryptThread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//pull out the encryptions
		for (int i=0; i < threads.size()-1; i++) {
			EncryptThread thread = threads.get(i);
			encryptions.addAll(thread.getEncryptions());
		}
		
		
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
			int stop = ((thread+1)*stride >= messages.size()) ? messages.size()-1 : (thread+1)*stride;
			List<BigInteger> subList = messages.subList(start, stop);
			DecryptThread t = new DecryptThread(encryptionScheme.getPrime(), key, subList);
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
		for (int i=0; i < threads.size()-1; i++) {
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
			int stop = ((thread+1)*stride >= messages.size()) ? messages.size()-1 : (thread+1)*stride;
			List<BigInteger> subList = messages.subList(start, stop);
			DecryptThread t = new DecryptThread(encryptionScheme.getPrime(), key, subList, shift);
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
		for (int i=0; i < threads.size()-1; i++) {
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
