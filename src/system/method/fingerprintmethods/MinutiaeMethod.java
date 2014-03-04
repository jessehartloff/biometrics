package system.method.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;

import settings.fingerprintmethodsettings.MinutiaSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.allcommonclasses.modalities.Minutia;
import system.method.feature.Feature;
import system.method.feature.Variable;


/**
 * 
 * Determines how minutia points are quantized and compared
 *
 */
public class MinutiaeMethod extends FingerprintMethod {

	private MinutiaSettings settings;
	
	public class InnerMinutia extends Feature{
		private MinutiaSettings innerSettings;
		public InnerMinutia(){
			this.innerSettings = settings;
			variables.put("x", new Variable(innerSettings.x));
			variables.put("y", new Variable(innerSettings.y));
			variables.put("theta", new Variable(innerSettings.theta));
		}
	}

	
	public MinutiaeMethod() {
		settings = MinutiaSettings.getInstance();
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
			minu.variables.get("x").setPrequantizedValue(minutia.getX().doubleValue());
			minu.variables.get("y").setPrequantizedValue(minutia.getY().doubleValue());
			minu.variables.get("theta").setPrequantizedValue(minutia.getTheta().doubleValue());
			minutiae.add(minu);
		}
		return minutiae;
	}


//	@Override
//	public Double distance(BigInteger point1, BigInteger point2) {
//		InnerMinutia m1 = new InnerMinutia();
//		InnerMinutia m2 = new InnerMinutia();
//		m1.fromBigInt(point1);
//		m2.fromBigInt(point2);
//		return m1.distanceFrom(m2);
//	}


	@Override
	public Long getTotalBits() {
		InnerMinutia m = new InnerMinutia();
		return m.getTotalBits();
	}
	
	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint){
		return new ArrayList<Feature>(this.fingerprintToMinutia(fingerprint));
	}
	
	@Override
	public Feature getBlankFeatureForTraining(){
		return new InnerMinutia();
	}
	
}
