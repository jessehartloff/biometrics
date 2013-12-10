package system.allcommonclasses;

import java.util.ArrayList;
import system.allcommonclasses.modalities.*;

/**
 * 
 * A single user of the system. Stores the users unique ID and all their biometric readings.
 *
 */
public class User implements Comparable<User>{

	public Integer id;
	//public ArrayList<Fingerprint> fingerprintReadings;
	//public ArrayList<Iris> irisReadings;
	//public ArrayList<Face> faceReadings;

	public ArrayList<? extends Biometric> readings;


	// Allows sorting by ID
	@Override
	public int compareTo(User compareUser) {
		return this.id - compareUser.id;
	}
	
}
