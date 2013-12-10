package interfaces.commandline;

import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.modalities.Minutia;
import system.allcommonclasses.parameters.GeneralParameters;
import system.allcommonclasses.settings.Settings;
import system.base.Processor;
import system.vectordistance.*;

// Main's main job is to build a parameters object, populate it, and give it to a Processor.
// Alternatively, it could read a serialized Parameters file are give that to a Processor.
public class Main {
	
	private static void bigger(BigInteger big){
		big = big.add(BigInteger.TEN);
	}

	public static void main(String[] args) {
		

		Minutia m = new Minutia();
		m.x = 50L;
		
		Minutia m2 = m;
		
		m2.x += 100L;
		
		BigInteger big = BigInteger.TEN;
		BigInteger big2 = big;
		
		System.out.println("m: " + m);
		System.out.println("m2: " + m2);
		System.out.println("");
		
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
		
		
		Settings settings = new Settings();
		
		settings.globalSettings.setFingerprintMethod("triangles");
		settings.globalSettings.setCoordinator("defaulttesting");
		settings.globalSettings.setHasher("straighthasher");
		
		{}// TODO main
		
		GeneralParameters parameters = new GeneralParameters();
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
