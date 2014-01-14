package system.allcommonclasses.settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SettingsIO {
	
	public static void saveSettingsToFile(String filename){
		try{
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			Settings settings = new Settings();
			settings.syncWithSettingsClasses();
			out.writeObject(settings);
			file.close();
		}catch(IOException exc){
			exc.printStackTrace();
		}
	}
	

	public static void loadSettingsFromFile(String filename){
		
		try{
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			Settings settings = null;
			settings = (Settings) in.readObject();
			settings.loadToSettingsClasses();
			in.close();
			file.close();
		}catch(IOException exc){
			exc.printStackTrace();
		}catch(ClassNotFoundException classExc){
			classExc.printStackTrace();
		}
		
	}

}