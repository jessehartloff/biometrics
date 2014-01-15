package system.allcommonclasses.utilities;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;

public class UsersIO {

	public static Users getUsers(String dataset) {
		Users readUsers = null;
		
		try{
			String fileName = "datasets/" + dataset + ".ser";
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			readUsers = (Users) in.readObject();
			in.close();fileIn.close();
			
			for(User user : readUsers.users){
				user.prequantizedEnrolledTemplates = new ArrayList<Template>();
				user.prequantizedTestTemplates = new ArrayList<ArrayList<Template>>();
			}
			
		}catch(Exception exp){
			exp.printStackTrace();
		}

		
		readUsers.computeBins();
		return readUsers;
	}

	
	
}
