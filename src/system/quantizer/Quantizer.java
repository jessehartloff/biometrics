package system.quantizer;

import java.math.BigInteger;
import java.util.LinkedHashMap;

import system.allcommonclasses.commonstructures.Users;
import system.method.feature.Feature;

public abstract class Quantizer {

	private static Quantizer quantizer;
	
	public static void setQuantizer(Quantizer quantizer) {
		Quantizer.quantizer = quantizer;
	}

	public static Quantizer getQuantizer() {
		return Quantizer.quantizer;
	}
	
	public abstract Double getTotalLogOfBins();
	public abstract Long getTotalBits();
	public abstract Feature getBlankFeatureForTraining();
	public abstract void train(Users trainingUsers);
	
	// quantizedValues is used for variable histograms. It will be empty and can be filled by this function
	// if you want to see your variable histograms.
	public abstract BigInteger featureToBigInt(Feature feature, LinkedHashMap<String, Long> quantizedValues); 
	
	public abstract Feature quantizeFeature(Feature feature);
	public abstract Feature getRandomFeature();
	public abstract BigInteger getRandomBigInt();
}
