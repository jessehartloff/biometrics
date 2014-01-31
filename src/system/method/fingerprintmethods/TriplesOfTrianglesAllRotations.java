package system.method.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;

import settings.modalitysettings.methodsettings.fingerprintmethodsettings.TriangleSettings;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.modalities.Fingerprint;

/**
 * 
 * Putting a new spin on triples of triangles.
 *
 */
public class TriplesOfTrianglesAllRotations extends TriplesOfTriangles {

	
	public TriplesOfTrianglesAllRotations() {
		super();	
	}
	

	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		return triplesOfTrianglesAllRotationsQuantizeOne(fingerprint);
	}
	
	public Template triplesOfTrianglesAllRotationsQuantizeOne(Fingerprint fingerprint) {
		Template toReturn = new Template();
		ArrayList<Template> manyTemplates = super.triplesOfTrianglesQuantizeAll(fingerprint);
//		HashSet<BigInteger> manyTemplates2 = new HashSet<BigInteger>();
		
		for(Template template : manyTemplates){
			for(BigInteger bigInt : template.hashes){
				toReturn.hashes.add(bigInt);
//				manyTemplates2.add(bigInt);
			}
		}
		return toReturn;
	}

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
