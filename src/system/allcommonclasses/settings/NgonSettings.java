package system.allcommonclasses.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class NgonSettings implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	private LinkedHashMap<String, MethodVariableSettings> ngonVariableMap; 
	private Long N;
	
	private Double rotationStep;
	private Double rotationStart;
	private Double rotationStop;
	
	private Long kClosestMinutia;
	
	private static NgonSettings instance;
	
	private NgonSettings(){
		ngonVariableMap = new LinkedHashMap<String, MethodVariableSettings >();
	}
	
	public static NgonSettings getInstance(){
		if(instance == null){
			instance = new NgonSettings();
		}
		return instance;
		
	}

	public void setN(Long n) {
		this.N = n;
		ngonVariableMap.put("theta0", new MethodVariableSettings());
		for(Long i = 1L; i < n; i++){
			ngonVariableMap.put("x" + i.toString(), new MethodVariableSettings());
			ngonVariableMap.put("y" + i.toString(), new MethodVariableSettings());
			ngonVariableMap.put("theta" + i.toString(), new MethodVariableSettings());
		}
	}
	
	public MethodVariableSettings getMinutiaComponentVariable(String component, Long i){
		return ngonVariableMap.get(component+i.toString());
	}
	
	public void setMiuntiaComponentVariable(String component,Long i, MethodVariableSettings setting){
		ngonVariableMap.put(component + i.toString(), setting);
	}
	
	
	public Long getN(){
		return N;
	}

	public Double getRotationStep() {
		return this.rotationStep;
	}
	
	public void setRotationStep(Double rotationStep) {
		this.rotationStep = rotationStep;
	}

	public Double getRotationStart() {
		return rotationStart;
	}

	public void setRotationStart(Double rotationStart) {
		this.rotationStart = rotationStart;
	}

	public Double getRotationStop() {
		return rotationStop;
	}

	public void setRotationStop(Double rotationStop) {
		this.rotationStop = rotationStop;
	}
	
	public Long getkClosestMinutia() {
		return kClosestMinutia;
	}

	public void setkClosestMinutia(Long kClosestMinutia) {
		this.kClosestMinutia = kClosestMinutia;
	}

	public void setAllNumberOfBins(Long xBinNumber, Long yBinNumber, Long thetaBinNumber) {
		ngonVariableMap.get("theta0").setBins(thetaBinNumber.intValue());
		for(Long i = 1L; i < this.getN(); i++){
			ngonVariableMap.get("x"+i.toString()).setBins(xBinNumber.intValue());
			ngonVariableMap.get("y"+i.toString()).setBins(yBinNumber.intValue());
			ngonVariableMap.get("theta"+i.toString()).setBins(thetaBinNumber.intValue());

		}
		
	}



}