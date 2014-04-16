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
		Runtime rt = Runtime.getRuntime();
		try {
			String command = "/bingyzhang/Enc AIUiUE/kjQmLfJu514zVw/NzOlHc ";
			int iter = 200;
			Random rand = new Random();
			for(int i = 0; i < iter; i ++){
				BigInteger randomInt = new BigInteger(190, rand);				
				command = command + " "+new String(Base64.encodeBase64(randomInt.toByteArray()));
			}
			
			Process p = rt.exec(System.getProperty("user.dir")+command);
			p.waitFor(); 

			InputStream is = p.getInputStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			ArrayList<BigInteger> encryptedBigInts = new ArrayList<BigInteger>();
			
			for(int i = 0; i < iter; i ++){
				BigInteger returnedInt = new BigInteger(Base64.decodeBase64(r.readLine()));
				encryptedBigInts.add(returnedInt);
			}
			return encryptedBigInts;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public static ArrayList<BigInteger> reencrypt(ArrayList<BigInteger> messages, BigInteger key ){
		Runtime rt = Runtime.getRuntime();
		try {
			String command = "/bingyzhang/ReEnc AIUiUE/kjQmLfJu514zVw/NzOlHc ";
			int iter = 200;
			Random rand = new Random();
			for(int i = 0; i < iter; i ++){
				BigInteger randomInt = new BigInteger(190, rand);				
				command = command + " "+new String(Base64.encodeBase64(randomInt.toByteArray()));
			}
			
			Process p = rt.exec(System.getProperty("user.dir")+command);
			p.waitFor(); 

			InputStream is = p.getInputStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			ArrayList<BigInteger> encryptedBigInts = new ArrayList<BigInteger>();
			
			for(int i = 0; i < iter; i ++){
				BigInteger returnedInt = new BigInteger(Base64.decodeBase64(r.readLine()));
				encryptedBigInts.add(returnedInt);
			}
			return encryptedBigInts;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;	}
	
	public static void main(String[] args){
		reencrypt(null,null);
	}
}
