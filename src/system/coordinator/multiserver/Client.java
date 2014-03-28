package system.coordinator.multiserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
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

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;

import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerTwoSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

public class Client extends Server {
	// extends server
	private ObjectOutputStream S1Out;
	private ObjectOutputStream S2Out;
	private InputStreamReader S1Reader;
	public Client(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
		try {
			Socket S1 = new Socket(ServerOneSettings.getInstance().ip().getValue(), ServerOneSettings.getInstance().portNumber().getValue().intValue());
			Socket S2 = new Socket(ServerTwoSettings.getInstance().ip().getValue(), ServerTwoSettings.getInstance().portNumber().getValue().intValue());
			S1Out = new ObjectOutputStream (S1.getOutputStream());
			S2Out = new ObjectOutputStream (S2.getOutputStream());
			InputStream S1In = S1.getInputStream();
			S1Reader = new InputStreamReader(S1In); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public KeyPair getKeyPair(){
		try{
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
			return keyGen.generateKeyPair();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

	}


	@Override
	public RawScores run() {

		  
		
//        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
//        
//		for (User user : this.users.users) {
//			KeyPair dhKeys = keyGenerator.generateKeyPair();
//			PublicKey publicKey = dhKeys.getPublic();
//			//String publicKeyString = publicKey.toString();
//			PrivateKey privateKey = dhKeys.getPrivate();
//			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//			S2Out.write(privateKey.getEncoded());
//
//
//			ArrayList<Template> encryptedFingerprint = new ArrayList<Template>();
//			for (Biometric b : user.readings) {
//				Template encryptedBiometric = new Template();
//				for (BigInteger bigInt : b.quantizeOne().getHashes()) {
//					encryptedBiometric.getHashes().add(new BigInteger(cipher.doFinal(bigInt.toByteArray())));
//				}
//				encryptedFingerprint.add(encryptedBiometric);
//			}
//			
//			objStream.writeObject(encryptedFingerprint);
//			S2Out.write(byteStream.toByteArray());
//			S1Out.write(privateKey.getEncoded());
//			Double score = Double.valueOf(S1reader.read());
//			System.out.println("scores yaaaaay:"+score);
//		}
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
		toS1.setContents(privateKey.getEncoded());
		toS1.setEnrolling(true);
		toS1.setTesting(false);
		toS1.setUserID(userID);
		toS1.setOrigin("client");
		try{
		   Cipher cipher = Cipher.getInstance("ECDH", "BC");

		   cipher.init(Cipher.ENCRYPT_MODE,pair.getPublic());
		// 3.) encrypt Template with public key e(u) [public key] from 1.)

		Template encryptedBiometric = new Template();
		for (BigInteger bigInt : template.getHashes()) {
			encryptedBiometric.getHashes().add(new BigInteger(cipher.doFinal(bigInt.toByteArray())));
		}
		// 4.) sent e(Template) to Server_2
		toS2.setContents(encryptedBiometric);

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
	}

	public Double test(Template template,  Long userID) {
		// 1.) generate key pair
		// 2.) encrypt Template with public key e(u) [public key] from 1.)
		// 2a.) generate UUID for verification
		// 3.) send d(u) [private key] to Server_1
		// 4.) sent e(Template) to Server_2
		// wait for server 1's response
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
