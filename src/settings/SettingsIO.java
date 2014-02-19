package settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SettingsIO {
	
	public static void saveSettingsToFile(String filename){
		AllSettings all = AllSettings.getInstance();
		
		try{
			FileOutputStream fileOut = new FileOutputStream("savedSettings/" + filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(all);
			out.close();
			fileOut.close();
			System.out.println("Serialized Settings");
//			AllSettings.getInstance().panel.repaint();
//			AllSettings.getInstance().panel.validate();
		}catch(IOException exp){
			exp.printStackTrace();
		}
	}
	

	public static void loadSettingsFromFile(String filename){
		AllSettings.getInstance();
		
		try{
			String fileName = "savedSettings/" + filename;
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			in.readObject();
			in.close();
			fileIn.close();
			AllSettings.getInstance().updateGUI();
//			AllSettings.getInstance().panel.repaint();
//			AllSettings.getInstance().panel.validate();
		}catch(Exception exp){
			exp.printStackTrace();
		}
		
		AllSettings.getInstance().updateDisplay();
		
	}

}