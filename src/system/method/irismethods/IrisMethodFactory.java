package system.method.irismethods;

import system.allcommonclasses.settings.GlobalSettings;

public class IrisMethodFactory{
	
	public static void makeIrisMethod(){
		switch(IrisMethodEnumerator.valueOf(GlobalSettings.getInstance().getIrisMethod())){
			//cases filled in here once we have iris methods
		}
	}
	
	public enum IrisMethodEnumerator{
		//don't forget to add them here to the enumerator
		//all caps
	}
	
}