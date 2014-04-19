package system.coordinator.multiserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import settings.AllSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ClientSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.hashersettings.AllHasherSettings;
import settings.hashersettings.HasherSettings;
import settings.hashersettings.ShortcutFuzzyVaultSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;
import system.hasher.HasherFactory.HasherEnumerator;

public class Server1 extends Server {
	private HashMap<Long,Template> map;
	private BigInteger clientKey;
	private InterServerObjectWrapper receivedObject;

	
	public Server1(Hasher hasher, Users users) {
		super(hasher, users);
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

	}

	public void enroll(InterServerObjectWrapper objectIn) {
		// 1.) Call Hasher.hashEnrollTemplate on enrolling Templates
		// 2.) Store that Template in RAM BECAUSE SQL JIM AND JEN!
//		Cipher cipher = null;;
//		try {
//			cipher = Cipher.getInstance("ECIES","BH");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
		long start = System.currentTimeMillis();
		Template template = (Template) objectIn.getContents();
		
//		Template newTemplate = new Template();
//		
//		for(BigInteger point : template.getHashes()){
//			BigInteger temp;
//			BigInteger shiftAdder = point.and(BigInteger.ONE);
//			if(shiftAdder.equals(BigInteger.ONE)){System.out.println("chaff");}
//			temp = point.shiftRight(1);
//			temp = encryptionScheme.decrypt(temp, clientKey);
//			newTemplate.getHashes().add((temp.shiftLeft(1)).add(shiftAdder));
//		}
//
//		System.out.println("enroll shifted: "+newTemplate.getHashes());

		Template decryptedTemplate = new Template();
		decryptedTemplate.setHashes(this.multiEncrypt(clientKey, template.getHashes(), false));
		
		long stop = System.currentTimeMillis();
		addToEnrollTiming("Server 1 decrypt gen points time", (stop-start));
		
		start = System.currentTimeMillis();

//		System.out.println("enroll b4: "+newTemplate.getHashes());
		Template fuzzyVault = hasher.hashEnrollTemplate(decryptedTemplate);
//		System.out.println("fv: "+fuzzyVault.getHashes());

		long ID = objectIn.getUserID();
//		System.out.println("enrollID: "+ID);
		map.put(ID,fuzzyVault);
		stop = System.currentTimeMillis();
		addToEnrollTiming("Server 1 hash enroll time", (stop-start));
	}

	public double test(InterServerObjectWrapper objectIn) {
		// 1.) Get test Templates from Hasher with Hasher.hashTestTemplates
		// 2.) Compare the test Templates and the enrolled Templates with
		// Hasher.compareTemplates
//		Cipher cipher = null;;
//		try {
//			cipher = Cipher.getInstance("ECIES","BH");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
		long start = System.currentTimeMillis();
		long ID = objectIn.getUserID();
//		System.out.println("testID: "+ID);

		ArrayList<Template> templates = (ArrayList<Template>)objectIn.getContents();
		
		ArrayList<Template> decryptedTemplates = new ArrayList<Template>();
		
		for(Template template : templates){
			Template decryptedTemplate = new Template();
			decryptedTemplate.setHashes(this.multiEncrypt(clientKey, template.getHashes(), false));
			decryptedTemplates.add(decryptedTemplate);
		}
		
		long stop = System.currentTimeMillis();
//		addToTestTiming("Server 1 decrypt all template genuines time", (stop-start));
		addToTestTiming("Server 1 decrypt genuines per template time", (stop-start)/templates.size());

		start = System.currentTimeMillis();
//		System.out.println("before:"+templates.get(0).getHashes());
		ArrayList<Template> testTemplates = hasher.hashTestTemplates(decryptedTemplates); 
//		System.out.println("after:"+testTemplates.get(0).getHashes());

		Template enrolledTemplate = map.get(ID);
//		System.out.println(enrolledTemplate.getHashes());
//		System.out.println(testTemplates.get(0).getHashes());
		System.out.println("Enrolled Size:"  + enrolledTemplate.getHashes().size());
		System.out.println("Test template 0 size:" + testTemplates.get(0).getHashes().size());
		Double result =  hasher.compareTemplates(enrolledTemplate, testTemplates);
		System.out.println(result);
		stop = System.currentTimeMillis();
//		addToTestTiming("Server 1 hash enroll all time", (stop-start));
		addToTestTiming("Server 1 hash enroll per template time", (stop-start)/templates.size());
		return result;
	}


