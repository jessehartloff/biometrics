package system.allcommonclasses.commonstructures;

import java.io.Serializable;
import java.util.ArrayList;

import system.allcommonclasses.modalities.*;
import system.allcommonclasses.settings.GlobalSettings;
import system.makefeaturevector.feature.Feature;
import system.makefeaturevector.feature.Variable;

/**
 * 
 * Wrapper class for ArrayList<User>.
 *
 */
public class Users implements Serializable{

	private static final long serialVersionUID = -3149963715534822479L;
	
	public ArrayList<User> users;
	
	public Users(){
		this.users = new ArrayList<User>();
	}
	
	// assumes all the users have the same type of biometric
	public void computeBins() {
		
		ArrayList<ArrayList<Long>> allPrequantizedValues = new ArrayList<ArrayList<Long>>();
		Feature blankFeature = Biometric.method.getBlankFeatureForBinning();
		for(Variable var : blankFeature.variables.values()){
			allPrequantizedValues.add(new ArrayList<Long>());
		}
		
		for(User user : this.users){
			for(Biometric bio : user.readings){
				ArrayList<Feature> features = bio.toFeatures();
				for(Feature feature : features){
					int i=0;
					for(Variable var : feature.variables.values()){
						allPrequantizedValues.get(i).add(var.getPrequantizedValue());
						i++;
					}
				}
			}
		}
		int i=0;
		for(Variable var : blankFeature.variables.values()){
			var.variableSettings.computeBinBoundaries(allPrequantizedValues.get(i));
			i++;
		}
		
		
	}
	
	
}
