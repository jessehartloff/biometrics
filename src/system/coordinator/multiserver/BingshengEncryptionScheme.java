package system.coordinator.multiserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

public class BingshengEncryptionScheme {
	
	public static ArrayList<BigInteger> encrypt(ArrayList<BigInteger> messages, BigInteger key ){
		int encryptionsPerCall = 200;
		Runtime rt = Runtime.getRuntime();
		try {
			ArrayList<String> toBingsheng = new ArrayList<String>();
			String command = null;
			
			int j = 0;
			for(; j < messages.size(); j++){
				if(j%encryptionsPerCall == 0){
					if(command != null){
						toBingsheng.add(command);
					}
					command = "/bingyzhang/Enc ";
					command += new String(Base64.encodeBase64(key.toByteArray()));
					command += " ";
				}
				
//				if(command == null){
//					command = "/bingyzhang/Enc ";
//					command += new String(Base64.encodeBase64(key.toByteArray()));
//					command += " ";
//				}else if(j%encryptionsPerCall == 0){
////					System.out.println("!#$!@TRFWERG");
//					toBingsheng.add(command);
//					command = "/bingyzhang/Enc ";
//					command += new String(Base64.encodeBase64(key.toByteArray()));
//					command += " ";
//				}
				
				BigInteger message = messages.get(j);
				command = command + " " + new String(Base64.encodeBase64(message.toByteArray()) );
				// command = command + " "+new String(Base64.encodeBase64(message.toByteArray()));
			}
			
//			if(j%encryptionsPerCall != 0){
			toBingsheng.add(command);
//			}
			
//			int iter = 200;
//			Random rand = new Random();
//			for(int i = 0; i < iter; i ++){
//				BigInteger randomInt = new BigInteger(190, rand);				
//				command = command + " " + new String(Base64.encodeBase64(randomInt.toByteArray()));
//			}
			
			ArrayList<Process> processes = new ArrayList<Process>();
			for(String command2 :toBingsheng){
				processes.add(rt.exec(System.getProperty("user.dir")+command2));
			} 
			for(Process p : processes){
				p.waitFor(); 
			}
//			System.out.println("finsihed!");
			ArrayList<BigInteger> encryptedBigInts = new ArrayList<BigInteger>();
//System.out.println("ll " + processes.size());
			for(Process p : processes){
				
				InputStream is = p.getInputStream();
				BufferedReader r = new BufferedReader(new InputStreamReader(is));
//				System.out.println("shitshitshit");
				for(int k = 0; k < encryptionsPerCall; k++){
					String s= r.readLine();
					if(s == null){
						break;
					}
//					System.out.println(s);
					encryptedBigInts.add(new BingyZhangPoint(s).toBigInt());
					//BigInteger returnedInt = new BigInteger(Base64.decodeBase64(s));
					//encryptedBigInts.add(returnedInt);
				}
				
			}

			return encryptedBigInts;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	
	
	public static ArrayList<BigInteger> reencrypt(ArrayList<BigInteger> messages, BigInteger key ){
		int encryptionsPerCall = 200;
		Runtime rt = Runtime.getRuntime();
		try {
			//			int iter = 200;
			ArrayList<String> toBingsheng = new ArrayList<String>();
//			int i = 0;
			//			while( i < messages.size()){
			//				String command = "/bingyzhang/ReEnc AIUiUE/kjQmLfJu514zVw/NzOlHc ";
			
			String command = null;
			BingyZhangPoint bzp = new BingyZhangPoint();
			int j = 0;
			for(; j < messages.size(); j++){
				if(command == null){
					command = "/bingyzhang/ReEnc ";
					command += new String(Base64.encodeBase64(key.toByteArray()));
					command += " ";
				}else if(j%encryptionsPerCall == 0){
//					System.out.println("!#$!@TRFWERG");
					toBingsheng.add(command);
					command = "/bingyzhang/ReEnc ";
					command += new String(Base64.encodeBase64(key.toByteArray()));
					command += " ";
				}
				
				BigInteger message = messages.get(j);
				bzp.fromBigInt(message);
				command += " " + bzp.toEncodedString();
				// command = command + " "+new String(Base64.encodeBase64(message.toByteArray()));
			}
			
//			if(j%encryptionsPerCall != 0){
			toBingsheng.add(command);
//			}
//			System.out.println(command);
//			toBingsheng.add(command);
//			i += j;
			//			}
			
			ArrayList<Process> processes = new ArrayList<Process>();
			for(String command2 :toBingsheng){
				processes.add(rt.exec(System.getProperty("user.dir")+command2));
			} 
			for(Process p : processes){
				p.waitFor(); 
			}
//			System.out.println("finsihed!");
			ArrayList<BigInteger> encryptedBigInts = new ArrayList<BigInteger>();
//System.out.println("ll " + processes.size());
			for(Process p : processes){
				InputStream is = p.getInputStream();
				BufferedReader r = new BufferedReader(new InputStreamReader(is));
//				System.out.println("shitshitshit");
				for(int k = 0; k < encryptionsPerCall; k++){
					String s= r.readLine();
					if(s == null){
						break;
					}
//					System.out.println(s);
					encryptedBigInts.add(new BingyZhangPoint(s).toBigInt());
					//BigInteger returnedInt = new BigInteger(Base64.decodeBase64(s));
					//encryptedBigInts.add(returnedInt);
				}
				
			}
			return encryptedBigInts;
		} catch (Exception e) {
			e.printStackTrace();
		}


		return null;
	}
	
	
	
	public static void main(String[] args){
		ArrayList<BigInteger> testInts = new ArrayList<BigInteger>();
//		Random r = new Random();
		for(int i = 0; i < 2000; i++){
			testInts.add(new BigInteger(190,new Random()));
		}
		System.out.println("made some ints");
		EncryptionScheme es = new EncryptionScheme();
		SimpleKeyPair keys = es.generateKeyPair();
		SimpleKeyPair keys2 = es.generateKeyPair();
		long start = System.currentTimeMillis();
		ArrayList<BigInteger> encryptedshit = encrypt(testInts, keys.getPrivate());
		long middle = System.currentTimeMillis();
		ArrayList<BigInteger> encryptedershit = reencrypt(encryptedshit, keys2.getPrivate());
		long end = System.currentTimeMillis();
//		ArrayList<BigInteger> unencryptedershit = reencrypt(encryptedershit, keys.getPublic());
		
//		ArrayList<BigInteger> shit = encrypt(testInts, keys2.getPrivate() );
		System.out.println("encrypt: " + (middle - start) );
		System.out.println("reencpt: " + (end- middle) );
//		System.out.println("commute:\n" + unencryptedershit);
//		System.out.println("straight-forward:\n" + shit);
//		System.out.println("\n" + shit.size());
	}
}