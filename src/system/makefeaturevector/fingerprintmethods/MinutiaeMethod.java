package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Minutia;
import system.allcommonclasses.settings.*;
import system.makefeaturevector.feature.Feature;
import system.makefeaturevector.feature.Variable;


/**
 * 
 * Determines how minutia points are quantized and compared
 *
 */
public class MinutiaeMethod extends FingerprintMethod {

	private MinutiaeSettings settings;
	
	public class InnerMinutia extends Feature{
		private MinutiaeSettings innerSettings;
		public InnerMinutia(){
			this.innerSettings = settings;
			variables.put("x", new Variable(innerSettings.x));
			variables.put("y", new Variable(innerSettings.y));
			variables.put("theta", new Variable(innerSettings.theta));
		}
	}

	
	public MinutiaeMethod() {
		settings = MinutiaeSettings.getInstance();
	}


	@Override
	public Template quantizeOne(Fingerprint fingerprint){
		Template template = new Template();
		ArrayList<InnerMinutia> minutiae = this.fingerprintToMinutia(fingerprint);
		for(InnerMinutia minutia : minutiae){
			template.hashes.add(minutia.toBigInt());
		}
		return template;
	}


	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 
		for(double rotation=settings.getRotationStart(); 
				rotation<settings.getRotationStop(); 
				rotation+=settings.getRotationStep()){
			Fingerprint rotatedPrint = fingerprint.rotate(rotation);
			for(Long deltaX=settings.getxStart();
					deltaX<settings.getxStop();
					deltaX+=settings.getxStep()){
				for(Long deltaY=settings.getyStart();
						deltaY<settings.getyStop();
						deltaY+=settings.getyStep()){
					Fingerprint rotatedTranslatedPrint = rotatedPrint.translate(deltaX, deltaY);
					templates.add(this.quantizeOne(rotatedTranslatedPrint));
				}
			}
		}
		return templates;
	}

	private ArrayList<InnerMinutia> fingerprintToMinutia(Fingerprint fingerprint) {
		ArrayList<InnerMinutia> minutiae = new ArrayList<InnerMinutia>();
		for(Minutia minutia : fingerprint.minutiae){
			InnerMinutia minu = new InnerMinutia();
			minu.variables.get("x").setPrequantizedValue(minutia.getX());
			minu.variables.get("y").setPrequantizedValue(minutia.getY());
			minu.variables.get("theta").setPrequantizedValue(minutia.getTheta());
			minutiae.add(minu);
		}
		return minutiae;
	}


	@Override
	public Double distance(BigInteger point1, BigInteger point2) {
		InnerMinutia m1 = new InnerMinutia();
		InnerMinutia m2 = new InnerMinutia();
		m1.fromBigInt(point1);
		m2.fromBigInt(point2);
		return m1.distanceFrom(m2);
	}

	@Override
	public void doAllTheBinning(ArrayList<Fingerprint> fingerprints) {
		ArrayList<ArrayList<Long>> allPrequantizedValues = new ArrayList<ArrayList<Long>>();
		InnerMinutia m = new InnerMinutia();
		for(Variable var : m.variables.values()){
			allPrequantizedValues.add(new ArrayList<Long>());
		}
		
		for(Fingerprint fingerprint : fingerprints){
			ArrayList<InnerMinutia> minutiae = this.fingerprintToMinutia(fingerprint);
			for(InnerMinutia minutia : minutiae){
				int i=0;
				for(Variable var : minutia.variables.values()){
					allPrequantizedValues.get(i).add(var.getPrequantizedValue());
					i++;
				}
			}
		}
		
		int i=0;
		for(Variable var : m.variables.values()){
			var.variableSettings.computeBinBoundaries(allPrequantizedValues.get(i));
			i++;
		}	
	}

	@Override
	public Long getTotalBits() {
		InnerMinutia m = new InnerMinutia();
		return m.getTotalBits();
	}
	
	@Override
	protected ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint){
		return new ArrayList<Feature>(this.fingerprintToMinutia(fingerprint));
	}
	
	@Override
	protected Feature getBlankFeatureForBinning(){
		return new InnerMinutia();
	}
	
}
