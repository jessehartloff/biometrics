package unittests.testmultiserver;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

import system.coordinator.multiserver.EncryptionScheme;
import system.coordinator.multiserver.SimpleKeyPair;

public class TestEncryption {

	@Test
	public void testEncryption() {
		EncryptionScheme scheme = new EncryptionScheme();
		SimpleKeyPair keys = scheme.generateKeyPair();
		
		BigInteger message = new BigInteger(150, new Random());

		BigInteger messagePoint = scheme.encodeMessage(message);
		
		BigInteger cryptoText = scheme.encrypt(messagePoint, keys.getPrivate());
		BigInteger decryptedMessage = scheme.encrypt(cryptoText, keys.getPublic());
		
		assertTrue("\nmessage: " + message + "\ndecrypted: " + decryptedMessage, 
				messagePoint.equals(decryptedMessage));
		
	}


	@Test
	public void testEncodeAndEncrypt() {
		EncryptionScheme scheme = new EncryptionScheme();
		SimpleKeyPair keys = scheme.generateKeyPair();
		
		BigInteger message = new BigInteger(150, new Random());

		BigInteger messagePoint = scheme.encodeMessage(message);
		BigInteger cryptoText = scheme.encrypt(messagePoint, keys.getPrivate());
		
		BigInteger cryptoText2 = scheme.encodeAndEncrypt(message, keys.getPrivate());
		
		assertTrue("\nencrypted seperate: " + cryptoText + "\nencoded and encrypt: " + cryptoText2, 
				cryptoText.equals(cryptoText2));
		
	}

	
	
	
	@Test
	public void testCommutativeEncryption() {
		EncryptionScheme scheme = new EncryptionScheme();
		SimpleKeyPair keys = scheme.generateKeyPair();
		SimpleKeyPair keys2 = scheme.generateKeyPair();
		
		BigInteger message = new BigInteger(150, new Random());
		
		BigInteger messagePoint = scheme.encodeMessage(message);
		
		for(int i=0; i<250; i++){
			BigInteger cryptoText = scheme.encrypt(messagePoint, keys.getPublic());
			BigInteger doubleCryptoText = scheme.encrypt(cryptoText, keys2.getPublic());
			BigInteger storedMessage = scheme.encrypt(doubleCryptoText, keys.getPrivate());

			BigInteger noDoubleEncrypt = scheme.encrypt(messagePoint, keys2.getPublic());
		}

		BigInteger cryptoText = scheme.encrypt(messagePoint, keys.getPublic());
		BigInteger doubleCryptoText = scheme.encrypt(cryptoText, keys2.getPublic());
		BigInteger storedMessage = scheme.encrypt(doubleCryptoText, keys.getPrivate());
		
		BigInteger noDoubleEncrypt = scheme.encrypt(messagePoint, keys2.getPublic());

		assertTrue("\nCommutative: " + storedMessage + "\nDirect: " + noDoubleEncrypt, 
				storedMessage.equals(noDoubleEncrypt)
				);
	}
	
}
