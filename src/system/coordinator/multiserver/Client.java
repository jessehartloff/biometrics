package system.coordinator.multiserver;

import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.ArrayList;

import javax.crypto.Cipher;

import settings.coordinatorsettings.multiservercoordinatorsettings.ClientSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerTwoSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Client extends Server{
	// extends server
	private ObjectOutputStream S1Out;
	private ObjectOutputStream S2Out;
	private ObjectInputStream S1In;
	private InputStreamReader S1Reader;
	private Socket S1;
	private Socket S2;


	public Client() {
		super(null, null);
		try {
			System.out.println("Connecting to Socket 1...");
			S1 = new Socket(InetAddress.getByName(ServerOneSettings.getInstance().ip().getValue()), ServerOneSettings.getInstance().portNumber().getValue().intValue());
//			System.out.println("Connecting to Socket 2...");
//			Socket S2 = new Socket(InetAddress.getByName(ServerTwoSettings.getInstance().ip().getValue()), ServerTwoSettings.getInstance().portNumber().getValue().intValue());

			S1Out = new ObjectOutputStream (S1.getOutputStream());
			System.out.println("Got Socket 1 Outputstream...");
			



			//System.out.println("Getting Socket 2 Inputstream...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	public KeyPair getKeyPair(){
		try{
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			//TODO figure out what this ECDH stuff does
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECIES", "BC");
			EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(
					"fffffffffffffffffffffffffffffffeffffffffffffffff", 16)), new BigInteger(
							"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
									"fffffffffffffffffffffffffffffffefffffffffffffffc", 16));

			ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(new BigInteger(
					"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
							"fffffffffffffffffffffffffffffffefffffffffffffffc", 16)), new BigInteger(
									"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), 1);

			keyGen.initialize(ecSpec, new SecureRandom());
			return keyGen.generateKeyPair();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public RawScores run() {
//		long start = System.currentTimeMillis();
//		enroll(hasher.makeEnrollTemplate(users.users.get(0).readings.get(0)), users.users.get(0).id);
//		long stop = System.currentTimeMillis();
//		System.out.println("enrolled in "+ (stop-start) + "sec");
//		test(hasher.makeTestTemplates(users.users.get(0).readings.get(0)), users.users.get(0).id);
//		System.out.println("tested!!");

		System.exit(0);
		return null;
	}

	public void enroll(Template template, Long userID) {
		InterServerObjectWrapper toS1 = new InterServerObjectWrapper();
		InterServerObjectWrapper toS2 = new InterServerObjectWrapper();
		// 1.) generate key pair
		KeyPair pair = this.getKeyPair();
		PublicKey publicKey = pair.getPublic();                            
		PrivateKey privateKey = pair.getPrivate();

		// 2.) send d(u) [private key] to Server_1
		toS1.setContents(privateKey);
		toS1.setEnrolling(true);
		toS1.setTesting(false);
		toS1.setUserID(userID);
		toS1.setOrigin("client");
		
		try{
			Cipher cipher = Cipher.getInstance("ECIES","BC");

			cipher.init(Cipher.ENCRYPT_MODE,publicKey);
			// 3.) encrypt Template with public key e(u) [public key] from 1.)

			Template encryptedBiometric = new Template();
			for (BigInteger bigInt : template.getHashes()) {
				encryptedBiometric.getHashes().add(new BigInteger(cipher.doFinal(bigInt.toByteArray())));
			}
			toS2.setContents(encryptedBiometric);

		}catch(Exception e){
			e.printStackTrace();
		}

		toS2.setEnrolling(true);
		toS2.setTesting(false);
		toS2.setOrigin("client");
		// 2a.) generate UUID for verification
		toS2.setUserID(userID);

		try{	
			System.out.println(System.currentTimeMillis());
			S1Out.writeObject(toS1);
//			S1Out.flush();
			System.out.println("Sent shit to S1");
//			S1Out.close();
			S1In = new ObjectInputStream(S1.getInputStream());
			System.out.println("Made input S1 steam and am waiting on response");
			int check =  S1In.read();
			System.out.println("Sent encryption to S1?" + check);
//			S1In.close();
			S1.close();
			// 4.) sent e(Template) to Server_2
			S2 = new Socket(InetAddress.getByName(ServerTwoSettings.getInstance().ip().getValue()), ServerTwoSettings.getInstance().portNumber().getValue().intValue());
			S2Out = new ObjectOutputStream (S2.getOutputStream());
			S2Out.writeObject(toS2);
			System.out.println("Sent shizz to server 2 Outputstream...");
			//5.) wait for server 1's response
			S2.close();
			ServerSocket client = new ServerSocket(ClientSettings.getInstance().portNumber().getValue().intValue());
//			System.out.println("before first accept");
			System.out.println("Waiting for S1's decision");
			Socket c = client.accept();
			
			S1In = new ObjectInputStream(c.getInputStream());
			int result  =  S1In.read();
			System.out.println("Successfully Enrolled... bitches!");


		} catch (Exception e){
			e.printStackTrace();
		}




	}

	public Double test(ArrayList<Template> testTemplates,  Long userID) {
		InterServerObjectWrapper toS1 = new InterServerObjectWrapper();
		InterServerObjectWrapper toS2 = new InterServerObjectWrapper();
		// 1.) generate key pair
		KeyPair pair = this.getKeyPair();
		PublicKey publicKey = pair.getPublic();
		PrivateKey privateKey = pair.getPrivate();

		// 2.) send d(u) [private key] to Server_1
		toS1.setContents(privateKey.getEncoded());
		toS1.setEnrolling(true);
		toS1.setTesting(false);
		toS1.setUserID(userID);
		toS1.setOrigin("client");
		try{
			Cipher cipher = Cipher.getInstance("ECIES", "BC");

			cipher.init(Cipher.ENCRYPT_MODE,pair.getPublic());
			// 3.) encrypt each Template in ArrayList<Template> with public key e(u) [public key] from 1.)

			ArrayList<Template> encryptedTestTemplate = new ArrayList<Template>();
			for(Template template : testTemplates){
				Template encryptedTemplate = new Template();
				for (BigInteger bigInt : template.getHashes()) {
					encryptedTemplate.getHashes().add(new BigInteger(cipher.doFinal(bigInt.toByteArray())));
				}
				encryptedTestTemplate.add(encryptedTemplate);
			}  
			// 4.) sent e(Template) to Server_2
			toS2.setContents(encryptedTestTemplate);

		}catch(Exception e){}

		toS2.setEnrolling(true);
		toS2.setTesting(false);
		toS1.setOrigin("client");
		// 2a.) generate UUID for verification
		toS2.setUserID(userID);

		try{	
			S1Out.writeObject(toS1);
			S2Out.writeObject(toS2);

		} catch (Exception e){}


		//5.) wait for server 1's response
		try{
			InterServerObjectWrapper result = (InterServerObjectWrapper) S1In.readObject();

		} catch(Exception e){}






		// 1.) generate key pair
		// 2.) encrypt Template with public key e(u) [public key] from 1.)
		// 2a.) generate UUID for verification
		// 3.) send d(u) [private key] to Server_1
		return null;
	}

	@Override
	public Object receive(Object object) {
		return null;
	}

	@Override
	public void send(Object object) {
	}

}
