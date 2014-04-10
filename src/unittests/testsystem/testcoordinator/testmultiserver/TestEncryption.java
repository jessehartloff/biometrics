package unittests.testsystem.testcoordinator.testmultiserver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.security.KeyPair;
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
		
//		for(int i=0; i<10000; i++){
//			BigInteger cryptoText = scheme.encrypt(keys.getPublic(), message);
//			BigInteger decryptedMessage = scheme.decrypt(keys.getPrivate(), cryptoText);
//		}

		BigInteger cryptoText = scheme.encrypt(keys.getPublic(), message);
		BigInteger decryptedMessage = scheme.decrypt(keys.getPrivate(), cryptoText);

//		System.out.println(message.modPow(keys.getPrivate(), scheme.getPrime()).modPow(keys.getPublic(), scheme.getPrime()) );
//		System.out.println(message.modPow(keys.getPublic(), scheme.getPrime()).modPow(keys.getPrivate(), scheme.getPrime()) );
//		
//		
//		System.out.println(keys.getPublic().multiply(keys.getPrivate()).mod(scheme.getPrime()) );
		
		assertFalse("\nmessage: " + message + "\nencrypted: " + cryptoText, message.equals(cryptoText));
		assertTrue("\nmessage: " + message + "\ndecrypted: " + decryptedMessage, message.equals(decryptedMessage));
	}

	
	
	@Test
	public void testCommutativeEncryption() {
		EncryptionScheme scheme = new EncryptionScheme();
		SimpleKeyPair keys = scheme.generateKeyPair();
		SimpleKeyPair keys2 = scheme.generateKeyPair();
		
		BigInteger message = BigInteger.valueOf(12645634563257L);
		BigInteger cryptoText = scheme.encrypt(keys.getPublic(), message);
		BigInteger doubleCryptoText = scheme.encrypt(keys2.getPublic(), cryptoText);
		BigInteger storedMessage = scheme.decrypt(keys.getPrivate(), doubleCryptoText);
		
		BigInteger noDoubleEncrypt = scheme.encrypt(keys2.getPublic(), message);

		assertTrue("\nCommutative: " + storedMessage + "\nDirect: " + noDoubleEncrypt, storedMessage.equals(noDoubleEncrypt));
	}
	
}
