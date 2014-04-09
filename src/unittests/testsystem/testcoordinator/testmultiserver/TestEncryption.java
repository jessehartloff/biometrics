package unittests.testsystem.testcoordinator.testmultiserver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.security.KeyPair;

import org.junit.Test;

import system.coordinator.multiserver.EncryptionScheme;

public class TestEncryption {

	@Test
	public void testEncryption() {
		EncryptionScheme scheme = new EncryptionScheme();
		KeyPair keys = scheme.generateKeyPair();
		
		BigInteger message = BigInteger.valueOf(543721895623497857L);
		BigInteger cryptoText = scheme.encrypt(keys.getPublic(), message);
		BigInteger decryptedMessage = scheme.decrypt(keys.getPrivate(), cryptoText);

		assertFalse("\nmessage: " + message + "\nencrypted: " + cryptoText, message.equals(cryptoText));
		assertTrue("\nmessage: " + message + "\ndecrypted: " + decryptedMessage, message.equals(decryptedMessage));
	}

	
	
	@Test
	public void testCommutativeEncryption() {
		EncryptionScheme scheme = new EncryptionScheme();
		KeyPair keys = scheme.generateKeyPair();
		KeyPair keys2 = scheme.generateKeyPair();
		
		BigInteger message = BigInteger.valueOf(12645634563257L);
		BigInteger cryptoText = scheme.encrypt(keys.getPublic(), message);
		BigInteger doubleCryptoText = scheme.encrypt(keys2.getPublic(), cryptoText);
		BigInteger storedMessage = scheme.decrypt(keys.getPrivate(), doubleCryptoText);
		
		BigInteger noDoubleEncrypt = scheme.encrypt(keys2.getPublic(), message);

		assertTrue("\nCommutative: " + storedMessage + "\nDirect: " + noDoubleEncrypt, storedMessage.equals(noDoubleEncrypt));
	}
	
}
