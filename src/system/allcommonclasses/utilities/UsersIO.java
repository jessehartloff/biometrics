package system.allcommonclasses.utilities;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import system.allcommonclasses.Users;

public class UsersIO {

	public static Users getUsers(String dataset) {
		Users readUsers = null;
		
		try{
			String fileName = "datasets/" + dataset + ".ser";
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			readUsers = (Users) in.readObject();
			in.close();fileIn.close();
		}catch(Exception exp){
			exp.printStackTrace();
		}
		
		return readUsers;
	}

	// this was used to serialize the FVC datasets. No one should ever have to use this again.
//	Users users = FingerprintIO.readFVC(2006, 4);
//	
//	try{
//	FileOutputStream fileOut = new FileOutputStream("FVC/2006-DB4.ser");
//	ObjectOutputStream out = new ObjectOutputStream(fileOut);
//	out.writeObject(users);
//	out.close();
//	fileOut.close();
//	System.out.println("Done");
//}catch(IOException exp){
//	exp.printStackTrace();
//}
	
}
