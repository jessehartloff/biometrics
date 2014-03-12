package interfaces.commandline;


import java.awt.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;

import settings.AllSettings;
import settings.coordinatorsettings.AllMatchingCoordinatorSettings;
import settings.coordinatorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.DefaultTestingPrequantizedMultiThreadedSettings;
import settings.coordinatorsettings.TestGeneratorFVCTestsSettings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.fingerprintmethodsettings.PRINTSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FingerprintSettings;
import settings.settingsvariables.SettingsDropDownItem;
import system.biometricsystem.BiometricSystem;
import system.allcommonclasses.commonstructures.Results;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.*;
import system.allcommonclasses.utilities.PrincipleComponentAnalysis;


// Main's main job is to build a parameters object, populate it, and give it to a Processor.
// Alternatively, it could read a serialized Parameters file are give that to a Processor.
public class Main {
	public static void main(String[] args) {		
	
		AllSettings settings = AllSettings.getInstance(); // loads all the default values
		
		// set the values for any comboBox
		AllFingerprintMethodSettings.getInstance().manuallySetComboBox(PRINTSettings.getInstance());
		//AllMatchingCoordinatorSettings.getInstance().manuallySetComboBox(DefaultTestingPrequantizedMultiThreadedSettings.getInstance());
		AllModalitySettings.getInstance().manuallySetComboBox(FingerprintSettings.getInstance());
		AllTestGeneratorSettings.getInstance().manuallySetComboBox(new TestGeneratorFVCTestsSettings());
//		FingerprintSettings.getInstance().testingDataset( ).manuallySetComboBox(new SettingsDropDownItem("FVC20021Small.ser"));
//		FingerprintSettings.getInstance().trainingDataset().manuallySetComboBox(new SettingsDropDownItem("FVC20021Small.ser"));
		FingerprintSettings.getInstance().testingDataset( ).manuallySetComboBox(new SettingsDropDownItem("FVC2002DB1.ser"));
		FingerprintSettings.getInstance().trainingDataset().manuallySetComboBox(new SettingsDropDownItem("FVC2002DB1.ser"));
		//...
		// set values for everything else
		PRINTSettings.getInstance().rotationRegions().setValue(8);
		PRINTSettings.getInstance().n().setValue(6);
		PRINTSettings.getInstance().kClosestMinutia().setValue(7);
		PRINTSettings.getInstance().distanceBins().setValue(6);//...
		PRINTSettings.getInstance().sigmaBins().setValue(6);//...
		PRINTSettings.getInstance().phiBins().setValue(6);//...
		//NgonSettings.getInstance().rotationRegions().setValue(8);
//		NgonSettings.getInstance().n().setValue(4);
//		NgonSettings.getInstance().kClosestMinutia().setValue(8);
//		NgonSettings.getInstance().xBins().setValue(4);//...EER: 0.0581910403714915
//		NgonSettings.getInstance().yBins().setValue(9);//...
//		NgonSettings.getInstance().thetaBins().setValue(7);//...
		

		settings.runSystemAndMakeGraphs();
		//settings.runSystemAndGetResults();

//		Results results = settings.buildSystem().go();
//		System.out.print(results.rawScores);
//		System.out.println(results);

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
		
//		AllSettings all = AllSettings.getInstance();
//		
//		try{
//			FileOutputStream fileOut = new FileOutputStream("testAnotherThing.ser");
//			ObjectOutputStream out = new ObjectOutputStream(fileOut);
//			out.writeObject(all);
//			out.close();
//			fileOut.close();
//			System.out.println("Serialized");
//		}catch(IOException exp){
//			exp.printStackTrace();
//		}
				
		
		
//		PrincipleComponentAnalysis pca = new PrincipleComponentAnalysis();
//		pca.setup(5, 6);
//	
//
//		double[] u0 = {3.2, 3.7, 0.3, 7.5, 4.3, 99.7};
//		double[] u1 = {6.2, 1.7, 1.3, 8.2, 4.2, 99.7};
//		double[] u2 = {3.2, 2.1, 0.7, 7.5, 7.3, 99.7};
//		double[] u3 = {9.2, 3.7, 1.7, 6.5, 3.3, 99.7};
//		double[] u4 = {2.2, 4.7, 0.3, 7.9, 5.3, 99.2};
//
//		pca.addSample(u0);
//		pca.addSample(u1);
//		pca.addSample(u2);
//		pca.addSample(u3);
//		pca.addSample(u4);
//		
//		pca.computeBasis(5);
//		
//		double[] ux = {4.3, 2.0, 1.7, 5.4, 6.0, 14.3};
//		for(double d : pca.sampleToEigenSpace(ux)){
//			System.out.println(d);
//		}
//		System.out.println();
//		double[] uy = {2.2, 4.7, 0.3, 7.9, 5.3, 99.2};
//		for(double d : pca.sampleToEigenSpace(uy)){
//			System.out.println(d);
//		}
//		System.out.println();
//		double[] uz = {3.2, 3.7, 0.3, 7.5, 4.3, 3.7};
//		for(double d : pca.sampleToEigenSpace(uz)){
//			System.out.println(d);
//		}
		
		
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
