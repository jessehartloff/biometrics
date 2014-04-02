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
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

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

	
	private PrivateKey _privateKey;
	private InterServerObjectWrapper _receivedObject;
	private InterServerObjectWrapper _objectToSend;
	
	Server2(Hasher hasher, Users enrollees) {
		
		super(hasher, enrollees);
	}


	// FIX SERVER2 and DO THE HASHMAP FOR FUZZY VAULT 
	// testing 
	
	

	public BigInteger encrypt(Key key, BigInteger minutia, Cipher cipher){
		
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
	
	public KeyPair generateKeyPair() throws Exception{
	
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
	
	public void initialize(  ) throws Exception{
		
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
			_receivedObject = (InterServerObjectWrapper)objInput.readObject();
			
			if (_receivedObject.isEnrolling()){
					enroll();
			}else{
					test();
			}
			ObjectOutputStream objOutput = new ObjectOutputStream(S1.getOutputStream());
			objOutput.writeObject(_objectToSend);
		}

		
	}
	
	public void enroll() throws Exception{
		Cipher cipher = Cipher.getInstance("ECDH", "BC");
		Template receivedEncryptedFP = (Template)_receivedObject.getContents(); 
		for (BigInteger minutiaPoint : receivedEncryptedFP.getHashes() ){
			minutiaPoint = encrypt(_privateKey, minutiaPoint, cipher);
		}
		_objectToSend = new InterServerObjectWrapper();
		_objectToSend.setContents(receivedEncryptedFP);
		_objectToSend.setOrigin("server 2");
	}
	
	public void test() throws Exception{
		Cipher cipher = Cipher.getInstance("ECDH","BH");
		ArrayList<Template> fingerprintList = (ArrayList<Template>)_receivedObject.getContents();
		for (Template fingerprint : fingerprintList){
			for (BigInteger minutiaPoint : fingerprint.getHashes()){
				minutiaPoint = encrypt(_privateKey, minutiaPoint,cipher);
			}
		}
		_objectToSend = new InterServerObjectWrapper();
		_objectToSend.setContents(fingerprintList);
		_objectToSend.setOrigin("server 2");
		
	}






	@Override
	public RawScores run() {
		// TODO Auto-generated method stub
		
		
		return null;
	}

}
