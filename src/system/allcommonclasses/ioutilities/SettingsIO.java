package system.allcommonclasses.ioutilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import system.allcommonclasses.settings.Settings;

public class SettingsIO {
	
	public static void save(String filename){
		try{
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			Settings settings = new Settings();
			settings.saveSettings();
			out.writeObject(settings);
			file.close();
		}catch(IOException exc){
			exc.printStackTrace();
		}
	}
	

	public static void load(String filename){
		
		try{
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			Settings settings = null;
			settings = (Settings) in.readObject();
			settings.loadSettings();
			in.close();
			file.close();
		}catch(IOException exc){
			exc.printStackTrace();
		}catch(ClassNotFoundException classExc){
			classExc.printStackTrace();
		}
		
	}

}