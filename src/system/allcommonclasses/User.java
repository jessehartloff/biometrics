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

	public ArrayList<? extends Biometric> readings;

	public ArrayList<Template> prequantizedEnrolledTemplates;
	public ArrayList<ArrayList<Template>> prequantizedTestTemplates;

	public User(){
		prequantizedEnrolledTemplates = new ArrayList<Template>();
		prequantizedTestTemplates = new ArrayList<ArrayList<Template>>();
	}
	
	// Allows sorting by ID
	@Override
	public int compareTo(User compareUser) {
		return this.id - compareUser.id;
	}
	
}
