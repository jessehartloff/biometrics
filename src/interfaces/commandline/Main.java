package interfaces.commandline;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.modalities.Minutia;
import system.allcommonclasses.settings.GlobalSettings;
import system.allcommonclasses.settings.Settings;
import system.allcommonclasses.utilities.FingerprintIO;
import system.base.Processor;
import system.vectordistance.*;

// Main's main job is to build a parameters object, populate it, and give it to a Processor.
// Alternatively, it could read a serialized Parameters file are give that to a Processor.
public class Main {
	
	private static void bigger(BigInteger big){
		big = big.add(BigInteger.TEN);
	}

	public static void main(String[] args) {

		Settings settings = new Settings();
		
		// !! change to the directory where CUBS_FP_DATA reside. 
		// This will change later to not be terrible.
		GlobalSettings.setDirectoryPathForFVC("/Users/jessehartloff/Documents/");
		
		
		settings.globalSettings.setFingerprintMethodString("triangles");
		settings.globalSettings.setCoordinator("defaulttesting");
		settings.globalSettings.setHasher("straighthasher");

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
		
		settings.triangleSettings.setThresholdForTriplets(70.0);

		settings.pathSettings.d0.setBins(4);
		settings.pathSettings.d1.setBins(4);
		settings.pathSettings.d2.setBins(1);
		settings.pathSettings.d3.setBins(1);
		settings.pathSettings.phi1.setBins(4);
		settings.pathSettings.phi2.setBins(1);
		settings.pathSettings.phi3.setBins(1);
		settings.pathSettings.sigma0.setBins(4);
		settings.pathSettings.sigma1.setBins(4);
		settings.pathSettings.sigma2.setBins(1);
		settings.pathSettings.sigma3.setBins(1);
		
		settings.fuzzyVaultSettings.setNumberOfChaffPoints(00L);
		
		Processor p = new Processor();
		p.go(settings);


	}
	
}
