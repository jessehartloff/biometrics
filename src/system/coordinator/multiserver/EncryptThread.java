package system.coordinator.multiserver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class EncryptThread extends Thread{
	/**
	 * Member variables
	 */
	private EncryptionScheme encryptionScheme;
	private ArrayList<BigInteger> encryptions;
	private BigInteger key;
	private List<BigInteger> messages;
//	private boolean wait;
//	private boolean lock;
	private boolean shift; //marks points at server 2
	private int shiftVal;
	
	/**
	 * Constructor
	 */
	public EncryptThread(BigInteger prime, BigInteger key, List<BigInteger> messages) {
		//need a new encryption scheme object for each thread
		encryptionScheme = new EncryptionScheme();
		encryptions = new ArrayList<BigInteger>();
		this.key = key;
		this.setMessages(messages);
//		wait = true;
		shift = false;
	}
	
	public EncryptThread(BigInteger prime, BigInteger key, List<BigInteger> messages, int shiftVal) {
		//need a new encryption scheme object for each thread
		encryptionScheme = new EncryptionScheme();
		encryptions = new ArrayList<BigInteger>();
		this.key = key;
		this.setMessages(messages);
//		wait = true;
		this.shift = true;
		this.shiftVal = shiftVal;
		
	}

	@Override
	public void run() {
//		System.out.println("Yo I'm ready to encrypt stuff");
		encryptMessages(key, getMessages());
//		while(wait);
	}
	
	
	/**
	 * Used to encrypt a set of messages
	 * @param key
	 * @param messages
	 * @return
	 */
	public void encryptMessages(BigInteger key, List<BigInteger> messages) {
//		System.out.println("Encrypting stuff");
//		lock = true;
		for (BigInteger m : messages) {
//			System.out.println("m "+m);
			if(!shift) m = encryptionScheme.encodeMessage(m); // encode at client
//			System.out.println("m "+m);

			BigInteger em = encryptionScheme.encrypt(m, key);
			//deals with when we have to mark chaff at S2
			if (shift) {
				em.shiftLeft(1);
				if(shiftVal != 0) em.add(BigInteger.ONE);
			}
			encryptions.add(m);
		}
//		lock = false;
	}

	public ArrayList<BigInteger> getEncryptions() {
//		while(lock);
		return encryptions;
	}
//	public void finish() {
//		wait = false;
//	}

	public List<BigInteger> getMessages() {
		return messages;
	}

	public void setMessages(List<BigInteger> messages) {
		this.messages = messages;
	}
}
