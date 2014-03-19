package settings.coordinatorsettings.testgeneratorsettings;

public class TestGeneratorAllTestsSettings extends TestGeneratorSettings{

	@Override
	public String getTestGenerator() {
		return "GENERATEALLTESTS";
	}

	@Override
	protected void addSettings() {
	}
	
	@Override
	public String getLabel(){
		return "All Tests";
	}


}
