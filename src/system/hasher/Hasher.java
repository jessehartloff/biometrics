/**
 * 
 */
package system.hasher;

import java.util.ArrayList;
import system.allcommonclasses.*;
import system.allcommonclasses.modalities.*;
import system.allcommonclasses.transformations.Transformation;
import system.makefeaturevector.Method;

/**
 * Controls all the hashing details. Typically, this class is in charge of the security of the scheme.
 *
 */
public abstract class Hasher {

	Method method;
	
	/**
	 * Converts a biometric to an enrolling template.
	 * 
	 * @param biometric any biometric
	 * @return a template
	 */
	public abstract Template makeEnrollTemplate(Biometric biometric);
	
	/**
	 * Converts a biometric to the test template(s). There could be more than one if the method is not invariant
	 * or has any other reason to have more than one. This may also differ from the enrolled 
	 * template on the Hasher level also. Best example of this is fuzzy vault where the enrolled 
	 * template has chaff points and the test template doesn't.
	 * 
	 * @param biometric
	 * @return an ArrayList containing all relevant templates.
	 */
	public abstract ArrayList<Template> makeTestTemplates(Biometric biometric);
	
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
		int n = template.hashes.size();
		
		for(int i=0; i<n; i++){
			template.hashes.set(i, permutation.transform(template.hashes.get(i)));
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
	
}
