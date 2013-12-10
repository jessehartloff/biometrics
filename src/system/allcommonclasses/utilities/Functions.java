package system.allcommonclasses.utilities;


public class Functions {

	public static Integer binsToBits(Integer bins){
		Double d = Math.ceil(Math.log10(bins)/Math.log10(2));
		return d.intValue();
	}


	

	
	
}
