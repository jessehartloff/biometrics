package system.coordinator.testgenerators;

import system.allcommonclasses.settings.GlobalSettings;

public class TestGeneratorFactory{
	
	public static TestGenerator makeTestGenerator(){
		switch(TestGeneratorEnumerator.valueOf(GlobalSettings.getInstance().getTestGenerator())){
			case GENERATEFVCSTYLETESTS:
				return new GenerateFVCStyleTests();
			default:
				System.out.println("You did not provide an appropriate test generator");
				return new GenerateFVCStyleTests();
		}
	}
	
	public enum TestGeneratorEnumerator{
		GENERATEFVCSTYLETESTS;
	}
}