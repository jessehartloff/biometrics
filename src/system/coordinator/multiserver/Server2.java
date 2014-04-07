package system.coordinator.multiserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;

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




import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerTwoSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Server2 extends system.coordinator.Coordinator {

	
	private PrivateKey _privateKey;
	private InterServerObjectWrapper _receivedObject;
	private InterServerObjectWrapper _objectToSend;
	private HashMap<Long, Key> keyMap;
	
	public Server2(Hasher hasher, Users enrollees) {
		
		super(hasher, enrollees);

	}


	// FIX SERVER2 and DO THE HASHMAP FOR FUZZY VAULT 
	// testing 
	
	

	public BigInteger encrypt(Key key, BigInteger minutia, Cipher cipher){
		
		BigInteger encryptedMinutia = null;
		try{
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			encryptedMinutia = new BigInteger (cipher.doFinal(minutia.toByteArray()));
		    System.out.println("Encrypted!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return encryptedMinutia;
	}
	
	public KeyPair generateKeyPair() throws Exception{
		    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECIES", "BC");
		    System.out.println("Generated key pair");
		    EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(
		        "fffffffffffffffffffffffffffffffeffffffffffffffff", 16)), new BigInteger(
		        "fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
		        "fffffffffffffffffffffffffffffffefffffffffffffffc", 16));

		    ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(new BigInteger(
		    		"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
		        "fffffffffffffffffffffffffffffffefffffffffffffffc", 16)), new BigInteger(
		        "fffffffffffffffffffffffffffffffefffffffffffffffc", 16), 1);

		    keyGen.initialize(ecSpec, new SecureRandom());

		    //KeyAgreement aKeyAgree = KeyAgreement.getInstance("ECDH", "BC");
		    KeyPair keyPair = keyGen.generateKeyPair();
		    
		    return keyPair;
		    
		    
		    
	}
	
	/*
	public void initialize() throws Exception{
		
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
	*/
	
	
	
	public InterServerObjectWrapper testEnroll(InterServerObjectWrapper receivedObject, PrivateKey privateKey) throws Exception{
		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		Template receivedEncryptedFP = (Template)receivedObject.getContents(); 
		for (BigInteger minutiaPoint : receivedEncryptedFP.getHashes() ){
			minutiaPoint = encrypt(privateKey, minutiaPoint, cipher);
		}
		InterServerObjectWrapper objectToSend = new InterServerObjectWrapper();
		objectToSend.setContents(receivedEncryptedFP);
		objectToSend.setOrigin("server 2");
		return objectToSend;
	}
	
	public InterServerObjectWrapper testTest(InterServerObjectWrapper receivedObject, PrivateKey privateKey) throws Exception{
		Cipher cipher = Cipher.getInstance("ECIES","BH");
		ArrayList<Template> fingerprintList = (ArrayList<Template>)receivedObject.getContents();
		for (Template fingerprint : fingerprintList){
			for (BigInteger minutiaPoint : fingerprint.getHashes()){
				minutiaPoint = encrypt(_privateKey, minutiaPoint,cipher);
			}
		}
		InterServerObjectWrapper objectToSend = new InterServerObjectWrapper();
		objectToSend.setContents(fingerprintList);
		objectToSend.setOrigin("server 2");
		return objectToSend;
	}
	
	public void initialize() throws Exception{
		_privateKey = generateKeyPair().getPrivate();
		keyMap = new HashMap<Long, Key>();
		int port = ServerTwoSettings.getInstance().portNumber().getValue().intValue();
		Socket S1 = new Socket(InetAddress.getByName(ServerOneSettings.getInstance().ip().getValue()),
								ServerOneSettings.getInstance().portNumber().getValue().intValue());
		ServerSocket serv_socket = new ServerSocket(port);
		Socket client = null;
		
		int state = 1; 
		while (true) {
			switch (state){
			case 1:
				System.out.println("Server 2 listening....");

				client = serv_socket.accept();
				ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
				_receivedObject = (InterServerObjectWrapper) objIn.readObject();
				if (_receivedObject.isEnrolling()){
					state = 2;
				}else {
					state = 3;
				}
				break;
			case 2:
				enroll(_receivedObject, _privateKey);
				ObjectOutputStream objOutputEnroll = new ObjectOutputStream(S1.getOutputStream());
				objOutputEnroll.writeObject(_objectToSend);
				state = 1;
				break;
			case 3:
				test(_receivedObject, _privateKey);
				ObjectOutputStream objOutputTest = new ObjectOutputStream(S1.getOutputStream());
				objOutputTest.writeObject(_objectToSend);
				state = 1;
				break;
			}
				
		}
		
		// THIS NEEDS TO BE FIXED. I WILL DO THIS TOMORROW ie. Friday Morning 
		// In short, this will be accidentally trying to send out data to the same 
		// file descriptor that it just received from ie. the client which is not what we
		// want
//		ObjectOutputStream objOutput = new ObjectOutputStream(client.getOutputStream());
//		objOutput.writeObject(_objectToSend);
		
		
		

	}
	
	public void enroll(InterServerObjectWrapper receivedObject, Key privateKey) throws Exception{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		System.out.println("adding bouncy castle");
		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		System.out.println("got the cipher!");
		System.out.println(receivedObject.getUserID());
		System.out.println(privateKey);
		this.keyMap.put(receivedObject.getUserID(), privateKey);
		Template receivedEncryptedFP = (Template) receivedObject.getContents(); 
		for (BigInteger minutiaPoint : receivedEncryptedFP.getHashes() ){
			minutiaPoint = encrypt(privateKey, minutiaPoint, cipher);
		}
		_objectToSend = new InterServerObjectWrapper();
		_objectToSend.setContents(receivedEncryptedFP);
		_objectToSend.setOrigin("server 2");
	}
	
	public void test(InterServerObjectWrapper receivedObject, Key privateKey) throws Exception{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		Cipher cipher = Cipher.getInstance("ECIES","BC");
		ArrayList<Template> fingerprintList = (ArrayList<Template>)receivedObject.getContents();
		for (Template fingerprint : fingerprintList){
			for (BigInteger minutiaPoint : fingerprint.getHashes()){
				minutiaPoint = encrypt(privateKey, minutiaPoint,cipher);
			}
		}
		_objectToSend = new InterServerObjectWrapper();
		_objectToSend.setContents(fingerprintList);
		_objectToSend.setOrigin("server 2");
		
	}






	@Override
	public RawScores run() {
		// TODO Auto-generated method stub
		// call intitialize 
		try {
			initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
