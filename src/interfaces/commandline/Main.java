package interfaces.commandline;

import system.allcommonclasses.settings.Settings;
import system.base.Processor;

// Main's main job is to build a parameters object, populate it, and give it to a Processor.
// Alternatively, it could read a serialized Parameters file are give that to a Processor.
public class Main {


	public static void main(String[] args) {

	
		Settings settings = new Settings();
		
		
		settings.globalSettings.setFingerprintMethodString("NGONS");
		settings.globalSettings.setCoordinator("DEFAULTTESTINGPREQUANTIZED");
		settings.globalSettings.setHasher("STRAIGHTHASHER");
		settings.globalSettings.setDataset("FVC2002DB1");
		settings.globalSettings.setTestGenerator("GENERATEFVCSTYLETESTS");

		
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
		settings.nGonSettings.setAllNumberOfBins(8L,8L,8L);
		settings.nGonSettings.setkClosestMinutia(3L);
		settings.nGonSettings.setRotationStart(-50.0);
		settings.nGonSettings.setRotationStop(50.0);
		settings.nGonSettings.setRotationStep(5.0);

		
		Processor processor = new Processor();
		processor.go(settings);


	}
}
