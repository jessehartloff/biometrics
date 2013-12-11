package system.coordinator;
import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.modalities.Biometric;
import system.allcommonclasses.settings.GlobalSettings;
import system.hasher.*;

/**
 * Coordinates all the stuff. Generates and runs tests.
 * 
 */
public abstract class Coordinator {
	
	Hasher hasher;
	Users users;
	
	/**
	 * Coordinator needs to know how to hash, and who's using the system.
	 * 
	 * @param hasher 
	 * @param enrollees
	 */
	public Coordinator(Hasher hasher, Users users){
		this.hasher = hasher;
		this.users = users;
	}
	
	/**
	 * Runs the tests to evaluate the system.
	 * 
	 * @return The relevant scores from the tests. Could be FAR/FRR, rankings, histogram, etc.
	 */
	public abstract RawScores run();

	protected void doTheBinning(){
		ArrayList<Template> templates = new ArrayList<Template>();
		for(User user : users.users){
			for(Biometric b : user.readings){
//				System.out.println(b.quantizeOne().hashes);
				templates.add(b.quantizeOne());
			}
		}
		GlobalSettings.fingerprintMethod.doAllTheBinning(templates); // bad bad bad TODO fix this before iris and face goes up
	}
	
	{}// TODO could have some methods to add more users or replace the existing users
	
}
