package system.allcommonclasses.utilities;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import system.allcommonclasses.User;
import system.allcommonclasses.Users;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Minutia;
import system.allcommonclasses.settings.GlobalSettings;

public class FingerprintIO {

	public static Fingerprint fingerprintFromFile(String file){
		Fingerprint fingerprint = new Fingerprint();
		
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);

			dis.readLine();
			dis.readLine();
			dis.readLine();
			dis.readLine();
			dis.readLine();
			
			while (dis.available() != 0) {
				String minutiaText = dis.readLine();
				Minutia minutia = new Minutia();

				//System.out.println(minutiaText);
				String[] parseIt = minutiaText.split(" ");
				
				minutia.x = Long.valueOf(parseIt[0]);
				minutia.y = Long.valueOf(parseIt[1]);
				minutia.theta = Long.valueOf(parseIt[2]);
				
				fingerprint.minutiae.add(minutia);
			}
			
			fis.close();
			bis.close();
			dis.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(fingerprint);
		return fingerprint;
	}
	
	public static Users readFVC(){
		Users users = new Users();
		ArrayList<Fingerprint> allFingerprints = new ArrayList<Fingerprint>();
		for(int id=1; id<=100; id++){
			User user = new User();
			user.id = id;
			ArrayList<Fingerprint> fingerprints = new ArrayList<Fingerprint>();
			user.readings = fingerprints;
			
			for(int reading=1; reading<=8; reading++){
				String file = "/Users/jessehartloff/Documents/CUBS_FP_DATA/FVC2002/DB1/features/" 
						+ id +"_"+ reading + ".fp";
				fingerprints.add(fingerprintFromFile(file));
				allFingerprints.add(fingerprintFromFile(file));
			}

			//System.out.println("id: " + id);
			users.users.add(user);
			//System.out.println(user.readings);
		}
		GlobalSettings.fingerprintMethod.doAllTheBinning(allFingerprints);
		
		return users;
	}
	
	
}
