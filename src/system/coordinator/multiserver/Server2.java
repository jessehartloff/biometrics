package system.coordinator.multiserver;

//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.math.BigInteger;
//import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.PrivateKey;
import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.Security;
//import java.security.spec.ECFieldFp;
//import java.security.spec.ECParameterSpec;
//import java.security.spec.ECPoint;
//import java.security.spec.EllipticCurve;
import java.util.ArrayList;
import java.util.HashMap;

//import javax.crypto.Cipher;
//import javax.crypto.NoSuchPaddingException;

import java.util.HashSet;

import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerTwoSettings;
//import settings.hashersettings.AllHasherSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;
//import system.hasher.HasherFactory.HasherEnumerator;
import system.quantizer.Quantizer;

public class Server2 extends Server {

	
	private BigInteger publicKey; //FIXME use different key for each user
	private InterServerObjectWrapper receivedObject;
//	private InterServerObjectWrapper objectToSend;
//	private Socket S1;
	private HashMap<Long, BigInteger> keyMap;
	
	public Server2(Hasher hasher, Users enrollees) {
		
		super(hasher, enrollees);

	}
	
	public void initialize() throws Exception{
		SimpleKeyPair keyPair = encryptionScheme.generateKeyPair();
		this.publicKey = keyPair.getPublic();
		this.keyMap = new HashMap<Long, BigInteger>(); //userID to encryption key
		int port = ServerTwoSettings.getInstance().portNumber().getValue().intValue();
		
		ServerSocket serverSocket = new ServerSocket(port);
		Socket client = null;
		long start;
		long stop;
		int state = 1; 
		while (true) {
//			System.out.println("State:"+state);

			switch (state){
			case 1:
				System.out.println("Server 2 listening....");
//
//				client = fromS1.accept();
//				System.out.println("Server 2 has accepted the client");
//				ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
//				System.out.println("S2 inStream made");
//				_receivedObject = (InterServerObjectWrapper) objIn.readObject();
//				System.out.println("S2 object read");
//				System.out.println(_receivedObject.getOrigin());
//				System.out.println(_receivedObject.getContents());
//
//				System.out.println(_receivedObject.isEnrolling());
//				System.out.println(_receivedObject.getUserID());

				receivedObject = receive(serverSocket, false, "IGNORE THIS");
				if (receivedObject.getOrigin().equals("getEnrollTiming") || receivedObject.getOrigin().equals("getTestTiming")) {
					state = 1;
				} else if (receivedObject.isEnrolling()){
					state = 2;
				}else {
					state = 3;
				}
				break;
			case 2: //enroll
//				S1 = new Socket(InetAddress.getByName(ServerOneSettings.getInstance().ip().getValue()),
//						ServerOneSettings.getInstance().portNumber().getValue().intValue());
				start = System.currentTimeMillis();
				InterServerObjectWrapper encryptedBiometric = enroll(receivedObject, publicKey);
				stop = System.currentTimeMillis();
				addToEnrollTiming("Server 2 enroll time", (stop-start));
				send(ServerOneSettings.getInstance().ip().getValue(),
						ServerOneSettings.getInstance().portNumber().getValue().intValue(),
						encryptedBiometric, true, "Enroll template");
				state = 1;
				break;
			case 3: //test
				start = System.currentTimeMillis();
				InterServerObjectWrapper encryptedBiometrics = test(receivedObject, publicKey);
				stop = System.currentTimeMillis();
				addToEnrollTiming("Server 2 test all time", (stop-start));
				send(ServerOneSettings.getInstance().ip().getValue(),
						ServerOneSettings.getInstance().portNumber().getValue().intValue(),
						encryptedBiometrics, false, "test templates");
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
	
	public InterServerObjectWrapper enroll(InterServerObjectWrapper receivedObject, BigInteger publicKey) throws Exception{
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//		System.out.println("adding bouncy castle");
//		Cipher cipher = Cipher.getInstance("ECIES", "BC");
//		System.out.println("got the cipher!");
		//store this users key
		this.keyMap.put(receivedObject.getUserID(), publicKey);
		Template receivedEncryptedFP = (Template) receivedObject.getContents(); 
//		System.out.println(receivedEncryptedFP);
		
		//encrypt genuines and mark with a 0 at the end
//		for(BigInteger feature : receivedEncryptedFP.getHashes() ){
//			feature = encryptionScheme.encrypt(publicKey, feature);
//			feature = feature.shiftLeft(1); //mark the genuines for chaff injection
//		}
		Template outGoingFV = new Template();
		long start = System.currentTimeMillis();
		System.out.println("S2 enroll fp size: "+receivedEncryptedFP.getHashes().size());
		outGoingFV.getHashes().addAll(multiEncrypt(publicKey, receivedEncryptedFP.getHashes(), false)); 
//		outGoingFV.addAll(receivedEncryptedFP.getHashes());
		long stop = System.currentTimeMillis();
		addToEnrollTiming("Server 2 multiEncrypt gen time", (stop-start));
		//add in chaff points (need to be encrypted at S2)
		//chaff points are marked with a 1 as the least significant bit

//		HashSet<BigInteger> chaff = new HashSet<BigInteger>();
		
//		start = System.currentTimeMillis();
//		long numberOfChaffPoints = ServerTwoSettings.getInstance().chaffPoints().getValue();
//		System.out.println("number of chaff: " + numberOfChaffPoints);
//		for(int i=0; i<numberOfChaffPoints; i++){ 
//		while(chaff.size() < numberOfChaffPoints){
//			chaff.add(Quantizer.getQuantizer().getRandomBigInt());
////			BigInteger c = Quantizer.getQuantizer().getRandomBigInt();
////			c = encryptionScheme.encrypt(publicKey, c);
////			c = c.shiftLeft(1).add(BigInteger.ONE); //mark as chaff for chaff injection
////			chaff.add(c);
//		}
		
//		System.out.println("actual number of chaff: " + chaff.size());
//		
//		outGoingFV.getHashes().addAll(multiEncrypt(publicKey, chaff, false)); 
//
//		System.out.println("template size: " + outGoingFV.getHashes().size());

//		stop = System.currentTimeMillis();
//		addToEnrollTiming("Server 2 generate and multiEcrypt chaff time", (stop-start));
//		receivedEncryptedFP.setHashes(outGoingFV);
		//send out the fv
		InterServerObjectWrapper objectToSend = new InterServerObjectWrapper();
		objectToSend.setContents(outGoingFV);
		objectToSend.setOrigin("server 2");
		objectToSend.setEnrolling(true);
		objectToSend.setUserID(receivedObject.getUserID());
		return objectToSend;
	}
	
	public InterServerObjectWrapper test(InterServerObjectWrapper receivedObject, BigInteger publicKey){
		long start = System.currentTimeMillis();
		ArrayList<Template> templates = (ArrayList<Template>) receivedObject.getContents();
		ArrayList<Template> outGoingTemplates = new ArrayList<Template>();
		for (Template template : templates){
//			System.out.println(template.getHashes().size());

			Template hashes = new Template();
			hashes.getHashes().addAll(multiEncrypt(publicKey, template.getHashes(), false));
			outGoingTemplates.add(hashes);
//			template.setHashes(hashes);
			
//			for (BigInteger feature : template.getHashes()){
//				feature = encryptionScheme.encrypt(publicKey, feature);
//				hashes.add(feature);
//			}
//			template.setHashes(hashes);
		}
//		System.out.println(templates.get(0).getHashes().size());
		long stop = System.currentTimeMillis();
//		addToTestTiming("Server 2 total multiEncrypt templates time", (stop-start));
		addToTestTiming("Server 2 multiEncrypt per template time", (stop-start)/templates.size());
		
		InterServerObjectWrapper objectToSend = new InterServerObjectWrapper();
		objectToSend.setContents(outGoingTemplates);
		objectToSend.setOrigin("server 2");
		objectToSend.setEnrolling(false);
		objectToSend.setTesting(true);
		objectToSend.setUserID(receivedObject.getUserID());
		return objectToSend;

		
	}






	@Override
	public RawScores run() {

		try {
			initialize();
		} catch (Exception e) {
			System.out.println("Server 2 failed to initialize");
			e.printStackTrace();
		}

		return null;
	}

}

