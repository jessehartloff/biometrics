package settings.coordinatorsettings.testgeneratorsettings;

public class TestGeneratorFVCTestsSettings extends TestGeneratorSettings{

	@Override
	public String getTestGenerator() {
		return "GENERATEFVCSTYLETESTS";
	}

	@Override
	protected void addSettings() {
	}
	
	@Override
	public String getLabel(){
		return "FVC Tests";
	}

}
