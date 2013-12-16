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
	
		
//		BufferedReader in = new BufferedReader(new FileReader("/Users/jessehartloff/Documents/CUBS_FP_DATA/DB1/features/1_1.fp"));
//		String text = in.readLine();
//		in.close();
//
//		System.out.println("text: " + text);
//		
//		"/Users/jessehartloff/Documents/CUBS_FP_DATA/DB1/features/1_1.fp"

		Settings settings = new Settings();
		
		
		// !! change to the directory where CUBS_FP_DATA reside. 
		// This will change later to not be terrible.
		GlobalSettings.setDirectoryPathForFVC("/Users/jessehartloff/Documents/");
		
		
		
		settings.globalSettings.setFingerprintMethodString("triangles");
		settings.globalSettings.setCoordinator("defaulttesting");
		settings.globalSettings.setHasher("straighthasher");

		settings.triangleSettings.theta0.setBins(4);
		settings.triangleSettings.x1.setBins(4);
		settings.triangleSettings.y1.setBins(4);
		settings.triangleSettings.theta1.setBins(4);
		settings.triangleSettings.x2.setBins(4);
		settings.triangleSettings.y2.setBins(4);
		settings.triangleSettings.theta2.setBins(4);

		settings.triangleSettings.setRotationStart(-50.0);
		settings.triangleSettings.setRotationStop(50.0);
		settings.triangleSettings.setRotationStep(5.0);
		
		settings.triangleSettings.setThresholdForTriplets(70.0);
		
		Processor p = new Processor();
		p.go(settings);

//		Double d = Double.NEGATIVE_INFINITY;
//		Double dd = 0.0;
//		if(dd > d){
//			d = dd;
//		}
//		System.out.println("d: " + d);
		
		Minutia m = new Minutia();
		m.x = 50L;
		
		Minutia m2 = m;
		
		m2.x += 100L;
		
		BigInteger big = BigInteger.TEN;
		BigInteger big2 = big;
		
//		System.out.println("m: " + m);
//		System.out.println("m2: " + m2);
//		System.out.println("");
		
//		big2 = BigInteger.ZERO;
//
//		System.out.println("big: " + big);
//		System.out.println("big2: " + big2);
//		System.out.println("");
//		
//		bigger(big2);
//
//		System.out.println("big: " + big);
//		System.out.println("big2: " + big2);
//		System.out.println("");
		
		
		
		{}// TODO main
		
		// new Parameters or read serialized from file
		
		Processor runner = new Processor();
		//runner.go(parameters);
		
		
		//int i = 67;
		//double f = i;

		ArrayList<Integer> q;
		q = new ArrayList<Integer>();
		q.add(5);
		q.add(3);
		q.add(8);
		q.add(14);
		
		ArrayList<Integer> r = new ArrayList<Integer>();
		r.add(0);
		r.add(0);
		r.add(11);
		r.add(15);
		
		Distance<Integer> distance = new LkDistance<Integer>();
		
		Double dist = distance.distance(q, r);
		
		/*
		System.out.println("f: " + f);		
		System.out.println("i: " + i);		
		System.out.println("1/i: " + 1/i);
		System.out.println("1.0/i: " + 1.0/i);
		*/
		
		Integer a = 9;
		Integer b = 3;
		Integer c;
		
		c = a + b;


//		System.out.println("Dammit Jim!");
//
//		System.out.println("Happy last day of the semester!");
//
//		System.out.println("c: " + c);
		//System.out.println("DISTANCE: " + dist);


	}
	
}
