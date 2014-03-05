package system.quantizer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;

import settings.fingerprintmethodsettings.TriangleSettings;
import settings.quantizersettings.PCASettings;
import settings.settingsvariables.SettingsMethodVariable;
import system.allcommonclasses.commonstructures.User;
import system.allcommonclasses.commonstructures.Users;
import system.allcommonclasses.modalities.Biometric;
import system.allcommonclasses.utilities.PrincipleComponentAnalysis;
import system.method.feature.Feature;
import system.method.feature.PCAVariable;
import system.method.feature.ThetaVariable;
import system.method.feature.Variable;
import system.method.feature.XYVariable;
import system.method.fingerprintmethods.Triangles.Triangle;
import system.method.fingerprintmethods.Triangles.Triangle.TriangleComparator;

public class PCA extends Quantizer{

	private PrincipleComponentAnalysis pca;
//	private Binning binner;
	private Long numberOfComponents;
	private int numberOfVariables;
	
	private ArrayList<SettingsMethodVariable> variableSettings; 
	
	
	public PCA(){
		pca = new PrincipleComponentAnalysis();
		this.variableSettings = new ArrayList<SettingsMethodVariable>();
		this.numberOfComponents = PCASettings.getInstance().numberOfComponents().getValue();
	}
	
	@Override
	public void train(Users trainingUsers) {
		
		ArrayList<Feature> allFeatures = new ArrayList<Feature>();
		
		Feature blankFeature = Biometric.method.getBlankFeatureForTraining();	
		this.numberOfVariables = blankFeature.variables.size();
		int numberOfFeatures = 0;
		
		for(User user : trainingUsers.users){
			for(Biometric bio : user.readings){
				ArrayList<Feature> features = bio.toFeatures();
				for(Feature feature : features){
					numberOfFeatures++;
					allFeatures.add(feature);
				}
			}
		}
		
		pca.setup(numberOfFeatures, numberOfVariables);
		
		for(Feature feature : allFeatures){
			double[] vector = new double[numberOfVariables];
			int i=0;
			for(Variable var : feature.variables.values()){
				vector[i] = var.getPrequantizedValue().doubleValue();
				i++;
			}
			pca.addSample(vector);
		}
		
		pca.computeBasis(this.numberOfComponents.intValue());
		
		ArrayList<ArrayList<Double>> allPrequantizedValues = new ArrayList<ArrayList<Double>>();
		
		for(int j=0; j<this.numberOfComponents; j++){
			this.variableSettings.add(new SettingsMethodVariable());
			this.variableSettings.get(j).setBins(PCASettings.getInstance().bitsPerComponent().getValue());
			allPrequantizedValues.add(new ArrayList<Double>());
		}
		

		
		
		for(Feature feature : allFeatures){
			double[] vector = new double[numberOfVariables];
			int i=0;
			for(Variable var : feature.variables.values()){
				vector[i] = var.getPrequantizedValue().doubleValue();
				i++;
			}
			double[] components = pca.sampleToEigenSpace(vector);
			
			for(int k=0; k<this.numberOfComponents; k++){
				allPrequantizedValues.get(k).add(components[k]);
			}
			
		}
		
		for(int i=0; i<this.numberOfComponents; i++){
			variableSettings.get(i).computeBinBoundaries(allPrequantizedValues.get(i));
		}
		
		
	}
	
	

	@Override
	public BigInteger featureToBigInt(Feature feature, LinkedHashMap<String, Long> quantizedValues) {
		double[] featureDoubles = new double[this.numberOfVariables];
		
		int q=0;
		for(Variable var : feature.variables.values()){
			featureDoubles[q] = var.getPrequantizedValue().doubleValue();
			q++;
		}
		
		double[] featureComponents = pca.sampleToEigenSpace(featureDoubles);
		
		
		BigInteger toReturn = BigInteger.valueOf(0);
		
		for(int i=0; i<this.numberOfComponents; i++){
			toReturn = toReturn.shiftLeft(variableSettings.get(i).getBits().intValue());
			Long quantizedValue = variableSettings.get(i).findBin(featureComponents[i]);
//			quantizedValues.put("Component " + i, quantizedValue);
			toReturn = toReturn.add(BigInteger.valueOf(quantizedValue));
		}
		
		return toReturn;
	}

	
	@Override
	public Feature quantizeFeature(Feature feature) {
		Feature toReturn = new Feature();
		
		double[] featureDoubles = new double[this.numberOfVariables];
		
		int q=0;
		for(Variable var : feature.variables.values()){
			featureDoubles[q] = var.getPrequantizedValue().doubleValue();
			q++;
		}
		
		double[] featureComponents = pca.sampleToEigenSpace(featureDoubles);
		
		
		
		for(int i=0; i<this.numberOfComponents; i++){
			Long quantizedValue = variableSettings.get(i).findBin(featureComponents[i]);
			toReturn.quantizedValues.put("Component " + i, quantizedValue);
		}
		
		return toReturn;
	}
	
	
	
	@Override
	public Double getTotalLogOfBins() {
		Double totalBits = 0.0;
		for(SettingsMethodVariable variable : variableSettings){
			totalBits += variable.getLogOfBins();
		}
		return totalBits;
	}


	@Override
	public Long getTotalBits() {
		Long totalBits = 0L;
		for(SettingsMethodVariable variable : variableSettings){
			totalBits += variable.getBits();
		}
		return totalBits;
	}

	
	@Override
	public Feature getBlankFeatureForTraining() {
		return new PCAFeature();
	}

	
	public class PCAFeature extends Feature{


		public PCAFeature(){
			SettingsMethodVariable methodVar = new SettingsMethodVariable();
			methodVar.setBins(PCASettings.getInstance().bitsPerComponent().getValue());
			for(int i=0; i<PCASettings.getInstance().numberOfComponents().getValue(); i++){
				this.variables.put("Component " + i, new PCAVariable(methodVar));
			}
		}
		

	
	}


	@Override
	public Feature getRandomFeature() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
