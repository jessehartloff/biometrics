package system.coordinator.multiserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

public class BingyZhangEncryptionScheme {
	
	public static HashSet<BigInteger> encrypt(HashSet<BigInteger> messages, BigInteger key ){

		int encryptionsPerCall = 200;

		Runtime rt = Runtime.getRuntime();
		try {
			ArrayList<String> toBingsheng = new ArrayList<String>();
			String command = null;

//			for(int j=0; j < messages.size(); j++){
			int j=0;
			for(BigInteger message : messages){
				
				if(j%encryptionsPerCall == 0){
					if(command != null){
						toBingsheng.add(command);
//						System.out.println(command);
					}
					command = "/bingyzhang/Enc ";
					command += new String(Base64.encodeBase64(key.toByteArray()));
//					command += " ";
				}
//				BigInteger message = messages.get(j);
				command += " " + new String(Base64.encodeBase64(message.toByteArray()) );

				j++;
			}
			
			toBingsheng.add(command);
			
			ArrayList<Process> processes = new ArrayList<Process>();

			HashSet<BigInteger> encryptedBigInts = new HashSet<BigInteger>();

			for(String command2 :toBingsheng){
				Process p = rt.exec(System.getProperty("user.dir")+command2);
				processes.add(p);
			} 
			for(Process p : processes){
				p.waitFor(); 
			}
			
			for(Process p : processes){
				InputStream is = p.getInputStream();
				BufferedReader r = new BufferedReader(new InputStreamReader(is));
				
				for(int k=0; k<encryptionsPerCall; k++){
					String s = r.readLine();
//					System.out.print(s);
					if(s == null){
						break;
					}
					encryptedBigInts.add(new BingyZhangPoint(s).toBigInt());
				}
				p.destroy();
			}

			return encryptedBigInts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	
	public static HashSet<BigInteger> reEncrypt(HashSet<BigInteger> messages, BigInteger key ){
		int encryptionsPerCall = 200;
		Runtime rt = Runtime.getRuntime();
		try {
			
			ArrayList<String> toBingsheng = new ArrayList<String>();
			String command = null;
			BingyZhangPoint bzp = new BingyZhangPoint();

//			for(int j = 0; j < messages.size(); j++){
			int j=0;
			for(BigInteger message : messages){
				if(j%encryptionsPerCall == 0){
					if(command != null){
						toBingsheng.add(command);
					}
					command = "/bingyzhang/ReEnc ";
					command += new String(Base64.encodeBase64(key.toByteArray()));
					command += " ";
				}
//				BigInteger message = messages.get(j);
				bzp.fromBigInt(message);
				command += " " + bzp.toEncodedString();
				j++;
			}
			
			toBingsheng.add(command);
			
			ArrayList<Process> processes = new ArrayList<Process>();
			HashSet<BigInteger> encryptedBigInts = new HashSet<BigInteger>();
			
			for(String command2 :toBingsheng){
				Process p = rt.exec(System.getProperty("user.dir")+command2);
				processes.add(p);
			} 
			for(Process p : processes){
				p.waitFor(); 
			}
			
			for(Process p : processes){ 
				InputStream is = p.getInputStream();
				BufferedReader r = new BufferedReader(new InputStreamReader(is));
				for(int k = 0; k < encryptionsPerCall; k++){
					String s= r.readLine();
					if(s == null){
						break;
					}
					encryptedBigInts.add(new BingyZhangPoint(s).toBigInt());
				}
				p.destroy();
			}
			return encryptedBigInts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	public static void main(String[] args){
		HashSet<BigInteger> testInts = new HashSet<BigInteger>();
		for(int i = 0; i < 2000; i++){
			testInts.add(new BigInteger(190, new Random()));
		}
		System.out.println("made some ints" + testInts);
		EncryptionScheme es = new EncryptionScheme();
		SimpleKeyPair keys = es.generateKeyPair();
		SimpleKeyPair keys2 = es.generateKeyPair();
		long start = System.currentTimeMillis();
		HashSet<BigInteger> encryptedshit = encrypt(testInts, keys.getPrivate());
//		System.out.println("Jim: " + encryptedshit);
//		HashSet<BigInteger> encryptedshit = encrypt(testInts, new BigInteger(40, new Random()));
		long middle = System.currentTimeMillis();
		HashSet<BigInteger> encryptedershit = reEncrypt(encryptedshit, keys2.getPrivate());
//		HashSet<BigInteger> encryptedershit = reEncrypt(encryptedshit, new BigInteger(80, new Random()));
		long end = System.currentTimeMillis();
//		HashSet<BigInteger> unencryptedershit = reEncrypt(encryptedershit, keys.getPublic());
		
//		HashSet<BigInteger> shit = encrypt(testInts, keys2.getPrivate() );
		System.out.println("encrypt: " + (middle - start) );
		System.out.println("reencpt: " + (end- middle) );
//		System.out.println("commute:\n" + unencryptedershit);
//		System.out.println("straight-forward:\n" + shit);
//		System.out.println("\n" + shit.size());
	}
}