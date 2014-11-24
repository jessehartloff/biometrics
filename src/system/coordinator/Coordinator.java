package system.coordinator;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;

import java.sql.SQLException;

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
