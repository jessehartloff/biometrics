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
		try{
			
			Socket client = serverSocket.accept();
			ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
			InterServerObjectWrapper receivedObject = (InterServerObjectWrapper) objIn.readObject();
			client.close();
			return receivedObject;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void send(String ip, int port, InterServerObjectWrapper message){
		try {
			Socket socket = new Socket(InetAddress.getByName(ip), port);
			ObjectOutputStream objOutput = new ObjectOutputStream(socket.getOutputStream());
			objOutput.writeObject(message);
			socket.close();
		}catch(ConnectException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
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
		//pull out the encryptions
		synchronized(encryptions){
			for (int i=0; i < threads.size()-1; i++) {
				EncryptThread thread = threads.get(i);
				ArrayList<BigInteger> these = thread.getEncryptions();
				encryptions.addAll(these);
				thread.finish();
			}
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
		//pull out the encryptions
		synchronized(encryptions){
		for (int i=0; i < threads.size()-1; i++) {
			EncryptThread thread = threads.get(i);
			encryptions.addAll(thread.getEncryptions());
			thread.finish();
		}
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
		//pull out the decryptions
		synchronized(decryptions){
		for (int i=0; i < threads.size()-1; i++) {
			DecryptThread thread = threads.get(i);
			decryptions.addAll(thread.getDecryptions());
			thread.finish();
		}
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
		//pull out the decryptions
		synchronized(decryptions){
		for (int i=0; i < threads.size()-1; i++) {
			DecryptThread thread = threads.get(i);
			decryptions.addAll(thread.getDecryptions());
			thread.finish();
		}
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
