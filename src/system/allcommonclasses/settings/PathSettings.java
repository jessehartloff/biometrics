package system.allcommonclasses.settings;

import java.io.Serializable;

public class PathSettings implements Serializable{

	private static final long serialVersionUID = 1L;

	//settings
	public MethodVariableSettings d0;
	public MethodVariableSettings d1;
	public MethodVariableSettings d2;
	public MethodVariableSettings d3;
	public MethodVariableSettings sigma0;
	public MethodVariableSettings sigma1;
	public MethodVariableSettings sigma2;
	public MethodVariableSettings sigma3;
	public MethodVariableSettings phi1;
	public MethodVariableSettings phi2;
	public MethodVariableSettings phi3;
	
	private Long kClosestMinutia;
	//
	
	//singleton
	private static PathSettings instance;
	private PathSettings(){
		d0 = new MethodVariableSettings();
		d1 = new MethodVariableSettings();
		d2 = new MethodVariableSettings();
		d3 = new MethodVariableSettings();
		sigma0 = new MethodVariableSettings();
		sigma1 = new MethodVariableSettings();
		sigma2 = new MethodVariableSettings();
		sigma3 = new MethodVariableSettings();
		phi1 = new MethodVariableSettings();
		phi2 = new MethodVariableSettings();
		phi3 = new MethodVariableSettings();
	}
	public static PathSettings getInstance(){
		if(instance == null){
			instance = new PathSettings();
		}
		return instance;
	}

	//getters and setters
	public Long getkClosestMinutia() {
		return kClosestMinutia;
	}

	public void setkClosestMinutia(Long kClosestMinutia) {
		this.kClosestMinutia = kClosestMinutia;
	}
	
}
