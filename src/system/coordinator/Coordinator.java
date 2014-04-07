package system.coordinator;
import java.sql.SQLException;
import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Biometric;
import system.allcommonclasses.modalities.Fingerprint;
import system.hasher.*;

/**
 * Coordinates all the stuff. Generates and runs tests.
 * 
 */
public abstract class Coordinator {
	
	public Hasher hasher;
	public Users users;
	
	protected Coordinator nextCoordinator;
	
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
	 * @throws SQLException 
	 */
	public abstract RawScores run();
	
	
}
