package system.method.quantizers;

import java.math.BigInteger;

import settings.quantizersettings.PCASettings;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.utilities.PrincipleComponentAnalysis;
import system.method.feature.Feature;

public class PCA extends Quantizer{

	PrincipleComponentAnalysis pca;
	
//	pca.setup(5, 6);
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
	
	
	public PCA(){
		pca = new PrincipleComponentAnalysis();
	}
	
	@Override
	public void train(Users trainingUsers) {
		// TODO fill pca with users
		
		pca.computeBasis(PCASettings.getInstance().numberOfComponents().getValue().intValue());
	}

	@Override
	public BigInteger featureToBigInt(Feature feature) {
		double[] featureDoubles = null;
//		TODO feature to double[]
		double[] featureComponents = pca.sampleToEigenSpace(featureDoubles);
//		TODO featureComponents binning with PCASettings.getInstance().bitsPerComponent()
//		TODO binned components to bigInt
		return null;
	}

}
