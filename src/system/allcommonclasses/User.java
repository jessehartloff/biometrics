package system.allcommonclasses;

import java.util.ArrayList;
import system.allcommonclasses.modalities.*;

/**
 * 
 * A single user of the system. Stores the users unique ID and all their biometric readings.
 *
 */
public class User implements Comparable<User>{

	public Integer ID;
	public ArrayList<? extends Biometric> readings; // TODO check if this works as expected

	// Allows sorting by ID
	@Override
	public int compareTo(User compareUser) {
		return this.ID - compareUser.ID;
	}
	
}
