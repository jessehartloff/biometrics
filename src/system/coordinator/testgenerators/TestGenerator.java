package system.coordinator.testgenerators;


import system.allcommonclasses.commonstructures.Users;

/**
 * 
 * Determines which pairs of reading to test based on the users.
 *
 */
public abstract class TestGenerator {

	TestGenerator(){
	}
	
	/**
	 * Determines which pairs of reading to test based on the users.
	 *  
	 * @param enrollees
	 * @return Tests
	 */
	public abstract Tests generateTests(Users enrollees);
	
}
