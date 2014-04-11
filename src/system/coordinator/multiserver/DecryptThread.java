package system.coordinator.multiserver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DecryptThread extends Thread{
	/**
	 * Member variables
	 */
	private EncryptionScheme encryptionScheme;
	private ArrayList<BigInteger> decryptions;
	private BigInteger key;
	private List<BigInteger> messages;
	private boolean wait;
	private boolean lock;
	private boolean shift;
	
	/**
	 * Constructor
	 */
	public DecryptThread(BigInteger prime, BigInteger key, List<BigInteger> messages) {
		//need a new encryption scheme object for each thread
		encryptionScheme = new EncryptionScheme(prime);
		decryptions = new ArrayList<BigInteger>();
		this.key = key;
		this.messages = messages;
		wait = true;
		shift = false;
	}
	public DecryptThread(BigInteger prime, BigInteger key, List<BigInteger> messages, boolean shift) {
		//need a new encryption scheme object for each thread
		encryptionScheme = new EncryptionScheme(prime);
		decryptions = new ArrayList<BigInteger>();
		this.key = key;
		this.messages = messages;
		wait = true;
		this.shift = shift;
	}
	

	@Override
	public void run() {
		System.out.println("Yo I'm ready to decrypt stuff");
		decryptMessages(key, messages);
		while(wait);
	}

	
	/**
	 * Used to decrypt a set of encrypted messages
	 * @param key
	 * @param messages
	 * @return
	 */
	//NOTE IF WE ARE SHIFTING THEY WILL ONLY BE GENUINES SINCE WE DON'T DECRYPT CHAFF
	public void decryptMessages(BigInteger key, List<BigInteger> messages) {
		System.out.println("Decrypting stuff");
		lock = true;
		for (BigInteger dm : messages) {
			if(shift) dm.shiftRight(1);
			BigInteger m = encryptionScheme.decrypt(key, dm);
			if(shift) m.shiftLeft(1);
			decryptions.add(m);
		}
		lock = false;
	}
	public ArrayList<BigInteger> getDecryptions() {
		while(lock);
		return decryptions;
	}
	public void finish() {
		wait = false;
	}
}
