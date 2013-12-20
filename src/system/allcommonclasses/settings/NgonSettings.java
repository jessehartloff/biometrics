package system.allcommonclasses.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class NgonSettings implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	public LinkedHashMap<Long, ArrayList<MethodVariableSettings> > ngonVariableMap; //Make immutable?
	public Long N;
	
	private Double rotationStep;
	private Double rotationStart;
	private Double rotationStop;
	
	private Long kClosestMinutia;
	
	private static NgonSettings instance;
	
	private NgonSettings(){
		ngonVariableMap = new LinkedHashMap<Long, ArrayList<MethodVariableSettings> >();
		
	}
	
	public static NgonSettings getInstance(){
		if(instance == null){
			instance = new NgonSettings();
		}
		return instance;
		
	}

	public void setN(Long n) {
		this.N = n;
		for(Long i = 0L; i < n; i++){
			ArrayList<MethodVariableSettings> ithVariableList = new ArrayList<MethodVariableSettings>();
			for(int j = 0; j < 3; j++)
				ithVariableList.add(new MethodVariableSettings());
			ngonVariableMap.put(i, ithVariableList);
		}
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



}