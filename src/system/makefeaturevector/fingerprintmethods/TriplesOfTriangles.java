package system.makefeaturevector.fingerprintmethods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Fingerprint;
import system.makefeaturevector.fingerprintmethods.Triangles.Triangle;

/**
 * 
 * Same as triangles, but times three.
 *
 */
public class TriplesOfTriangles extends Triangles {

	protected class TriangleTriplet{
		Triangle t0;
		Triangle t1;
		Triangle t2;
		
		ArrayList<Triangle> ts;
		
		protected void order(){
			ArrayList<Triangle> threeTriangles = new ArrayList<Triangle>();
			threeTriangles.add(t0);
			threeTriangles.add(t1);
			threeTriangles.add(t2);
			Collections.sort(threeTriangles);
			t0 = threeTriangles.get(0);
			t1 = threeTriangles.get(1);
			t2 = threeTriangles.get(2);
			
		}
		
		protected BigInteger toBigInt(){
			{}// TODO triangles to big int
			return null;
		}
		
		protected void fromBigInt(BigInteger bigInt){
			{}// TODO  big int to triangles
		}
	}
	
	
	
	public TriplesOfTriangles() {
	}
	
	/**
	 * The getInstance methods are the only way to create FingerprintMethod objects. This ensures that
	 * each run of the system will only have only one FingerprintMethod that is shared by 
	 * every object that needs one. This prevents cases where two readings are being
	 * compared, but were quantized using different methods.
	 * 
	 * If an instance already exists, a check is made to ensure that the existing method matches
	 * the method asked for.
	 *  
	 * @return An instance of a FingerprintMethod
	 */
//	public static FingerprintMethod getInstance(){
//		if(singleFingerprintMethod == null){
//			singleFingerprintMethod = new TriplesOfTriangles();
//		}
//		else{
//			FingerprintMethod.checkClass("TriplesOfTriangles");
//		}
//		return singleFingerprintMethod;
//	}
	
	
	@Override
	public Template quantizeOne(Fingerprint fingerprint) {
		Template template = new Template();
		ArrayList<Triangle> triangles = super.fingerprintToTriangles(fingerprint);
		
		int n = triangles.size();
		for(int i=0; i<n; i++){
			for(int j=i+1; j<n; j++){
				for(int k=j+1; k<n; k++){
					if(true/*TODO condition*/){{}
						TriangleTriplet triplet = new TriangleTriplet();
						triplet.t0 = triangles.get(i);
						triplet.t1 = triangles.get(j);
						triplet.t2 = triangles.get(k);
						triplet.order();
						template.hashes.add(triplet.toBigInt());
					}
				}
			}
		}
		return template;
	}

	
	@Override
	public ArrayList<Template> quantizeAll(Fingerprint fingerprint) {
		ArrayList<Template> templates = new ArrayList<Template>(); 
		
		for(double rotation=settings.getRotationStart(); 
				rotation<settings.getRotationStop(); 
				rotation+=settings.getRotationStep())
		{
			Fingerprint rotatedPrint = fingerprint.rotate(rotation);
			templates.add(this.quantizeOne(rotatedPrint));
		}
		return templates;
	}

	
	@Override
	public Double distance(BigInteger point1, BigInteger point2) {
		// TODO triple of triangles distance
		return null;
	}
	
	@Override
	public void doAllTheBinning(ArrayList<Template> templates) {
		{}//TODO fix binning to triplets
		ArrayList<Long> allTheta0 = new ArrayList<Long>();
		ArrayList<Long> allX1 = new ArrayList<Long>();
		ArrayList<Long> allY1 = new ArrayList<Long>();
		ArrayList<Long> allTheta1 = new ArrayList<Long>();
		ArrayList<Long> allX2 = new ArrayList<Long>();
		ArrayList<Long> allY2 = new ArrayList<Long>();
		ArrayList<Long> allTheta2 = new ArrayList<Long>();
		
		Triangle triangle = new Triangle();
		for(Template template : templates){
			for(BigInteger bigInt : template.hashes){
				triangle.fromBigInt(bigInt);
				allTheta0.add(triangle.theta0);
				allX1.add(triangle.x1);
				allY1.add(triangle.y1);
				allTheta1.add(triangle.theta1);
				allX2.add(triangle.x2);
				allY2.add(triangle.y2);
				allTheta2.add(triangle.theta2);
			}
		}
		
		settings.theta0.computeBinBoundaries(allTheta2);
		settings.x1.computeBinBoundaries(allX1);
		settings.y1.computeBinBoundaries(allY1);
		settings.theta1.computeBinBoundaries(allTheta1);
		settings.x2.computeBinBoundaries(allX2);
		settings.y2.computeBinBoundaries(allY2);
		settings.theta2.computeBinBoundaries(allTheta2);
	}
	
	
}
