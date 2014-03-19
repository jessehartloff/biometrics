/**
 * 
 */
package system.hasher;

import java.math.BigInteger;
import java.util.ArrayList;

import system.allcommonclasses.*;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.indexingstructure.IndexingStructure;
import system.allcommonclasses.modalities.*;
import system.allcommonclasses.transformations.Transformation;

/**
 * Controls all the hashing details. Typically, this class is in charge of the security of the scheme.
 */
public abstract class Hasher {

//	Method method;

	public abstract Template hashEnrollTemplate(Template template);
	public abstract ArrayList<Template> hashTestTemplates(ArrayList<Template> template);
	
	/**
	 * Converts a biometric to an enrolling template.
	 * 
	 * @param biometric any biometric
	 * @return a template
	 */
	public Template makeEnrollTemplate(Biometric biometric){
		return this.hashEnrollTemplate(biometric.quantizeOne());
	}
	
	/**
	 * Converts a biometric to the test template(s). There could be more than one if the method is not invariant
	 * or has any other reason to have more than one. This may also differ from the enrolled 
	 * template on the Hasher level also. Best example of this is fuzzy vault where the enrolled 
	 * template has chaff points and the test template doesn't.
	 * 
	 * @param biometric
	 * @return an ArrayList containing all relevant templates.
	 */
	public ArrayList<Template> makeTestTemplates(Biometric biometric){
		return this.hashTestTemplates(biometric.quantizeAll());
	}
	
	/**
	 * Computes the matching score between an enrolled template and a set of test templates.
	 * Returns the score to best matching test template.
	 * 
	 * @param enrolledTemplate
	 * @param testTemplate
	 * @return Score of the best matching test template
	 */
	public abstract Double compareTemplates(Template enrolledTemplate, ArrayList<Template> testTemplates);

	/**
	 * Permutes the template.
	 * 
	 * @param template unpermuted template
	 * @param permutation the permutation
	 * @return Permuted template
	 */
	public Template permute(Template template, Transformation permutation){
		
		for(BigInteger hash : template.getHashes()){
			hash = permutation.transform(hash);
		}
		
		return template; // don't really need to return this
	}
	
	/**
	 * Compares an enrolled template with a biometric without having to pre-compute the test templates.
	 * 
	 * @param enrolledTemplate
	 * @param biometric
	 * @return Matching score
	 */
	public Double compareTemplateWithBiometric(Template enrolledTemplate, Biometric biometric) {
		ArrayList<Template> testTemplate = this.makeTestTemplates(biometric);
		Double score = this.compareTemplates(enrolledTemplate, testTemplate);
		return score;
	}
	
	public void addToIndexingStructure(Biometric enrollBiometric, Long enrollID, IndexingStructure indexingStructure){
		
	}
	public Long findIndexingRank(Biometric testBiometric, Long testID, IndexingStructure indexingStructure, Long numberEnrolled){
		return Long.MAX_VALUE;
	}

}
