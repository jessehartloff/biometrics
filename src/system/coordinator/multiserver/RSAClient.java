package system.coordinator.multiserver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAClient {
	public void RSAKeyExchange() throws NoSuchAlgorithmException, UnknownHostException, IOException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		
		long startTime = System.currentTimeMillis();
		
		//Symmetric Key Distribution Using Asymmetric Encryption
		KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
		keyGenerator.initialize(1024);
		KeyPair rsaKeys = keyGenerator.generateKeyPair();
		PublicKey publicKey = rsaKeys.getPublic();
		String publicKeyString = publicKey.toString();
		PrivateKey privateKey = rsaKeys.getPrivate();
		long keyGenTime = System.currentTimeMillis() - startTime; 
		System.out.println("Took "+ keyGenTime + " milliseconds to generate key pair ");
		
		System.out.println("Public key is " + publicKeyString);
		//System.out.println("Length of public key is :" + publicKeyString.length());
		
		
		Socket clnt_socket;
		int port = 58063;
		clnt_socket = new Socket(InetAddress.getLocalHost(),port);
		ObjectOutputStream objOut = new ObjectOutputStream(clnt_socket.getOutputStream());
		objOut.writeObject(publicKey);
		
		byte[] inputBuffer = new byte[128];
		BufferedInputStream inputStream = new BufferedInputStream(clnt_socket.getInputStream());
		inputStream.read(inputBuffer,0,128);
		System.out.println("Received Encrypted Key :" +new String(inputBuffer, "UTF-8"));
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedKey = cipher.doFinal(inputBuffer);
		System.out.println("Decrypted Key :" +new String(decryptedKey, "UTF-8"));
		
		long finishTime = System.currentTimeMillis();
		
		System.out.println("Time For RSA Key Exchange Was : " + (finishTime-startTime) + " milliseconds");
		
	}
	public static void main ( String[] args){
		RSAClient client = new RSAClient();
		try {
			client.RSAKeyExchange();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
