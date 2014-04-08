package interfaces.commandline;

import settings.AllSettings;
import settings.coordinatorsettings.testgeneratorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.testgeneratorsettings.TestGeneratorFVCTestsSettings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.NgonAllRotationsSettings;
import settings.fingerprintmethodsettings.PRINTSettings;
import settings.hashersettings.HasherSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FingerprintSettings;
import settings.settingsvariables.SettingsDropDownItem;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import system.allcommonclasses.indexingstructure.SQLStructure;
import system.allcommonclasses.utilities.SQLFunctions;
import system.hasher.fuzzyvault.CRC;
import java.lang.*;
import system.method.fingerprintmethods.NgonsAllRotations;
//import oracle.jdbc.*;
// Main's main job is to build a parameters object, populate it, and give it to a Processor.
// Alternatively, it could read a serialized Parameters file are give that to a Processor.

public class Main {
	public static void main(String[] args) throws Exception {
		SQLFunctions myfs = new SQLFunctions("test");
		myfs.executeMyQueryNoReturn("CREATE DATABASE IF NOT EXISTS temp_database;");
		myfs.executeMyQueryNoReturn("use temp_database;");
		for(int i = 0; i < 100000; i++){
			BigInteger big = new BigInteger("5");
			
		}
		System.out.println("We just did stuff--ain't that neat?");
		System.exit(0);
		myfs.executeMyQueryNoReturn("create table if not exists watch (company text, owner text);");
		SQLFunctions sqlFunctions = new SQLFunctions("fec");
		sqlFunctions.executeMyQueryNoReturn("CREATE DATABASE IF NOT EXISTS biometrics;");
		sqlFunctions.executeMyQueryNoReturn("use biometrics;");
		sqlFunctions.executeMyQueryNoReturn("create table if not exists indexing (bi text, userid text;");
		System.exit(0);
//		myfs.executeMyQueryNoReturn("insert into watch values (\"Rolex\", \"Jim\");");
		ResultSet rs2 = myfs.executeMyQuery("select * from watch;");
		while(rs2.next()){
			System.out.println(rs2.getString(1) + ' ' + rs2.getString(2));
		}
		myfs.executeMyQueryNoReturn("use fec;");
		ResultSet rs = myfs.executeMyQuery("select * from blah");
		while(rs.next()){
			System.out.println(rs.getString(1)+' '+rs.getString(2));
//			System.out.println(rs.toString());
		}
//		myfs.executeMyQueryNoReturn("create table plastics (length int);");
//		myfs.executeMyQueryNoReturn("insert into plastics value (55);");
		ResultSet rs1 = myfs.executeMyQuery("select * from plastics where length < 55");
		while(rs1.next()){
			System.out.println(rs1.getString("length"));
		}
		System.exit(0);
//		int fieldSize = 13;
//		CRCPolynomial crcPoly = new CRCPolynomial();
//		crcPoly = CRCPolynomial.createIrreducible(fieldSize);
//		System.out.println(crcPoly.toPolynomialString());
//		System.out.println(crcPoly.toDecimalString());
//		System.out.println(crcPoly.toBinaryString());
//		CRCPolynomial myPoly = new CRCPolynomial();
//		myPoly = myPoly.createIrreducible(fieldSize);
//		System.out.println(myPoly.toPolynomialString());
//		System.out.println(myPoly.toDecimalString());
//		System.out.println(myPoly.toBinaryString());
//		System.out.println(myPoly.toArrayList().toString());
//		System.out.println(CRC.CheckCRC(crcPoly.toArrayList(),myPoly.toArrayList()));
//		HashMap<Integer,ArrayList<BigInteger>> myMap = new HashMap<Integer,ArrayList<BigInteger>>();
//		for(int i = 1; i < 50; i++){
//			new CRCPolynomial();`
//			CRCPolynomial ref = CRCPolynomial.createIrreducible(i);
//			System.out.print(i);System.out.println(ref.toArrayList().toString());
////			myMap.put(i, ref.toArrayList());
//		}
//		System.exit(0);
//		System.out.println(myMap.toString());
		/*
		 * private static Connection connection = null; if(connection == null){
		 * try{ OracleDataSource ds = new OracleDataSource();
		 * ds.setUser(userName); ds.setPassword(password);
		 * ds.setURL("jdbc:oracle:thin:@aos.acsu.buffalo.edu:1521/aos.buffalo.edu"
		 * ); connection = ds.getConnection();
		 * 
		 * }catch(SQLException e){
		 * System.out.println("Cannot connect to database");
		 * e.printStackTrace(); System.exit(1); } }
		 * 
		 * System.exit(1);
		 */
		AllSettings settings = AllSettings.getInstance(); // loads all the
															// default values
		// set the values for any comboBox
		AllFingerprintMethodSettings.getInstance().manuallySetComboBox(NgonAllRotationsSettings.getInstance());
		// AllMatchingCoordinatorSettings.getInstance().manuallySetComboBox(DefaultTestingPrequantizedMultiThreadedSettings.getInstance());
		AllModalitySettings.getInstance().manuallySetComboBox(FingerprintSettings.getInstance());
		AllTestGeneratorSettings.getInstance().manuallySetComboBox(new TestGeneratorFVCTestsSettings());
		// FingerprintSettings.getInstance().testingDataset(
		// ).manuallySetComboBox(new SettingsDropDownItem("FVC20021Small.ser"));
		// FingerprintSettings.getInstance().trainingDataset().manuallySetComboBox(new
		// SettingsDropDownItem("FVC20021Small.ser"));
		FingerprintSettings
				.getInstance()
				.testingDataset()
				.manuallySetComboBox(new SettingsDropDownItem("FVC2002DB2Training.ser"));
		FingerprintSettings
				.getInstance()
				.trainingDataset()
				.manuallySetComboBox(new SettingsDropDownItem("FVC2002DB1.ser"));
		// //...
		// set values for everything else
		//NgonAllRotationsSettings.getInstance().rotationRegions().setValue(8);
		NgonAllRotationsSettings.getInstance().n().setValue(4);
		NgonAllRotationsSettings.getInstance().kClosestMinutia().setValue(4);
		NgonAllRotationsSettings.getInstance().xBins().setValue(6);// ...
		NgonAllRotationsSettings.getInstance().yBins().setValue(7);// ...
		NgonAllRotationsSettings.getInstance().thetaBins().setValue(8);// ...
//		 NgonSettings.getInstance().rotationRegions().setValue(8);
//		NgonsSingleEnrollAllRotationsSettings.getInstance().n().setValue(3);
//		NgonsSingleEnrollAllRotationsSettings.getInstance().kClosestMinutia().setValue(4);
//		NgonsSingleEnrollAllRotationsSettings.getInstance().xBins().setValue(4);//...EER:
////		 0.0581910403714915
//		NgonsSingleEnrollAllRotationsSettings.getInstance().yBins().setValue(9);//...
//		NgonsSingleEnrollAllRotationsSettings.getInstance().thetaBins().setValue(7);//...

		// settings.runSystemAndMakeGraphs();
		settings.runSystemAndGetResults();

		// Results results = settings.buildSystem().go();
		// System.out.print(results.rawScores);
		// System.out.println(results);

		// TestSerialize tester = TestSerialize.getInstance();
		// tester.setValue(8012L);
		// tester.setAnotherValue(57L);
		//
		// System.out.println(TestSerialize.getInstance());
		//
		// try{
		// FileOutputStream fileOut = new FileOutputStream("testThisThing.ser");
		// ObjectOutputStream out = new ObjectOutputStream(fileOut);
		// out.writeObject(tester);
		// out.close();
		// fileOut.close();
		// System.out.println("Serialized");
		// }catch(IOException exp){
		// exp.printStackTrace();
		// }
		//
		//
		//
		// // System.out.println(TestSerialize.staticValue);
		// //
		// tester.setValue(0L);
		// tester.setAnotherValue(0L);
		//
		//
		// System.out.println(TestSerialize.getInstance());
		//
		// TestSerialize testing;
		//
		// try{
		// String fileName = "testThisThing.ser";
		// FileInputStream fileIn = new FileInputStream(fileName);
		// ObjectInputStream in = new ObjectInputStream(fileIn);
		// // testing = (TestSerialize) in.readObject();
		// in.readObject();
		// in.close();
		// fileIn.close();
		//
		// // System.out.println(testing);
		//
		// }catch(Exception exp){
		// exp.printStackTrace();
		// }
		//
		//
		// System.out.println(TestSerialize.getInstance());

		// AllSettings all = AllSettings.getInstance();
		//
		// try{
		// FileOutputStream fileOut = new
		// FileOutputStream("testAnotherThing.ser");
		// ObjectOutputStream out = new ObjectOutputStream(fileOut);
		// out.writeObject(all);
		// out.close();
		// fileOut.close();
		// System.out.println("Serialized");
		// }catch(IOException exp){
		// exp.printStackTrace();
		// }

		// PrincipleComponentAnalysis pca = new PrincipleComponentAnalysis();
		// pca.setup(5, 6);
		//
		//
		// double[] u0 = {3.2, 3.7, 0.3, 7.5, 4.3, 99.7};
		// double[] u1 = {6.2, 1.7, 1.3, 8.2, 4.2, 99.7};
		// double[] u2 = {3.2, 2.1, 0.7, 7.5, 7.3, 99.7};
		// double[] u3 = {9.2, 3.7, 1.7, 6.5, 3.3, 99.7};
		// double[] u4 = {2.2, 4.7, 0.3, 7.9, 5.3, 99.2};
		//
		// pca.addSample(u0);
		// pca.addSample(u1);
		// pca.addSample(u2);
		// pca.addSample(u3);
		// pca.addSample(u4);
		//
		// pca.computeBasis(5);
		//
		// double[] ux = {4.3, 2.0, 1.7, 5.4, 6.0, 14.3};
		// for(double d : pca.sampleToEigenSpace(ux)){
		// System.out.println(d);
		// }
		// System.out.println();
		// double[] uy = {2.2, 4.7, 0.3, 7.9, 5.3, 99.2};
		// for(double d : pca.sampleToEigenSpace(uy)){
		// System.out.println(d);
		// }
		// System.out.println();
		// double[] uz = {3.2, 3.7, 0.3, 7.5, 4.3, 3.7};
		// for(double d : pca.sampleToEigenSpace(uz)){
		// System.out.println(d);
		// }

		// old stuff //

		//
		// AllSettings settings = new AllSettings();
		//
		// settings.globalSettings.setFingerprintMethod("TRIANGLES");
		// settings.globalSettings.setMatchingCoordinator("NONE");
		// //
		// settings.globalSettings.setMatchingCoordinator("MULTIPLEENROLLMENT");
		//
		// settings.globalSettings.setIndexingCoordinator("NONE");
		// settings.globalSettings.setHistogramCoordinator("NONE");
		// settings.globalSettings.setHasher("STRAIGHTHASHER");
		// settings.globalSettings.setTestGenerator("GENERATEFVCSTYLETESTS");
		// settings.globalSettings.setIndexingStructure("RAM");
		//
		// settings.globalSettings.setTrainingDataset("FVC2002Training");
		// settings.globalSettings.setTestingDataset("FVC2002Testing");
		//
		// settings.globalSettings.setEerStepSize(1.0);
		//
		//
		// settings.triangleSettings.theta0.setBins(8);
		// settings.triangleSettings.x1.setBins(8);
		// settings.triangleSettings.y1.setBins(8);
		// settings.triangleSettings.theta1.setBins(8);
		// settings.triangleSettings.x2.setBins(8);
		// settings.triangleSettings.y2.setBins(8);
		// settings.triangleSettings.theta2.setBins(8);
		//
		// settings.triangleSettings.setRotationStart(-100.0);
		// settings.triangleSettings.setRotationStop(100.0);
		// settings.triangleSettings.setRotationStep(10.0);
		//
		// settings.triangleSettings.setMinimumPointsForTripletOfTriangles(4L);
		// settings.triangleSettings.setThresholdForTriplets(25.0);
		// settings.triangleSettings.setkClosestMinutia(3L);
		// settings.triangleSettings.setkClosestTriangles(3L);
		//
		// settings.pathSettings.d0.setBins(4);
		// settings.pathSettings.d1.setBins(4);
		// settings.pathSettings.d2.setBins(4);
		// settings.pathSettings.d3.setBins(4);
		// settings.pathSettings.phi1.setBins(4);
		// settings.pathSettings.phi2.setBins(4);
		// settings.pathSettings.phi3.setBins(4);
		// settings.pathSettings.sigma0.setBins(4);
		// settings.pathSettings.sigma1.setBins(4);
		// settings.pathSettings.sigma2.setBins(4);
		// settings.pathSettings.sigma3.setBins(4);
		//
		// // settings.pathSettings.kClosestMinutia.setValue(5);
		//
		// settings.minutiaeSettings.x.setBins(8);
		// settings.minutiaeSettings.y.setBins(8);
		// settings.minutiaeSettings.theta.setBins(8);
		//
		// settings.minutiaeSettings.setRotationStart(-50.0);
		// settings.minutiaeSettings.setRotationStop(50.0);
		// settings.minutiaeSettings.setRotationStep(10.0);
		//
		// settings.minutiaeSettings.setxStart(-50L);
		// settings.minutiaeSettings.setxStop(50L);
		// settings.minutiaeSettings.setxStep(10L);
		//
		// settings.minutiaeSettings.setyStart(-50L);
		// settings.minutiaeSettings.setyStop(50L);
		// settings.minutiaeSettings.setyStep(10L);
		//
		// settings.fuzzyVaultSettings.setNumberOfChaffPoints(00L);
		//
		//
		// settings.ngonSettings.setN(5L); //5
		// settings.ngonSettings.setAllNumberOfBins(8L,8L,8L);//5,5,5 //err of
		// 736
		// settings.ngonSettings.setkClosestMinutia(6L); //7
		//
		//
		//
		// settings.ngonSettings.setRotationStart(-50.0);
		// settings.ngonSettings.setRotationStop(50.0);
		// settings.ngonSettings.setRotationStep(5.0);
		//
		//
		// BiometricSystem processor = new BiometricSystem();
		// Results results = processor.go(settings);
		//
		// System.out.print(results.rawScores);
		// System.out.println(results);

	}
}
