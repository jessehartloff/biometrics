package system.method.quantizers;

import java.math.BigInteger;

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
	
	
	public abstract void train(Users trainingUsers);
	
	public abstract BigInteger featureToBigInt(Feature feature);
	
}
