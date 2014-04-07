package system.allcommonclasses.commonstructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import system.allcommonclasses.modalities.*;
import system.method.feature.Feature;
import system.method.feature.Variable;

/**
 * 
 * Wrapper class for ArrayList<User>.
 *
 */
public class Users implements Serializable{

	private static final long serialVersionUID = -3149963715534822479L;
	
	public ArrayList<User> users;
	
	
	public Users(){
		this.users = new ArrayList<User>();
	}
	

	
	public Double removeFailureToCapture() {
		Long total = 0L;
		Long id = 0L;
		Long numberOfFailures = 0L;
		ArrayList<User> usersToRemove = new ArrayList<User>();
		for(User user : this.users){
			ArrayList<Biometric> readingsToRemove = new ArrayList<Biometric>();
			user.id = id;
			for(Biometric biometric : user.readings){
				user.id = total;
				total++;
				if(biometric.isFailure()){
					numberOfFailures++;
					readingsToRemove.add(biometric);
				}
			}
			for(Biometric biometric : readingsToRemove){
				user.readings.remove(biometric);
			}
			if(user.readings.isEmpty()){
				usersToRemove.add(user);
			}
			id++;
		}
		for(User user : usersToRemove){
			this.users.remove(user);
			System.out.println("Lost a whole user!");
		}
		return numberOfFailures.doubleValue()/total.doubleValue();
	}
	
		
}
