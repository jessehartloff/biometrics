package system.coordinator.testgenerators;

import system.allcommonclasses.modalities.Biometric;

/**
 * 
 * Identifies a pair of biometrics to be tested and whether it is a genuine or impostor test.
 *
 */
public class Test {

	public Long enrolledUserID;
	public Long enrolledReadingNumber;
	
	public Long testUserID;
	public Long testReadingNumber;
	
	public Biometric enroll;
	public Biometric test;
	public Boolean genuine;
	
}
