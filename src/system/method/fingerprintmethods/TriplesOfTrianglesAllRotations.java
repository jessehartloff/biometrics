package system.method.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import settings.fingerprintmethodsettings.TriangleSettings;
import settings.fingerprintmethodsettings.TripletsOfTrianglesAllRotationsSettings;
import settings.fingerprintmethodsettings.TripletsOfTrianglesSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;

/**
 * 
 * Putting a new spin on triples of triangles.
 *
 */
public class TriplesOfTrianglesAllRotations extends TriplesOfTriangles {

	private TripletsOfTrianglesAllRotationsSettings settings;
	
	public TriplesOfTrianglesAllRotations() {
		super(TripletsOfTrianglesAllRotationsSettings.getInstance());
		this.settings = TripletsOfTrianglesAllRotationsSettings.getInstance();
	}
	

	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return triplesOfTrianglesAllRotationsQuantizeOne(fingerprint);
	}
	
	public Template triplesOfTrianglesAllRotationsQuantizeOne(Fingerprint fingerprint) {
		Template toReturn = new Template();
//		toReturn.hashes.
		ArrayList<Template> manyTemplates = super.triplesOfTrianglesQuantizeAll(fingerprint);
//		HashSet<BigInteger> manyTemplates2 = new HashSet<BigInteger>();
		
		int totalHashes = 0;
		for(Template template : manyTemplates){
			totalHashes += template.getHashes().size();
		}
		
		BigInteger[] allHashes = new BigInteger[totalHashes];
		
		int i=0;
		for(Template template : manyTemplates){
			for(BigInteger bigInt : template.getHashes()){
				allHashes[i] = bigInt;
				i++;
//				toReturn.hashes.add(bigInt);
//				manyTemplates2.add(bigInt);
			}
		}
		
		toReturn.setHashes(new HashSet<BigInteger>(Arrays.asList(allHashes))); 
		
		return toReturn;
	}

//	EER: 0.24113856068743286
//	FTC: 0.00125
//	ZeroFAR: [FRR=0.4822771213748657, threshold=0.9]
	
	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		return triplesOfTrianglesAllRotationsQuantizeAll(fingerprint);
	}
		
	public ArrayList<Template> triplesOfTrianglesAllRotationsQuantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 
		templates.add(this.triplesOfTrianglesAllRotationsQuantizeOne(fingerprint));
		return templates;
	}

	
}
