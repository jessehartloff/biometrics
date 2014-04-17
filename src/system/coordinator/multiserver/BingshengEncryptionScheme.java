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
				command = command + " " + new String(Base64.encodeBase64(randomInt.toByteArray()));
			}
			
			Process p = rt.exec(System.getProperty("user.dir")+command);
			p.waitFor(); 

			InputStream is = p.getInputStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			ArrayList<BigInteger> encryptedBigInts = new ArrayList<BigInteger>();
			
			for(int i = 0; i < iter; i ++){
				BingyZhangPoint bzp = new BingyZhangPoint(r.readLine());
				BigInteger returnedInt = bzp.toBigInt();//new BigInteger(Base64.decodeBase64(r.readLine()));
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

			int iter = 200;
			ArrayList< String > toBingsheng = new ArrayList<String>();
			int i = 0;
			while( i < messages.size()){
//				String command = "/bingyzhang/ReEnc AIUiUE/kjQmLfJu514zVw/NzOlHc ";
				String command = "/bingyzhang/ReEnc ";
				command += new String(Base64.encodeBase64(key.toByteArray()));
				command += " ";
				int j = 0;
				BingyZhangPoint bzp = new BingyZhangPoint();
				while(j < iter){
					BigInteger message = messages.get(i+j);
					bzp.fromBigInt(message);
					command += " " + bzp.toEncodedString();
//					command = command + " "+new String(Base64.encodeBase64(message.toByteArray()));
					j++;
				}
				System.out.println(command);
				toBingsheng.add(command);
				i += j;
			}
			ArrayList<Process> processes = new ArrayList<Process>();
			for(String command :toBingsheng){
				processes.add(rt.exec(System.getProperty("user.dir")+command));
			}
			for(Process p : processes){
				p.waitFor(); 
			}
			System.out.println("finsihed!");
			ArrayList<BigInteger> encryptedBigInts = new ArrayList<BigInteger>();

			for(Process p : processes){
				InputStream is = p.getInputStream();
				BufferedReader r = new BufferedReader(new InputStreamReader(is));
				System.out.println("shitshitshit");
				int poop = 0, poop2 = 0;
				for(int k = 0; k < iter; k ++){
					String s= r.readLine();
					System.out.println(s);
					if(s == null)
					poop++;
					else
						poop2++;
					//BigInteger returnedInt = new BigInteger(Base64.decodeBase64(s));
					//encryptedBigInts.add(returnedInt);
				}
				System.out.println(poop);
				System.out.println(poop2);
				
			}
			return encryptedBigInts;
		} catch (Exception e) {
			e.printStackTrace();
		}


		return null;
	}
	
	public static void main(String[] args){
		ArrayList<BigInteger> testInts = new ArrayList<BigInteger>();
		Random r = new Random();
		for(int i = 0; i < 5000; i++)
			testInts.add(new BigInteger(190,r));	
		System.out.println("made some ints");
		ArrayList<BigInteger> encryptedshit = encrypt(null, new BigInteger(190, new Random()) );
		reencrypt(encryptedshit, new BigInteger(190, new Random()));
	}
}
