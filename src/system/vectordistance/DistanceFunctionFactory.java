package system.vectordistance;

import system.allcommonclasses.settings.GlobalSettings;

public class DistanceFunctionFactory{
	
	public static void makeDistanceFunction(){
		switch(DistanceFunctionEnumerator.valueOf(GlobalSettings.getInstance().getDistanceFunction())){
			//cases to be filled in once we have various distance metrics
		}
	}
	
	public enum DistanceFunctionEnumerator{
		//dont' forget to all them here
		//all caps
	}
}