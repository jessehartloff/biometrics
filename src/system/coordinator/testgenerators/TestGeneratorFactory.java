package system.coordinator.testgenerators;

import settings.coordinatorsettings.testgeneratorsettings.AllTestGeneratorSettings;
import system.coordinator.testgenerators.GenerateFVCStyleTests;
import system.coordinator.testgenerators.TestGenerator;

public class TestGeneratorFactory{
	
	public static TestGenerator makeTestGenerator(){
		switch(TestGeneratorEnumerator.valueOf(AllTestGeneratorSettings.getTestGenerator())){
			case GENERATEFVCSTYLETESTS:
				return new GenerateFVCStyleTests();
			case GENERATEALLTESTS:
				return new GenerateAllTests();
		default:
				System.out.println("You did not provide an appropriate test generator");
				return new GenerateFVCStyleTests();
		}
	}
	
	public enum TestGeneratorEnumerator{
		GENERATEFVCSTYLETESTS, GENERATEALLTESTS;
	}
}