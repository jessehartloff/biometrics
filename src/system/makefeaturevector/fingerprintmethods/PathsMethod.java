package system.makefeaturevector.fingerprintmethods;

import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.*;
import system.allcommonclasses.modalities.*;
import system.allcommonclasses.settings.*;
import system.makefeaturevector.feature.Feature;
import system.makefeaturevector.feature.SigmaVariable;
import system.makefeaturevector.feature.Variable;

/**
 * 
 * Determines how Paths are quantized and compared. Paths are... (describe paths here)
 *
 */
public class PathsMethod extends FingerprintMethod {

	private PathSettings settings;
	
	public class Path extends Feature{
		private PathSettings innerSettings;
		public Path(){
			this.innerSettings = settings;
			variables.put("d0", new Variable(innerSettings.d0));
			variables.put("d1", new Variable(innerSettings.d1));
			variables.put("d2", new Variable(innerSettings.d2));
			variables.put("d3", new Variable(innerSettings.d3));
			variables.put("phi1", new Variable(innerSettings.phi1));
			variables.put("phi2", new Variable(innerSettings.phi2));
			variables.put("phi3", new Variable(innerSettings.phi3));
			variables.put("sigma0", new SigmaVariable(innerSettings.sigma0));
			variables.put("sigma1", new SigmaVariable(innerSettings.sigma1));
			variables.put("sigma2", new SigmaVariable(innerSettings.sigma2));
			variables.put("sigma3", new SigmaVariable(innerSettings.sigma3));
		}
	}

	
	public PathsMethod() {
		settings = PathSettings.getInstance();
	}


	@Override
	public Template quantizeOne(Fingerprint fingerprint){
		Template template = new Template();
		ArrayList<Path> paths = this.fingerprintToPaths(fingerprint);
		for(Path path : paths){
			template.hashes.add(path.toBigInt());
		}
		return template;
	}

	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 
		templates.add(this.quantizeOne(fingerprint));
		return templates;
	}
	
	
	public ArrayList<Path> fingerprintToPaths(Fingerprint fingerprint){
		ArrayList<Path> paths = new ArrayList<Path>();
		
		ArrayList<Minutia> minutiaeCopy = new ArrayList<Minutia>();
		
		for(Minutia minutia : fingerprint.minutiae){
			minutiaeCopy.add(minutia);
		}
		
		for(Minutia minutia : fingerprint.minutiae){
			Collections.sort(minutiaeCopy, minutia.getComparator()); // sorts by distance to minutia
			
			int startingIndex;
			for(startingIndex=0; minutiaeCopy.get(startingIndex).distanceTo(minutia) < 0.0001; startingIndex++);
			// could do remove spurious by changing the min distance
			Path path = this.makePath(minutia, 
					minutiaeCopy.get(startingIndex), 
					minutiaeCopy.get(startingIndex+1), 
					minutiaeCopy.get(startingIndex+2), 
					minutiaeCopy.get(startingIndex+3)
					);
			
			Path path2 = this.makePath(minutia, 
					minutiaeCopy.get(startingIndex), 
					minutiaeCopy.get(startingIndex+1), 
					minutiaeCopy.get(startingIndex+2), 
					minutiaeCopy.get(startingIndex+4)
					);
			
			Path path3 = this.makePath(minutia, 
					minutiaeCopy.get(startingIndex), 
					minutiaeCopy.get(startingIndex+1), 
					minutiaeCopy.get(startingIndex+3), 
					minutiaeCopy.get(startingIndex+4)
					);
			
			Path path4 = this.makePath(minutia, 
					minutiaeCopy.get(startingIndex), 
					minutiaeCopy.get(startingIndex+2), 
					minutiaeCopy.get(startingIndex+3), 
					minutiaeCopy.get(startingIndex+4)
					);
			
			Path path5 = this.makePath(minutia, 
					minutiaeCopy.get(startingIndex+1), 
					minutiaeCopy.get(startingIndex+2), 
					minutiaeCopy.get(startingIndex+3), 
					minutiaeCopy.get(startingIndex+4)
					);
			

			paths.add(path);
			paths.add(path2);
			paths.add(path3);
			paths.add(path4);
			paths.add(path5);	
		}
	
		return paths;
	}
	
	private Path makePath(Minutia m0, Minutia m1, Minutia m2, Minutia m3, Minutia m4){
		
	
		// order the minutiae
		ArrayList<Minutia> minutiae = new ArrayList<Minutia>();
		minutiae.add(m0);
		minutiae.add(m1);
		minutiae.add(m2);
		minutiae.add(m3);
		minutiae.add(m4);
		Collections.sort(minutiae);
		m0 = minutiae.get(0);
		m1 = minutiae.get(1);
		m2 = minutiae.get(2);
		m3 = minutiae.get(3);
		m4 = minutiae.get(4);
		
		Path pathToReturn = new Path();

		pathToReturn.variables.get("d0").setPrequantizedValue(Math.round(m0.distanceTo(m1)));
		pathToReturn.variables.get("d1").setPrequantizedValue(Math.round(m1.distanceTo(m2)));
		pathToReturn.variables.get("d2").setPrequantizedValue(Math.round(m2.distanceTo(m3)));
		pathToReturn.variables.get("d3").setPrequantizedValue(Math.round(m3.distanceTo(m4)));
		
		pathToReturn.variables.get("phi1").setPrequantizedValue(Math.round(Minutia.computeInsideAngle(m0,m1,m2)));
		pathToReturn.variables.get("phi2").setPrequantizedValue(Math.round(Minutia.computeInsideAngle(m1,m2,m3)));
		pathToReturn.variables.get("phi3").setPrequantizedValue(Math.round(Minutia.computeInsideAngle(m2,m3,m4)));
		
		pathToReturn.variables.get("sigma0").setPrequantizedValue(m0.getTheta() - m1.getTheta());
		pathToReturn.variables.get("sigma1").setPrequantizedValue(m1.getTheta() - m2.getTheta());
		pathToReturn.variables.get("sigma2").setPrequantizedValue(m2.getTheta() - m3.getTheta());
		pathToReturn.variables.get("sigma3").setPrequantizedValue(m3.getTheta() - m4.getTheta());
		
		return pathToReturn;
	}


	@Override
	public Long getTotalBits() {
		Path p = new Path();
		return p.getTotalBits();
	}

	@Override
	public ArrayList<Feature> fingerprintToFeatures(Fingerprint fingerprint){
		return new ArrayList<Feature>(this.fingerprintToPaths(fingerprint));
	}
	
	@Override
	public Feature getBlankFeatureForBinning(){
		return new Path();
	}
	
}
