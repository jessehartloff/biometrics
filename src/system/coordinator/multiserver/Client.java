package system.coordinator.multiserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerTwoSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Biometric;
import system.hasher.Hasher;

public class Client extends Server {
	// extends server

	public Client(Hasher hasher, Users enrollees) {
		super(hasher, enrollees);
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Socket S1 = new Socket(ServerOneSettings.getInstance().ip().getValue(), ServerOneSettings.getInstance().portNumber().getValue().intValue());
			Socket S2 = new Socket(ServerTwoSettings.getInstance().ip().getValue(), ServerTwoSettings.getInstance().portNumber().getValue().intValue());
			OutputStream S1Out = S1.getOutputStream();
			OutputStream S2Out = S2.getOutputStream();
			InputStream S1In = S1.getInputStream();
			InputStreamReader S1reader = new InputStreamReader(S1In); 
			

		  
			
	        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);

			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public KeyPair getKeyPair(){
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

		    KeyPair keyPair = keyGen.generateKeyPair();

			return keyPair;
	}
	
	
	@Override
	public RawScores run() {
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
	
	}

	public void enroll(Template template) {
		// 1.) generate key pair
		// 2.) encrypt Template with public key e(u) [public key] from 1.)
		// 2a.) generate UUID for verification
		// 3.) send d(u) [private key] to Server_1
		// 4.) sent e(Template) to Server_2
		// wait for server 1's response
	}

	public Double test(Template template) {
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
		try {
			Socket socket = new Socket("kq6py", 4321);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(Object object) {
		// TODO Auto-generated method stub

	}

}
