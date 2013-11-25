package system.coordinator;
import system.allcommonclasses.*;
import system.hasher.*;

/**
 * Coordinates all the stuff. Generates and runs tests.
 * TODO comment Coordinator better
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

	{}// TODO could have some methods to add more users or replace the existing users
	
}
