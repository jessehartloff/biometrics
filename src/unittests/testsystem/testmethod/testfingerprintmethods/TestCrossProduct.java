package unittests.testsystem.testmethod.testfingerprintmethods;

import static org.junit.Assert.*;

import org.junit.Test;

import system.allcommonclasses.modalities.Minutia;
import system.method.fingerprintmethods.PRINTS;

public class TestCrossProduct {
	@Test
	public void testCrossProduct(){
		Minutia m0 = new Minutia(0,0,0);
		Minutia m1 = new Minutia(0,1,0);
		Minutia m2 = new Minutia(-1,1,0);
		boolean normal = PRINTS.isLeft(m0, m1, m2);
		boolean reverse = PRINTS.isLeft(m1, m0, m2);
		System.out.println(normal);
		System.out.println(reverse);
		//assertTrue("shit", normal == reverse);

	}

}
