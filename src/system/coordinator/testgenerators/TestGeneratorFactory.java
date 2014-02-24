package system.coordinator.testgenerators;

import system.coordinator.testgenerators.GenerateFVCStyleTests;
import system.coordinator.testgenerators.TestGenerator;

public class TestGeneratorFactory{
	
	public static TestGenerator makeTestGenerator(){
		switch(TestGeneratorEnumerator.valueOf("GENERATEFVCSTYLETESTS")){ //TODO Jesse - test generator factory
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