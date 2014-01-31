package settings.modalitysettings.methodsettings.fingerprintmethodsettings;

import java.io.Serializable;

import settings.Settings;
import settings.settingsvariables.SettingsMethodVariable;

public class MinutiaSettings extends AFingerprintMethodSettings{

	private static final long serialVersionUID = 1L;

	//settings
	public SettingsMethodVariable x;
	public SettingsMethodVariable y;
	public SettingsMethodVariable theta;
	
	private Double rotationStep;
	private Double rotationStart;
	private Double rotationStop;
	
	private Long xStep;
	private Long xStart;
	private Long xStop;
	
	private Long yStep;
	private Long yStart;
	private Long yStop;
	//
	
	//singleton
	private static MinutiaSettings instance;
	public MinutiaSettings(){}
	public static MinutiaSettings getInstance(){
		if(instance == null){
			instance = new MinutiaSettings();
		}
		return instance;
	}

	
	//getters and setters
	public Double getRotationStep() {
		return rotationStep;
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

	public Long getxStep() {
		return xStep;
	}

	public void setxStep(Long xStep) {
		this.xStep = xStep;
	}

	public Long getxStart() {
		return xStart;
	}

	public void setxStart(Long xStart) {
		this.xStart = xStart;
	}

	public Long getxStop() {
		return xStop;
	}

	public void setxStop(Long xStop) {
		this.xStop = xStop;
	}

	public Long getyStep() {
		return yStep;
	}

	public void setyStep(Long yStep) {
		this.yStep = yStep;
	}

	public Long getyStart() {
		return yStart;
	}

	public void setyStart(Long yStart) {
		this.yStart = yStart;
	}

	public Long getyStop() {
		return yStop;
	}

	public void setyStop(Long yStop) {
		this.yStop = yStop;
	}
	@Override
	public String getMethodString() {
		return "MINUTIAEMETHOD";
	}
	

	@Override
	public String getLabel(){
		return "Minutia";
	}
	@Override
	protected void init() {
		this.x = new SettingsMethodVariable();
		this.y = new SettingsMethodVariable();
		this.theta = new SettingsMethodVariable();
		}
	
}
