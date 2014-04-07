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
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;

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

	public Server1(Hasher hasher, Users users) {
		super(hasher, users);
		// TODO Auto-generated constructor stub
	}

	private HashMap<Long,Template> _map;
	private PrivateKey _clientKey;

	//MUMBO JUMBO
	/* 
		public abstract class ServerOperation{
			InterServerObjectWrapper fromClient;
			InterServerObjectWrapper fromS2;
			public abstract Double run();
		}

		public class Test extends ServerOperation{

			@Override
			public Double run() {
				return null;
				// TODO Auto-generated method stub
				//ArrayList<Template> test = hasher.makeTestTemplates(user.readings.get(i));			
			}

		}

		public class Enroll extends ServerOperation{

			@Override
			public Double run() {
				return null;			
				//Template enroll = hasher.makeEnrollTemplate(user.readings.get(i));
				// TODO Auto-generated method stub

			}

		}

	 */

	// THIS IS HORSE
	/*
		public void initialize(){
			try {
	            ServerSocket S1 = new ServerSocket(ServerOneSettings.getInstance().portNumber().getValue().intValue());
	            AllHasherSettings.getInstance().manuallySetComboBox(ShortcutFuzzyVaultSettings.getInstance());
	            //Socket client = new Socket(ClientSettings.getInstance().ip().getValue(),ClientSettings.getInstance().portNumber().getValue().intValue());
	            //ObjectOutputStream toClient = new ObjectOutputStream(client.getOutputStream());
	            //Server1 s = new Server1(null, null);
	            System.out.println();
				while(true){
					//ServerOperation e;
					Socket serverA = S1.accept();
					ObjectInputStream inA = new ObjectInputStream(serverA.getInputStream());
					InterServerObjectWrapper A = (InterServerObjectWrapper) inA.readObject();
					Socket serverB = S1.accept();
					ObjectInputStream inB = new ObjectInputStream(serverB.getInputStream());
					InterServerObjectWrapper B = (InterServerObjectWrapper) inB.readObject();
					boolean isEnrolling = A.isEnrolling() && B.isEnrolling();
					if(isEnrolling){
						e = s.new Enroll();
					} else {
						e = s.new Test();
					} 
					if(A.getOrigin() == "client"){
						e.fromClient = A;
						e.fromS2 = B;
					} else{
						e.fromClient = B;
						e.fromS2 = A;
					}

					Double result = e.run();
					//toClient.writeDouble(result);
				}


			} catch (Exception e) {
				e.printStackTrace();
			}


		}
	 */
	/*
	public void initialize(){
		try{
		ServerSocket S1 = new ServerSocket(ServerOneSettings.getInstance().portNumber().getValue().intValue());
        AllHasherSettings.getInstance().manuallySetComboBox(ShortcutFuzzyVaultSettings.getInstance());
        Socket client = null;
        while ( true ){
        	client = S1.accept();
        	ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
        	InterServerObjectWrapper receivedObject= (InterServerObjectWrapper) objIn.readObject();
        	if (receivedObject.getOrigin() == "client"){
        		setClientKey(receivedObject);
        	}else{

        	}
        }

		}catch ( Exception e ){

		}
	}

	 */ 



	public void setClientKey (InterServerObjectWrapper clientObject){
		_clientKey = (PrivateKey)clientObject.getContents();


	}

	public PrivateKey getClientKey (){
		return _clientKey;
	}


	/*
	// FIXME EVERYONE EVER
	public Server1(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);

	}

	@Override
	public RawScores run() {
		int clientPort = 8000, S2Port = 8002;

		try {
			Socket client = new Socket("localhost", clientPort);
			Socket S2 = new Socket("localhost", S2Port);
			InputStream clientIn = client.getInputStream();
			InputStream S2In = S2.getInputStream();
			OutputStream S1Out = client.getOutputStream();
			InputStreamReader clientReader = new InputStreamReader(clientIn); 
			InputStreamReader S2reader = new InputStreamReader(S2In); 

			Cipher cipher = Cipher.getInstance("ECDH","BC");

	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        int data = clientReader.read();
	        while(data != -1){
	        	buffer.write(data);
	            data = clientReader.read();
	        }
	        buffer.flush();
	        clientReader.close();  

			ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer.toByteArray());
	        ObjectInputStream objStream = new ObjectInputStream(byteStream);
	        PrivateKey pk = (PrivateKey) objStream.readObject();

	        buffer = new ByteArrayOutputStream();
	        data = S2reader.read();
	        while(data != -1){
	        	buffer.write(data);
	            data = S2reader.read();
	        }
	        buffer.flush();


	        byteStream = new ByteArrayInputStream(buffer.toByteArray());
	        objStream = new ObjectInputStream(byteStream);
	        ArrayList<Template> encryptedFingerprint = (ArrayList<Template>) objStream.readObject();       

			cipher.init(Cipher.DECRYPT_MODE, pk);

			ArrayList<Template> decryptedFingerprint = new ArrayList<Template>();
			for(Template template : encryptedFingerprint){
				Template decryptedTemplate = new Template();
				for(BigInteger bigInt : template.getHashes()){
					decryptedTemplate.getHashes().add(new BigInteger(cipher.doFinal(bigInt.toByteArray())));	
				}
				decryptedFingerprint.add(decryptedTemplate);
			}
//			this.hasher.hashEnrollTemplate(template); // TODO I commented this out   //YOU DON'T SAY



		} catch(Exception e){
			e.printStackTrace();
		}


		return null;


	}
	 */ 

	// put all four of these functions in Server
	@Override
	public Object receive(Object object) {
		// make an ID class to handle uuid per transaction, server2 and client
		// origin and user id, plus enrollment/testing boolean
		// 0.) if it's the first time S1 has seen the user id, then it must be
		// an enrollment

		// 1.) Receive input from client and Server_2
		// 2.) Determine where the the input is coming from based on UUID
		// presence/matching in some external data structure
		// 2a.) Making an external thread to handle this process
		// 3.) if there's a match, then call other functions that handle each
		// case( from client or serve 2); these functions then call send(..)
		return object;
	}

	public void enroll(InterServerObjectWrapper objectIn) {
		// 1.) Call Hasher.hashEnrollTemplate on enrolling Templates
		// 2.) Store that Template in RAM BECAUSE SQL JIM AND JEN!
		Template template = (Template)objectIn.getContents();
		Template fuzzyVault = hasher.hashEnrollTemplate(template);
		long ID = objectIn.getUserID();
		_map.put(ID,fuzzyVault);
	}

	public double test(InterServerObjectWrapper objectIn) {
		// 1.) Get test Templates from Hasher with Hasher.hashTestTemplates
		// 2.) Compare the test Templates and the enrolled Templates with
		// Hasher.compareTemplates
		//
		long ID = objectIn.getUserID();
		ArrayList<Template> testTemplates = hasher.hashTestTemplates((ArrayList<Template>)objectIn.getContents()); 
		Template enrolledTemplate = _map.get(ID);
		Double result=  hasher.compareTemplates(enrolledTemplate, testTemplates);
		return result;
	}

	@Override
	public void send(Object object) {
		// Send back to the client
	}

	public void receivePrivateKey() throws Exception {

		/*
		 * int port = 0; ServerSocket serv_socket = null; Socket client = null;
		 * serv_socket = new ServerSocket(8000);
		 * serv_socket.setReuseAddress(true); port = serv_socket.getLocalPort();
		 * System.out.println(port); client = serv_socket.accept();
		 * 
		 * ObjectInputStream objInput = new
		 * ObjectInputStream(client.getInputStream()); PublicKey pk =
		 * (PublicKey) objInput.readObject(); System.out.println(pk.toString());
		 * 
		 * long startTime = System.currentTimeMillis(); String password =
		 * "ThisIsMyPassword"; //Symmetric Key Distribution Using Asymmetric
		 * Encryption KeyPairGenerator keyGenerator =
		 * KeyPairGenerator.getInstance("RSA"); keyGenerator.initialize(1024);
		 * KeyPair rsaKeys = keyGenerator.generateKeyPair(); PublicKey publicKey
		 * = rsaKeys.getPublic(); String publicKeyString = publicKey.toString();
		 * PrivateKey privateKey = rsaKeys.getPrivate();
		 * 
		 * long keyGenTime = System.currentTimeMillis() - startTime;
		 * System.out.println("Took "+ keyGenTime +
		 * " milliseconds to generate key pair "); //long startEncryption =
		 * System.currentTimeMillis();
		 * 
		 * Cipher cipher = Cipher.getInstance("RSA"); long startEncryption =
		 * System.currentTimeMillis(); byte[] encryptedKey = null;
		 * System.out.println("BEFORE WE ENCRYPT");
		 * 
		 * for ( int i= 0; i<5; i++){
		 * 
		 * cipher.init(Cipher.ENCRYPT_MODE,publicKey); encryptedKey =
		 * cipher.doFinal(password.getBytes()); //cipher.update(encryptedKey);
		 * System.out.println(new String(encryptedKey, "UTF-8")); }
		 * System.out.println("AFTER WE ENCRYPT"); long finishEncryption =
		 * System.currentTimeMillis();
		 * 
		 * System.out.println("Time to encrypt is : " + (finishEncryption -
		 * startEncryption));
		 * 
		 * cipher.init(Cipher.DECRYPT_MODE, privateKey); byte[] decryptedKey =
		 * cipher.doFinal(encryptedKey);
		 * System.out.println("Time to decrypt is : " + (
		 * System.currentTimeMillis()- finishEncryption));
		 * System.out.println(new String (decryptedKey, "UTF-8"));
		 */
	}

	@Override
	public RawScores run() {
		// TODO Auto-generated method stub
		return null;
	}



	private InterServerObjectWrapper _receivedObject;

	public void intitialize() throws Exception{
		ServerSocket S1 = new ServerSocket(ServerOneSettings.getInstance().portNumber().getValue().intValue());
		AllHasherSettings.getInstance().manuallySetComboBox(ShortcutFuzzyVaultSettings.getInstance());
		Socket client = null;

		int state = 1;
		while ( true ) {

			switch(state){
			case 1:
				client = S1.accept();
				ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
				_receivedObject= (InterServerObjectWrapper) objIn.readObject();
				if (_receivedObject.getOrigin() == "client"){
					state = 2;
				}else{
					state = 3;
				}
				break;
			case 2:
				setClientKey (_receivedObject);
				ObjectOutputStream objOut = new ObjectOutputStream(client.getOutputStream());
				objOut.write(0);
				state = 1; 
				break;
			case 3:
				if (_receivedObject.isEnrolling()){
					enroll(_receivedObject);
					state = 4;
				}else{
					double result = test(_receivedObject);
					ObjectOutputStream objTestOut = new ObjectOutputStream(client.getOutputStream());
					objTestOut.writeDouble(result);
					state = 1;
				}
				break;
			case 4 :
				ObjectOutputStream objEnrollOut = new ObjectOutputStream(client.getOutputStream());
				objEnrollOut.write(1);
				state = 1;
				break;
			}
		}
	}


}
