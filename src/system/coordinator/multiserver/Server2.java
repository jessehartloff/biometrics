package system.coordinator.multiserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collection;

import javax.crypto.Cipher;

import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerTwoSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

import javax.crypto.KeyAgreement;

public class Server2 extends system.coordinator.Coordinator {

	private PublicKey _publicKey;
	private static PrivateKey _privateKey;
	private KeyAgreement _keyAgreement;
	private static InterServerObjectWrapper _receivedObject;
	private static InterServerObjectWrapper _objectToSend;
	
	Server2(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
	}



	
	

	public static BigInteger encrypt(Key key, BigInteger minutia, Cipher cipher){
		
		BigInteger encryptedMinutia = null;
		try{
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedMinutia = new BigInteger (cipher.doFinal(minutia.toByteArray()));
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return encryptedMinutia;
	}
	
	public static KeyPair generateKeyPair() throws Exception{
	
		    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDH", "BC");
		    EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(
		        "fffffffffffffffffffffffffffffffeffffffffffffffff", 16)), new BigInteger(
		        "fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
		        "fffffffffffffffffffffffffffffffefffffffffffffffc", 16));

		    ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(new BigInteger(
		        "fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
		        "fffffffffffffffffffffffffffffffefffffffffffffffc", 16)), new BigInteger(
		        "fffffffffffffffffffffffffffffffefffffffffffffffc", 16), 1);

		    keyGen.initialize(ecSpec, new SecureRandom());

		    KeyAgreement aKeyAgree = KeyAgreement.getInstance("ECDH", "BC");
		    KeyPair keyPair = keyGen.generateKeyPair();
		    
		    return keyPair;
		    
		    
		    
	}
	
	public static void main( String[] args ) throws Exception{
		
		_privateKey = generateKeyPair().getPrivate();

		int port = ServerTwoSettings.getInstance().portNumber().getValue().intValue();
		Socket S1 = new Socket(ServerOneSettings.getInstance().ip().getValue(), ServerOneSettings.getInstance().portNumber().getValue().intValue());
		ServerSocket serv_socket = null;
		Socket client = null;
		serv_socket = new ServerSocket(port);
		while(true){
			//port = serv_socket.getLocalPort();
			client = serv_socket.accept();

			ObjectInputStream objInput = new ObjectInputStream(client.getInputStream());
			InterServerObjectWrapper receivedObject = (InterServerObjectWrapper)objInput.readObject();

			Template receivedEncryptedFP = (Template) receivedObject.getContents(); 
			Cipher cipher = Cipher.getInstance("ECDH", "BC");
			for (BigInteger minutiaPoint : receivedEncryptedFP.getHashes() ){
				minutiaPoint = encrypt(_privateKey, minutiaPoint,cipher);
			}
			InterServerObjectWrapper objectToSend = receivedObject;
			objectToSend.setContents(receivedEncryptedFP);
			objectToSend.setOrigin("server 2");


			//		ObjectInputStream objInput = new ObjectInputStream(client.getInputStream());
			//		_receivedObject = (InterServerObjectWrapper)objInput.readObject();
			//		if (_receivedObject.isEnrolling()){
			//			enroll();
			//		}else{
			//			test();
			//		}

			// THIS NEEDS TO BE FIXED. I WILL DO THIS TOMORROW ie. Friday Morning
			// In short, this will be accidentally trying to send out data to the same 
			// file descriptor that it just received from ie. the client which is not what we
			// want //fixed
			ObjectOutputStream objOutput = new ObjectOutputStream(S1.getOutputStream());
			objOutput.writeObject(objectToSend);
		}

		
	}
	
//	public static void enroll(){
//		test();
//	}
//	
//	public static void test(){
//		Template receivedEncryptedFP = (Template)_receivedObject.getContents(); 
//		for (BigInteger minutiaPoint : receivedEncryptedFP.getHashes() ){
//			minutiaPoint = encrypt(_privateKey, minutiaPoint);
//		}
//		_objectToSend = new InterServerObjectWrapper();
//		_objectToSend.setContents(receivedEncryptedFP);
//	}






	@Override
	public RawScores run() {
		// TODO Auto-generated method stub
		return null;
	}

}
