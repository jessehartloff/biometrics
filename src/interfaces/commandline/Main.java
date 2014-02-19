package interfaces.commandline;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JComboBox;

import settings.AllSettings;

import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.modalitysettings.FingerprintSettings;
import system.biometricsystem.BiometricSystem;

import system.allcommonclasses.commonstructures.Results;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.*;



// Main's main job is to build a parameters object, populate it, and give it to a Processor.
// Alternatively, it could read a serialized Parameters file are give that to a Processor.
public class Main {
	public static void main(String[] args) {		
		
		AllSettings settings = AllSettings.getInstance(); // loads all the default values
		
		// set the values for any comboBox
		AllFingerprintMethodSettings.getInstance().manuallySetComboBox(NgonSettings.getInstance());
		//...
		System.out.println("hi");
		// set values for everything else
		NgonSettings.getInstance().n().setValue(3);
		NgonSettings.getInstance().kClosestMinutia().setValue(4);
		//...
		
		
		settings.buildSystem();

//		TestSerialize tester = TestSerialize.getInstance();
//		tester.setValue(8012L);
//		tester.setAnotherValue(57L);
//		
//		System.out.println(TestSerialize.getInstance());
//		
//		try{
//			FileOutputStream fileOut = new FileOutputStream("testThisThing.ser");
//			ObjectOutputStream out = new ObjectOutputStream(fileOut);
//			out.writeObject(tester);
//			out.close();
//			fileOut.close();
//			System.out.println("Serialized");
//		}catch(IOException exp){
//			exp.printStackTrace();
//		}
//		
//		
//
////		System.out.println(TestSerialize.staticValue);
////		
//		tester.setValue(0L);
//		tester.setAnotherValue(0L);
//		
//
//		System.out.println(TestSerialize.getInstance());
//		
//		TestSerialize testing;
//		
//		try{
//			String fileName = "testThisThing.ser";
//			FileInputStream fileIn = new FileInputStream(fileName);
//			ObjectInputStream in = new ObjectInputStream(fileIn);
////			testing = (TestSerialize) in.readObject();
//			in.readObject();
//			in.close();
//			fileIn.close();
//
////			System.out.println(testing);
//			
//		}catch(Exception exp){
//			exp.printStackTrace();
//		}
//		
//
//		System.out.println(TestSerialize.getInstance());
		
		AllSettings all = AllSettings.getInstance();
		
		try{
			FileOutputStream fileOut = new FileOutputStream("testAnotherThing.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(all);
			out.close();
			fileOut.close();
			System.out.println("Serialized");
		}catch(IOException exp){
			exp.printStackTrace();
		}
				
				
		
		// old stuff //
		
//
//		AllSettings settings = new AllSettings();
//
//		settings.globalSettings.setFingerprintMethod("TRIANGLES");
//		settings.globalSettings.setMatchingCoordinator("NONE");
////		settings.globalSettings.setMatchingCoordinator("MULTIPLEENROLLMENT");
//
//		settings.globalSettings.setIndexingCoordinator("NONE");
//		settings.globalSettings.setHistogramCoordinator("NONE");
//		settings.globalSettings.setHasher("STRAIGHTHASHER");
//		settings.globalSettings.setTestGenerator("GENERATEFVCSTYLETESTS");
//		settings.globalSettings.setIndexingStructure("RAM");
//
//		settings.globalSettings.setTrainingDataset("FVC2002Training");
//		settings.globalSettings.setTestingDataset("FVC2002Testing");
//		
//		settings.globalSettings.setEerStepSize(1.0);
//
//
//		settings.triangleSettings.theta0.setBins(8);
//		settings.triangleSettings.x1.setBins(8);
//		settings.triangleSettings.y1.setBins(8);
//		settings.triangleSettings.theta1.setBins(8);
//		settings.triangleSettings.x2.setBins(8);
//		settings.triangleSettings.y2.setBins(8);
//		settings.triangleSettings.theta2.setBins(8);
//
//		settings.triangleSettings.setRotationStart(-100.0);
//		settings.triangleSettings.setRotationStop(100.0);
//		settings.triangleSettings.setRotationStep(10.0);
//
//		settings.triangleSettings.setMinimumPointsForTripletOfTriangles(4L);
//		settings.triangleSettings.setThresholdForTriplets(25.0);
//		settings.triangleSettings.setkClosestMinutia(3L);
//		settings.triangleSettings.setkClosestTriangles(3L);
//
//		settings.pathSettings.d0.setBins(4);
//		settings.pathSettings.d1.setBins(4);
//		settings.pathSettings.d2.setBins(4);
//		settings.pathSettings.d3.setBins(4);
//		settings.pathSettings.phi1.setBins(4);
//		settings.pathSettings.phi2.setBins(4);
//		settings.pathSettings.phi3.setBins(4);
//		settings.pathSettings.sigma0.setBins(4);
//		settings.pathSettings.sigma1.setBins(4);
//		settings.pathSettings.sigma2.setBins(4);
//		settings.pathSettings.sigma3.setBins(4);
//		
////		settings.pathSettings.kClosestMinutia.setValue(5);
//
//		settings.minutiaeSettings.x.setBins(8);
//		settings.minutiaeSettings.y.setBins(8);
//		settings.minutiaeSettings.theta.setBins(8);
//
//		settings.minutiaeSettings.setRotationStart(-50.0);
//		settings.minutiaeSettings.setRotationStop(50.0);
//		settings.minutiaeSettings.setRotationStep(10.0);
//
//		settings.minutiaeSettings.setxStart(-50L);
//		settings.minutiaeSettings.setxStop(50L);
//		settings.minutiaeSettings.setxStep(10L);
//
//		settings.minutiaeSettings.setyStart(-50L);
//		settings.minutiaeSettings.setyStop(50L);
//		settings.minutiaeSettings.setyStep(10L);
//		
//		settings.fuzzyVaultSettings.setNumberOfChaffPoints(00L);
//		
//
//		settings.ngonSettings.setN(5L); //5
//		settings.ngonSettings.setAllNumberOfBins(8L,8L,8L);//5,5,5 //err of 736
//		settings.ngonSettings.setkClosestMinutia(6L); //7
//
//
//
//		settings.ngonSettings.setRotationStart(-50.0);
//		settings.ngonSettings.setRotationStop(50.0);
//		settings.ngonSettings.setRotationStep(5.0);
//
//		
//		BiometricSystem processor = new BiometricSystem();
//		Results results = processor.go(settings);
//
//		System.out.print(results.rawScores);
//		System.out.println(results);

	}
}
