package interfaces.commandline;

import system.allcommonclasses.settings.Settings;
import system.base.Processor;

// Main's main job is to build a parameters object, populate it, and give it to a Processor.
// Alternatively, it could read a serialized Parameters file are give that to a Processor.
public class Main {


	public static void main(String[] args) {

		Settings settings = new Settings();
		

		// !! change to the directory where CUBS_FP_DATA reside. 
		// This will change later to not be terrible. TODO
		
//		LinkedHashMap<String, Integer> mapping = new LinkedHashMap<String, Integer>();
//		mapping.put("thing1", 5);
//		mapping.put("thing2", 77);
//		mapping.put("thing3", -38);
//		mapping.put("thing4", 1);
//
//		Integer f = mapping.get("crap that's not there"); // returns null
//		System.out.println(mapping.get("crap that's not there")); // returns null
//		
//		ArrayList<Integer> vals = new ArrayList<Integer>(mapping.values());
//		
//		for(Integer inter : vals){
//			System.out.println(inter);
//		}
//		System.out.println("");
//		Collections.reverse(vals);
//		for(Integer inter : vals){
//			System.out.println(inter);
//		}
//		
//
//		ArrayList<Method> meths = new ArrayList<Method>();
//
//		PathsMethod m1 = new PathsMethod();
//		meths.add(m1);
//		Triangles m2 = new Triangles();
//		meths.add(m2);
		
//		settings.globalSettings.setFingerprintMethodString("triangles");
//		settings.globalSettings.setCoordinator("defaulttesting");
//		settings.globalSettings.setHasher("straighthasher");

		settings.globalSettings.setDataset("FVC2002-DB1");
		
		settings.triangleSettings.theta0.setBins(8);
		settings.triangleSettings.x1.setBins(8);
		settings.triangleSettings.y1.setBins(8);
		settings.triangleSettings.theta1.setBins(8);
		settings.triangleSettings.x2.setBins(8);
		settings.triangleSettings.y2.setBins(8);
		settings.triangleSettings.theta2.setBins(8);

		settings.triangleSettings.setRotationStart(-50.0);
		settings.triangleSettings.setRotationStop(50.0);
		settings.triangleSettings.setRotationStep(5.0);

		settings.triangleSettings.setMinimumPointsForTripletOfTriangles(4L);
		settings.triangleSettings.setThresholdForTriplets(100.0);
		settings.triangleSettings.setkClosestMinutia(3L);
		settings.triangleSettings.setkClosestTriangles(3L);

		settings.pathSettings.d0.setBins(4);
		settings.pathSettings.d1.setBins(4);
		settings.pathSettings.d2.setBins(4);
		settings.pathSettings.d3.setBins(4);
		settings.pathSettings.phi1.setBins(4);
		settings.pathSettings.phi2.setBins(4);
		settings.pathSettings.phi3.setBins(4);
		settings.pathSettings.sigma0.setBins(4);
		settings.pathSettings.sigma1.setBins(4);
		settings.pathSettings.sigma2.setBins(4);
		settings.pathSettings.sigma3.setBins(4);
		
		settings.pathSettings.setkClosestMinutia(5L);

		settings.minutiaeSettings.x.setBins(8);
		settings.minutiaeSettings.y.setBins(8);
		settings.minutiaeSettings.theta.setBins(8);

		settings.minutiaeSettings.setRotationStart(-50.0);
		settings.minutiaeSettings.setRotationStop(50.0);
		settings.minutiaeSettings.setRotationStep(10.0);

		settings.minutiaeSettings.setxStart(-50L);
		settings.minutiaeSettings.setxStop(50L);
		settings.minutiaeSettings.setxStep(10L);

		settings.minutiaeSettings.setyStart(-50L);
		settings.minutiaeSettings.setyStop(50L);
		settings.minutiaeSettings.setyStep(10L);
		
		settings.fuzzyVaultSettings.setNumberOfChaffPoints(00L);
		
		settings.nGonSettings.setN(3L);
		
		Processor p = new Processor();
		p.go(settings);


	}
	
}
