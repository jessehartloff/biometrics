package system.allcommonclasses.commonstructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import system.allcommonclasses.modalities.*;
import system.method.feature.Feature;
import system.method.feature.Variable;

/**
 * 
 * Wrapper class for ArrayList<User>.
 *
 */
public class Users implements Serializable{

	private static final long serialVersionUID = -3149963715534822479L;
	
	public ArrayList<User> users;
	
//	public transient int testerer = 4;
	
	public Users(){
		this.users = new ArrayList<User>();
	}
	
	// assumes all the users have the same type of biometric
	public void computeBins() {
		System.out.println("gg");
		ArrayList<ArrayList<Long>> allPrequantizedValues = new ArrayList<ArrayList<Long>>();
		Feature blankFeature = Biometric.method.getBlankFeatureForBinning();
		for(Variable var : blankFeature.variables.values()){
			System.out.println(var.variableSettings.getBins());
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

		System.out.println("gg");
		
	}
	
		
}