	@Override
	public RawScores run() {
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public void initialize() {
		this.map = new HashMap<Long,Template>();
		
		try{

			ServerSocket S1 = new ServerSocket(ServerOneSettings.getInstance().portNumber().getValue().intValue());
	//		AllHasherSettings.getInstance().manuallySetComboBox(ShortcutFuzzyVaultSettings.getInstance());
			Socket client = null;
	
			int state = 1;
			while ( true ) {
//				System.out.println("State:"+state);
				switch(state){
				case 1:
					System.out.println("Server 1 listening on port " + ServerOneSettings.getInstance().portNumber().getValue().intValue() + "....");
	//				client = S1.accept();
	//				ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
	//				
	//				System.out.println("GOT SHIT FROM THE CLIENT");
	
					receivedObject = receive(S1, false, " IGNORE THIS ");
	//				System.out.println(System.currentTimeMillis());
					System.out.println("Received data from "+receivedObject.getOrigin());
					if (receivedObject.getOrigin().equals("client")){
						state = 2;
					} else if (receivedObject.getOrigin().equals("getEnrollTiming") || receivedObject.getOrigin().equals("getTestTiming")) {
						state = 1;
					}else if (receivedObject.isEnrolling()){
						state = 3;
					}else {
						state = 4;
					}
					
					break;
				/**
				 * receiving from the client
				 */
				case 2:

					System.out.println("Got to case 2");
					this.clientKey = (BigInteger) receivedObject.getContents();

//					System.out.println("client key: " + clientKey);
//					InterServerObjectWrapper response = new InterServerObjectWrapper();
//					response.setContents("Server 1 has successfully received client decryption key");
//					//send back the decision
////					System.out.println(ClientSettings.getInstance().ip().getValue());
//					send(ClientSettings.getInstance().ip().getValue(),
//							ClientSettings.getInstance().portNumber().getValue().intValue(),
//							response, true, "decrypt key");
					state = 1; 
					break;
				/**
				 * receiving enroll from server 2
				 */
				case 3:
	//				System.out.println("Got to case 3");
	//				client.close();
	//				client = new Socket(ClientSettings.getInstance().ip().getValue(), ClientSettings.getInstance().portNumber().getValue().intValue());
	//				System.out.println(receivedObject.getOrigin());
	//				System.out.println(receivedObject.getContents());
	//
	//				System.out.println(receivedObject.isEnrolling());
	//				System.out.println(receivedObject.getUserID());
					long start = System.currentTimeMillis();
					enroll(receivedObject);
					long stop = System.currentTimeMillis();
					addToEnrollTiming("Server 1 enroll template time", (stop-start));
					InterServerObjectWrapper decision = new InterServerObjectWrapper();
					decision.setContents("Server 1 has successfully enrolled template");
					//send back the decision
//					System.out.println(ClientSettings.getInstance().ip().getValue());
					send(ClientSettings.getInstance().ip().getValue(),
							ClientSettings.getInstance().portNumber().getValue().intValue(),
							decision, true, "enroll decision");
					state = 1;
	//				
	//	
	//				if (receivedObject.isEnrolling()){
	//					enroll(receivedObject);
	//					state = 4;
	//				}else{
	//					double result = test(receivedObject);
	//					ObjectOutputStream objTestOut = new ObjectOutputStream(client.getOutputStream());
	//					objTestOut.writeDouble(result);
	//					state = 1;
	//				}
					break;
				/**
				 * receiving test from server 2
				 */
				case 4 :
	//				ObjectOutputStream objEnrollOut = new ObjectOutputStream(client.getOutputStream());
	//				objEnrollOut.write(1);
	//				objEnrollOut.flush();
	//				System.out.println("Enrolled that ish");
					Double score = test(receivedObject);
					System.out.println(score);
					InterServerObjectWrapper matchScore = new InterServerObjectWrapper();
					matchScore.setContents(score);
					send(ClientSettings.getInstance().ip().getValue(),
							ClientSettings.getInstance().portNumber().getValue().intValue(),
							matchScore, false, "test decision");
					state = 1;
					break;
				}
				
//				/**
//				 * send back our timing
//				 */
//				case 5:
					
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
