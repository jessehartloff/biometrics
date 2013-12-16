package system.allcommonclasses.settings;

import java.io.Serializable;

public class PathSettings implements Serializable{

	private static final long serialVersionUID = 1L;

	public MethodVariable d0;
	public MethodVariable d1;
	public MethodVariable d2;
	public MethodVariable d3;
	public MethodVariable sigma0;
	public MethodVariable sigma1;
	public MethodVariable sigma2;
	public MethodVariable sigma3;
	public MethodVariable phi1;
	public MethodVariable phi2;
	public MethodVariable phi3;
	
	private static PathSettings instance;
	private PathSettings(){
		d0 = new MethodVariable();
		d1 = new MethodVariable();
		d2 = new MethodVariable();
		d3 = new MethodVariable();
		sigma0 = new MethodVariable();
		sigma1 = new MethodVariable();
		sigma2 = new MethodVariable();
		sigma3 = new MethodVariable();
		phi1 = new MethodVariable();
		phi2 = new MethodVariable();
		phi3 = new MethodVariable();
	}
	
	public static PathSettings getInstance(){
		if(instance == null){
			instance = new PathSettings();
		}
		return instance;
	}
	
}
