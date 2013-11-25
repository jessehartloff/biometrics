package system.hasher;

import java.math.BigInteger;
import java.util.ArrayList;
import system.allcommonclasses.Template;
import system.allcommonclasses.modalities.Biometric;
import system.allcommonclasses.transformations.Transformation;

/**
 * Implements fuzzy vault.
 *
 */
public class FuzzyVault extends Hasher implements Indexable {

	private class FuzzyVaultPoint {
		
		public BigInteger z;     // These should probably be renamed 
		public BigInteger gamma; // 
		public Boolean chaff;

	}
	
	Integer numberOfChaffPoints;
	Integer fieldSize; // actually the log of the field size, but I don't know how to say that in the 
					   // name. qFieldSize maybe?
	// more variables
	
	/**
	 * Makes the vault... some of these comments are not helpful. Probably not needed.
	 * 
	 * @param template
	 * @return
	 */
	private Template makeVault(Template template){
		
		ArrayList<FuzzyVaultPoint> vaultPoints = new ArrayList<FuzzyVaultPoint>();
		

		
		
		{}{}{}{}{}// TODO make secret polynomial
		
		// adds the genuine points to vaultPoints
		for (BigInteger bigInt : template.hashes) {
			FuzzyVaultPoint genuinePoint = new FuzzyVaultPoint();
			genuinePoint.z = bigInt;
			{}{}{}{}// TODO evaluate secret polynomial to get gamma
			genuinePoint.chaff = false;
			vaultPoints.add(genuinePoint);
		}
		
		{}{}{}{}// TODO add chaff points
		
		Template returnTemplate = new Template();
		
		for(FuzzyVaultPoint point : vaultPoints){
			returnTemplate.hashes.add(this.pointToBigInt(point));
		}
		
		return returnTemplate;
	}
	
	@Override
	public Template makeEnrollTemplate(Biometric biometric) {
		Template template = biometric.quantizeOne();
		return this.makeVault(template);
	}
	
	
	@Override
	public ArrayList<Template> makeTestTemplates(Biometric biometric) {
		
		ArrayList<Template> returnTemplates = new ArrayList<Template>();
		ArrayList<Template> templates = biometric.quantizeAll();
		
		int n = templates.size();
		
		for(int i=0; i<n; i++){
			returnTemplates.add(this.makeVault(templates.get(i)));{}{}{}{}// TODO no no no no no no
		}
		
		return returnTemplates;
	}
	
	
	@Override
	public Double compareTemplates(Template enrolledTemplate, ArrayList<Template> testTemplates) {
		
		Double maxScore = Double.MIN_VALUE;
		for (Template template : testTemplates) {
			Double score = 0.0;
			for (BigInteger hash : template.hashes) {
				{}{}{}{}// TODO try to unlock and get a score. Will involve bigIntToPoint.
				//      Not sure what to really return yet. ShortcutFuzzyVault can be 
				//      used to get scores, this should return the polynomial from decoding.
				//      It might be a long time(if ever) before we need this, but it should
				//      'probably' be figured out. Also, how do we find the max polynomial?
			}
			if(score > maxScore){
				maxScore = score;
			}
		}
		return maxScore;
	}

	@Override
	public Template permute(Template template, Transformation permutation){

		int n = template.hashes.size();
		
		for(int i=0; i<n; i++){
			FuzzyVaultPoint point = this.bigIntToPoint(template.hashes.get(i));
			{}{}{}{}// TODO might have to recover the secret polynomial...
			//      Maybe the permutation should be part of template creation.
			//      If so, need to keep multiple servers in mind.
			point.z = permutation.transform(point.z);
			{}{}{}{}// TODO re-evaluate gamma...
			template.hashes.set(i, this.pointToBigInt(point));
		}
		
		return template; // don't really need to return this
	}

	/**
	 * Converts a fuzzy vault point to a big int for storage in a template.
	 * 
	 * @param point
	 * @return
	 */
	private BigInteger pointToBigInt(FuzzyVaultPoint point){
		{}{}{}{}// TODO pointToBigInt. will use fieldSize
		return null;
	}

	/**
	 * Does what it says. This is where things get a little dangerous since this assumes that 
	 * the input represents a fuzzy vault point. Is there a better solution?
	 * 
	 * @param bigInt
	 * @return
	 */
	private FuzzyVaultPoint bigIntToPoint(BigInteger bigInt){
		{}{}{}{}// TODO bigIntToPoint. will use fieldSize
		return null;
	}
	
}
